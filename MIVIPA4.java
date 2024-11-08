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
// file parser

// file write huffman code book

// main class