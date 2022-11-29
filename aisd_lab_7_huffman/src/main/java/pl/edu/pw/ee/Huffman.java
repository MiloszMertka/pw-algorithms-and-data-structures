package pl.edu.pw.ee;

import java.io.*;
import java.util.*;

public class Huffman {

    private static final String SOURCE_FILENAME = "source.txt";
    private static final String COMPRESSED_FILENAME = "compressed.comp";
    private static final String DECOMPRESSED_FILENAME = "decompressed.txt";

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
        Queue<HuffmanTreeNode> huffmanTreeNodesPriorityQueue = createPriorityQueue(charactersFrequency);
        HuffmanTreeNode huffmanTreeRoot = createHuffmanTree(huffmanTreeNodesPriorityQueue);
        Map<Character, String> codes = createCodesMap(huffmanTreeRoot);

        List<Integer> bytes = new ArrayList<>();
        int bitsCount = parseCodesToBytes(bytes, characters, codes);

        writeCharactersMap(pathToRootDir, charactersFrequency);
        writeCompressedFile(pathToRootDir, bitsCount, bytes);

        return bitsCount;
    }

    private int decompressFile(String pathToRootDir) {
        // TODO
        return -1;
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

    private Queue<HuffmanTreeNode> createPriorityQueue(Map<Character, Integer> charactersFrequency) {
        Queue<HuffmanTreeNode> huffmanTreeNodesPriorityQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> charFreqEntry : charactersFrequency.entrySet()) {
            HuffmanTreeNode node = new HuffmanTreeNode(charFreqEntry.getKey(), charFreqEntry.getValue());
            huffmanTreeNodesPriorityQueue.add(node);
        }

        return huffmanTreeNodesPriorityQueue;
    }

    private HuffmanTreeNode createHuffmanTree(Queue<HuffmanTreeNode> huffmanTreeNodesPriorityQueue) {
        HuffmanTreeNode root = null;

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

    private int parseCodesToBytes(List<Integer> bytes, String characters, Map<Character, String> codes) {
        int bitsCount = 0;
        int byteCode = 0;

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

    private int addBitToByteCode(int byteCode, char bit) {
        if (bit == '1') {
            byteCode = byteCode >>> 1;
            byteCode |= 1 << 31;
        } else {
            byteCode = byteCode >>> 1;
        }

        return byteCode;
    }

    private boolean isByteCodeFull(int bitsCount) {
        return bitsCount % 32 == 0;
    }

    private void writeCompressedFile(String pathToRootDir, int bitsCount, List<Integer> bytes) {
        File outputFile = new File(pathToRootDir + COMPRESSED_FILENAME);
        boolean appendMode = true;
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile, appendMode)) {
            fileOutputStream.write(bitsCount);

            for (int fourBytes : bytes) {
                fileOutputStream.write(convertIntToByteArray(fourBytes));
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

    private void writeCharactersMap(String pathToRootDir, Map<Character, Integer> charactersFrequency) {
        File outputFile = new File(pathToRootDir + COMPRESSED_FILENAME);
        try (PrintWriter printWriter = new PrintWriter(outputFile)) {
            int entriesCount = charactersFrequency.size();
            printWriter.print(entriesCount);

            for (Map.Entry<Character, Integer> characterFrequency : charactersFrequency.entrySet()) {
                char character = characterFrequency.getKey();
                int frequency = characterFrequency.getValue();
                printWriter.print(character);
                printWriter.print(frequency);
            }
        } catch (IOException | SecurityException exception) {
            throw new IllegalStateException("Exception while writing tree to file " + outputFile.getAbsolutePath());
        }
    }

    private class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {

        private Character character;
        private int number;
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
