// Mia Huynh
// CSE 143 EH with TA Melody Lam
// Take-Home Assessment A8: Huffman
// The HuffManCode class represents a huffman code for a given text. 
// It also keeps track of a binary tree constructed using the huffman algorithm.

import java.util.*;
import java.io.*;
public class HuffmanCode {
    private HuffmanNode overallRoot;

    /**
     * Constructs a HuffmanCode object from a given array of frequencies
     * @param frequencies - the array stores the frequency of occurence of 
     *                      each ASCII character
     */
    public HuffmanCode(int[] frequencies) {
        Queue<HuffmanNode> priorityQueue = new PriorityQueue<HuffmanNode>();
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) {
                priorityQueue.add(new HuffmanNode(i, frequencies[i]));
            }
        }
        while(priorityQueue.size() > 1) {
            HuffmanNode leftNode = priorityQueue.remove();
            HuffmanNode rightNode = priorityQueue.remove();
            int sum = leftNode.frequency + rightNode.frequency;
            priorityQueue.add(new HuffmanNode(sum, leftNode, rightNode));
        }
        this.overallRoot = priorityQueue.remove();
    }

    /**
     * Constructs a HuffmanCode object by reading in a previously constructed
     * code from an input file, where every two lines is a pair, the first line
     * is the ASCII code of the character, and the second line is the huffman 
     * code for that character
     * @param input - the given Scanner is not null and contains data encoded
     *                in valid standard format
     */
    public HuffmanCode(Scanner input) {
        this.overallRoot = null;
        while (input.hasNext()) {
            int asciiValue = Integer.parseInt(input.nextLine()); 
            String code = input.nextLine(); 
            this.overallRoot = readHelper(this.overallRoot, code, asciiValue);
        }
    }

    /**
     * Reconstructs the tree from a given input file. Each call on the method
     * processes one pair of lines.
     * @param root - stores a reference to the current HuffmanNode 
     * @param code - the huffman code for a character
     * @param asciiValue - the ASCII code for a charater
     * @return - the constructed tree
     */
    private HuffmanNode readHelper(HuffmanNode root, String code, int asciiValue) {
        if (root == null) {
            root = new HuffmanNode(-1, null, null);
        }
        if (code.length() > 0) {
            if (code.charAt(0) == '0') { 
                root.left = readHelper(root.left, code.substring(1), asciiValue);
            } else { 
                root.right = readHelper(root.right, code.substring(1), asciiValue);
            }
        }
        root.ascii = asciiValue;
        return root;
    }

    /**
     * Stores the current huffman codes to the given output stream according to
     * this format: every two lines of output is a pair, the first line is the
     * ASCII code of the character, and the second line is the huffman code for 
     * that character
     * @param output - the given output stream
     */
    public void save(PrintStream output) {
        saveHelper(output, this.overallRoot, "");
    }

    /**
     * Traverses the constructed binary tree starting from the root to the node
     * containg the ASCII code, prints "0" if go left, prints "1" if go right
     * @param output - the given output stream
     * @param root - stores a reference to the current HuffmanNode
     * @param code - the huffman code for a character
     */
    private void saveHelper(PrintStream output, HuffmanNode root, String code) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                output.println(root.ascii);
                output.println(code);
            } else {
                saveHelper(output, root.left, code + "0");
                saveHelper(output, root.right, code + "1");
            } 
        }
    }

    /**
     * Reads individual bits from a given input stream and writes the corresponding
     * characters to the given output stream.
     * @param input - the given input stream always contains a legal encoding 
     *                of characters from this tree's huffman code
     * @param output - the given output stream
     */
    public void translate(BitInputStream input, PrintStream output) {
        HuffmanNode root = this.overallRoot;
        while (input.hasNextBit()) {
            int code = input.nextBit();
            if (code == 0) {
                root = root.left;
            } else {
                root = root.right;
            }
            if (root.left == null && root.right == null) {
                output.write(root.ascii);
                root = this.overallRoot;
            }
        }
    }

    // Class that represents a single node in the tree
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        public int ascii;           // ASCII code stored at this node
        public int frequency;       // frequency stored at this node
        public HuffmanNode left;    // reference to left subtree
        public HuffmanNode right;   // reference to right subtree

        /**
         * Constructs a node with the given ASCII code and frequency
         * @param ascii - given ASCII code
         * @param frequency - given frequency
         */
        public HuffmanNode(int ascii, int frequency) {
            this.ascii = ascii;
            this.frequency = frequency;
        }

        /**
         * Constructs a node with the given frequency and links
         * @param frequency - given frequency
         * @param left - link to left subtree
         * @param right - link to right subtree
         */
        public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        /**
         * Returns a number < 0 if this HuffmanNode comes before the other
         * Returns a number > 0 if this HuffmanNode comes after the other
         * Returns 0 if this HuffmanNode and the other are the same
         * HuffmanNodes are ordered by frequency (lower frequencies come first)
         * @return - a number < 0, > 0, or = 0
         */
        public int compareTo(HuffmanNode other) {
            return this.frequency - other.frequency;
        }
    }
}