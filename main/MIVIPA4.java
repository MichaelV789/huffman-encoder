package main;
import java.util.*;
import java.io.*;

//node for max heap of charaters
class Node implements Comparable<Node> {
    char ch;
    int freq;
    Node left, right;

    public Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        left = right = null;
    }

    @Override
    public int compareTo(Node other) {
        return this.freq - other.freq;
    }

    public void incrementFreq(){
        this.freq++;
    }
}

// priority queue to keep track of max heap
class PriorityQueue {

    public PriorityQueue(Node[] nodes, int size) {
        buildHeap(nodes, size);
    }

    static void heapify(Node arr[], int N, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2
 
        // If left child is larger than root
        if (l < N && arr[l].compareTo(arr[largest]) > 0)
            arr[largest] = arr[l];
 
        // If right child is larger than largest so far
        if (r < N && arr[r].compareTo(arr[largest]) > 0)
            arr[largest] = arr[r];
 
        // If largest is not root
        if (arr[largest] != arr[i]) {
            Node swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
 
            // Recursively heapify the affected sub-tree
            heapify(arr, N, largest);
        }
    }
    // Function to build a Max-Heap from the Array
    static void buildHeap(Node arr[], int N)
    {
        // Index of last non-leaf node
        int startIdx = (N / 2) - 1;
 
        // Perform reverse level order traversal
        // from last non-leaf node and heapify
        // each node
        for (int i = startIdx; i >= 0; i--) {
            heapify(arr, N, i);
        }
    }

}






// main class

public class MIVIPA4 {


    public static Node[] readTextFile(){
        Node[] nodes = {new Node('a', 0), new Node('b', 0), new Node('c', 0), new Node('d', 0), 
                new Node('e', 0), new Node('f', 0), new Node('g', 0), new Node('h', 0), new Node('i', 0), 
                    new Node('j', 0), new Node('k', 0), new Node('l', 0), new Node('m', 0), new Node('n', 0), 
                        new Node('o', 0), new Node('p', 0), new Node('q', 0), new Node('r', 0), 
                            new Node('s', 0), new Node('t', 0), new Node('u', 0), new Node('v', 0), 
                                new Node('w', 0), new Node('x', 0), new Node('y', 0), new Node('z', 0), new Node(' ', 0)};

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("main//merchant.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            System.out.println("File not found or error reading file: " + e.getMessage());
        }
        // Process the entire content as a single block
        String text = content.toString();

        for(char c : text.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                nodes[c - 'a'].incrementFreq(); // Increment the appropriate index for lowercase letters
            } else if (c == ' ') {
                nodes[26].incrementFreq(); // Space character
            }
        }

        return nodes;
    }

    // file write huffman code book

    public static void main(String[] args) throws IOException {
        
        
    }

}