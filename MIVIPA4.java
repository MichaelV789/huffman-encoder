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

// file parser

// file writer

// main class