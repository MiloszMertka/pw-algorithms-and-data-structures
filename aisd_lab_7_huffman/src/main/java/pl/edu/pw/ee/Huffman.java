package pl.edu.pw.ee;

import java.io.*;
import java.util.*;

public class Huffman {

    private static final String SOURCE_FILENAME = "source.txt";
    private static final String COMPRESSED_FILENAME = "compressed.comp";
    private static final String DECOMPRESSED_FILENAME = "decompressed.txt";
    private static final String TREE_FILENAME = "tree.tree";

    public int huffman(String pathToRootDir, boolean compress){
    	validatePathToRootDir(pathToRootDir);

        if (compress) {
            return compressFile(pathToRootDir);
        }

        return decompressFile(pathToRootDir);
    }

    private void validatePathToRootDir(String pathToRootDir) {
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("Path to root dir cannot be null!");
        }
    }

    private int compressFile(String pathToRootDir) {
        String characters = readSourceFile(pathToRootDir);

        Map<Character, Integer> charactersFrequency = createCharactersFrequencyMap(characters);
        HuffmanTreeNode huffmanTreeRoot = createHuffmanTree(charactersFrequency);
        Map<Character, String> codes = createCodesMap(huffmanTreeRoot);

        List<Byte> bytes = new ArrayList<>();
        int bitsCount = parseCodesToBytes(bytes, characters, codes);

        writeTreeFile(pathToRootDir, huffmanTreeRoot);
        writeCompressedFile(pathToRootDir, bitsCount, bytes);

        return bitsCount;
    }

    private int decompressFile(String pathToRootDir) {
        HuffmanTreeNode huffmanTreeRoot = readTreeFile(pathToRootDir);
        String characters = readCompressedFile(pathToRootDir, huffmanTreeRoot);

        writeDecompressedFile(pathToRootDir, characters);

        return characters.length();
    }

    private String readSourceFile(String pathToRootDir) {
        String content;
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(pathToRootDir + SOURCE_FILENAME);

        try (Reader reader = new BufferedReader(new FileReader(file))) {
            int readChar;
            while ((readChar = reader.read()) != -1) {
                char character = (char) readChar;
                validateCharacter(character);
                stringBuilder.append(character);
            }
            content = stringBuilder.toString();
        } catch (IOException ioException) {
            throw new IllegalStateException("Exception while reading file " + file.getAbsolutePath());
        }

        return content;
    }

    private Map<Character, Integer> createCharactersFrequencyMap(String characters) {
        Map<Character, Integer> charactersFrequency = new HashMap<>();

        for (char character : characters.toCharArray()) {
            if (!charactersFrequency.containsKey(character)) {
                charactersFrequency.put(character, 1);
            } else {
                charactersFrequency.put(character, charactersFrequency.get(character) + 1);
            }
        }

        return charactersFrequency;
    }

    private void validateCharacter(char character) {
        if (character <= 0 || character > 127) {
            throw new IllegalStateException("Found invalid character!");
        }
    }

    private HuffmanTreeNode createHuffmanTree(Map<Character, Integer> charactersFrequency) {
        HuffmanTreeNode root = null;

        Queue<HuffmanTreeNode> huffmanTreeNodesPriorityQueue = createPriorityQueue(charactersFrequency);

        while (huffmanTreeNodesPriorityQueue.size() > 1) {
            HuffmanTreeNode leftChildren = huffmanTreeNodesPriorityQueue.poll();
            HuffmanTreeNode rightChildren = huffmanTreeNodesPriorityQueue.poll();
            int freqSum = leftChildren.number + rightChildren.number;

            HuffmanTreeNode parent = new HuffmanTreeNode(freqSum, leftChildren, rightChildren);
            huffmanTreeNodesPriorityQueue.add(parent);

            root = parent;
        }

        validateHuffmanTreeIsNotEmpty(root);

        return root;
    }

    private Queue<HuffmanTreeNode> createPriorityQueue(Map<Character, Integer> charactersFrequency) {
        Queue<HuffmanTreeNode> huffmanTreeNodesPriorityQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> charFreqEntry : charactersFrequency.entrySet()) {
            HuffmanTreeNode node = new HuffmanTreeNode(charFreqEntry.getKey(), charFreqEntry.getValue());
            huffmanTreeNodesPriorityQueue.add(node);
        }

        return huffmanTreeNodesPriorityQueue;
    }

    private void validateHuffmanTreeIsNotEmpty(HuffmanTreeNode root) {
        if (root == null) {
            throw new IllegalStateException("Nothing to compress!");
        }
    }

    private Map<Character, String> createCodesMap(HuffmanTreeNode root) {
        Map<Character, String> codesMap = new HashMap<>();
        String code = "";
        convertCharsToCodes(codesMap, root, code);

        return codesMap;
    }

    private void convertCharsToCodes(Map<Character, String> codesMap, HuffmanTreeNode root, String code) {
        if (root.isLeafNode()) {
            codesMap.put(root.character, code);
            return;
        }

        convertCharsToCodes(codesMap, root.left, code + "0");
        convertCharsToCodes(codesMap, root.right, code + "1");
    }

    private int parseCodesToBytes(List<Byte> bytes, String characters, Map<Character, String> codes) {
        int bitsCount = 0;
        byte byteCode = 0;

        for (int i = characters.length() - 1; i >= 0; i--) {
            String code = codes.get(characters.charAt(i));

            for (int j = code.length() - 1; j >= 0; j--) {
                char bit = code.charAt(j);
                byteCode = addBitToByteCode(byteCode, bit);
                bitsCount++;

                if (isByteCodeFull(bitsCount)) {
                    bytes.add(byteCode);
                    byteCode = 0;
                }
            }
        }

        if (!isByteCodeFull(bitsCount)) {
            bytes.add(byteCode);
        }

        return bitsCount;
    }

    private byte addBitToByteCode(byte byteCode, char bit) {
        if (bit == '1') {
            byteCode = (byte) (byteCode >>> 1);
            byteCode |= 1 << 7;
        } else {
            byteCode = (byte) (byteCode >>> 1);
        }

        return byteCode;
    }

    private boolean isByteCodeFull(int bitsCount) {
        return bitsCount % 7 == 0;
    }

    private void writeCompressedFile(String pathToRootDir, int bitsCount, List<Byte> bytes) {
        File outputFile = new File(pathToRootDir + COMPRESSED_FILENAME);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            fileOutputStream.write(bitsCount);

            for (byte byteCode : bytes) {
                fileOutputStream.write(byteCode);
            }
        } catch (IOException | SecurityException exception) {
            throw new IllegalStateException("Exception while writing file " + outputFile.getAbsolutePath());
        }
    }

    private byte[] convertIntToByteArray(int value) {
        return new byte[] {
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
    }

    private void writeTreeFile(String pathToRootDir, HuffmanTreeNode root) {
        File outputFile = new File(pathToRootDir + TREE_FILENAME);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            objectOutputStream.writeObject(root);
        } catch (IOException | SecurityException exception) {
            throw new IllegalStateException("Exception while writing tree file " + outputFile.getAbsolutePath());
        }
    }

    private HuffmanTreeNode readTreeFile(String pathToRootDir) {
        File treeFile = new File(pathToRootDir + TREE_FILENAME);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(treeFile))) {
            return (HuffmanTreeNode) objectInputStream.readObject();
        } catch (IOException | SecurityException exception) {
            throw new IllegalStateException("Exception while reading tree file " + treeFile.getAbsolutePath());
        } catch (ClassNotFoundException | ClassCastException exception) {
            throw new IllegalStateException("Exception while parsing tree object!");
        }
    }

    private String readCompressedFile(String pathToRootDir, HuffmanTreeNode root) {
        StringBuilder result = new StringBuilder();

        File compressedFile = new File(pathToRootDir + COMPRESSED_FILENAME);
        try (FileInputStream fileInputStream = new FileInputStream(compressedFile)) {
            int bitsCount = fileInputStream.read();

            int readByte = 0;
            HuffmanTreeNode temp = root;
            for (int i = 0; i < bitsCount; i++) {
                if (i % 8 == 0) {
                    readByte = fileInputStream.read();
                }

                int position = 7 - (i % 8);
                if (isNthBitOne(readByte, position)) {
                    temp = temp.right;
                } else {
                    temp = temp.left;
                }

                if (temp.isLeafNode()) {
                    result.append(temp.character);
                    temp = root;
                }
            }
        } catch (IOException | SecurityException exception) {
            throw new IllegalStateException("Exception while reading compressed file " + compressedFile.getAbsolutePath());
        }

        return result.toString();
    }

    private boolean isNthBitOne(int readBytes, int n) {
        return ((readBytes >>> n) & 1) == 1;
    }

    private void writeDecompressedFile(String pathToRootDir, String characters) {
        File outputFile = new File(pathToRootDir + DECOMPRESSED_FILENAME);
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            printWriter.print(characters);
        } catch (IOException | SecurityException exception) {
            throw new IllegalStateException("Exception while writing decompressed file " + outputFile.getAbsolutePath());
        }
    }

    private static class HuffmanTreeNode implements Comparable<HuffmanTreeNode>, Serializable {

        private static final long serialVersionUID = 1L;

        private Character character;
        private transient int number;
        private HuffmanTreeNode left;
        private HuffmanTreeNode right;

        public HuffmanTreeNode(int number, HuffmanTreeNode left, HuffmanTreeNode right) {
            this.number = number;
            this.left = left;
            this.right = right;
        }

        public HuffmanTreeNode(Character character, int number) {
            this.character = character;
            this.number = number;
        }

        @Override
        public int compareTo(HuffmanTreeNode otherNode) {
            return number - otherNode.number;
        }

        public boolean isLeafNode() {
            return left == null && right == null;
        }

    }

}
