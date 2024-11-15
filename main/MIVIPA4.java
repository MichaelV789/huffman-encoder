package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// NOTE: Must adjust file paths for desktop run

public class MIVIPA4 {

    // Node class to represent each character and its frequency
    static class Node {
        char ch;
        int freq;
        Node left, right;

        public Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
            left = right = null;
        }

        public void incrementFreq() {
            this.freq++;
        }
    }

    // Priority Queue class implemented using binary heap
    static class PriorityQueue {
        private Node[] heap;
        private int size;

        public PriorityQueue(Node[] nodes, int size) {
            this.size = size;
            heap = new Node[size];
            System.arraycopy(nodes, 0, heap, 0, size);
            buildHeap();
        }

        // Heapify operation to maintain heap property
        private void heapify(int i) {
            int smallest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && heap[left].freq < heap[smallest].freq) {
                smallest = left;
            }
            if (right < size && heap[right].freq < heap[smallest].freq) {
                smallest = right;
            }

            if (smallest != i) {
                Node temp = heap[i];
                heap[i] = heap[smallest];
                heap[smallest] = temp;
                heapify(smallest);
            }
        }

        // Function to build a Min-Heap from the Array
        private void buildHeap() {
            int startIdx = (size / 2) - 1;
            for (int i = startIdx; i >= 0; i--) {
                heapify(i);
            }
        }

        // Extracts the minimum element from the heap
        public Node extractMin() {
            if (size == 0) return null;
            Node minNode = heap[0];
            heap[0] = heap[size - 1];
            size--;
            heapify(0);
            return minNode;
        }

        // Inserts a new node into the heap
        public void insert(Node node) {
            if (size == heap.length) {
                heap = Arrays.copyOf(heap, size * 2);
            }
            heap[size] = node;
            size++;
            int i = size - 1;
            while (i > 0 && heap[(i - 1) / 2].freq > heap[i].freq) {
                Node temp = heap[i];
                heap[i] = heap[(i - 1) / 2];
                heap[(i - 1) / 2] = temp;
                i = (i - 1) / 2;
            }
        }

        // Returns the current size of the heap
        public int getSize() {
            return size;
        }

        // For debugging purposes
        public void printHeap() {
            for (int i = 0; i < size; i++) {
                System.out.print(heap[i].ch + ":" + heap[i].freq + " ");
            }
            System.out.println();
        }
    }

    // Function to read and count frequencies from file
    public static Node[] readTextFile() {
        Node[] nodes = new Node[27];
        for (char c = 'a'; c <= 'z'; c++) {
            nodes[c - 'a'] = new Node(c, 0);
        }
        nodes[26] = new Node(' ', 0); // for space character

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("merchant.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            System.out.println("File not found or error reading file: " + e.getMessage());
        }

        String text = content.toString();
        for (char c : text.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                nodes[c - 'a'].incrementFreq();
            } else if (c == ' ') {
                nodes[26].incrementFreq();
            }
        }
        return nodes;
    }

    // Function to build the Huffman tree
    public static Node buildHuffmanTree(PriorityQueue pq) {
        while (pq.getSize() > 1) {
            Node left = pq.extractMin();
            Node right = pq.extractMin();
            Node newNode = new Node('\0', left.freq + right.freq);
            newNode.left = left;
            newNode.right = right;
            pq.insert(newNode);
        }
        return pq.extractMin();
    }

    // Function to generate Huffman codes
    public static void generateHuffmanCodes(Node root, String code, Map<Character, String> huffmanCodes) {
        if (root == null) return;

        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.ch, code);
        }
        generateHuffmanCodes(root.left, code + "0", huffmanCodes);
        generateHuffmanCodes(root.right, code + "1", huffmanCodes);
    }

    public static void main(String[] args) throws IOException {
        Node[] nodes = readTextFile();
        PriorityQueue pq = new PriorityQueue(nodes, 27);
        Node root = buildHuffmanTree(pq);

        // Generate Huffman codes
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, "", huffmanCodes);

        // Print Huffman codes for debugging
        System.out.println("Generated Huffman Codes:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Prompt the user for N
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of characters to encode: ");
        int N = sc.nextInt();

        // Read the text file into a string
        String text = new String(Files.readAllBytes(Paths.get("merchant.txt")));

        // Ensure N doesn't exceed the text length
        if (N > text.length()) {
            System.out.println("The value of N exceeds the length of the input text. Adjusting N to " + text.length());
            N = text.length(); // Set N to the length of the text
        }

        // Write the output to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            // Write the codebook
            for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }

            // Write the first N characters with running totals
            int totalBitsHuffman = 0;
            int totalBitsASCII = 0;
            for (int i = 0; i < N; i++) {
                char c = text.charAt(i);

                // Check if the character has a Huffman code
                String code = huffmanCodes.get(c);
                if (code == null) {
                    System.out.println("No Huffman code for character: " + c);
                    continue; // Skip the character if no Huffman code exists
                }

                int huffmanBits = code.length();
                int asciiBits = 7; // ASCII is always 7 bits
                totalBitsHuffman += huffmanBits;
                totalBitsASCII += asciiBits;
                writer.write(code + "\t" + totalBitsHuffman + "\t" + totalBitsASCII + "\n");
            }
        }
    }
}
