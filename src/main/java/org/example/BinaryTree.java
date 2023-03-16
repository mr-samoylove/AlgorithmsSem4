package org.example;

import java.util.ArrayDeque;
import java.util.Stack;

public class BinaryTree<T extends Comparable<T>> {
    private Node root;

    private class Node {
        private T value;
        private Node left;
        private Node right;
        private Color color;

        Node(T value) {
            this.value = value;
            this.color = Color.RED;
        }
    }

    private enum Color {
        RED, BLACK
    }

    public void printThreeUppermost() {
        if (root == null) System.out.println("Tree is empty");
        else if (root.left == null) System.out.println("Left is null");
        else if (root.right == null) System.out.println("Right is null");
        else {
            System.out.printf("root: %s, left: %s, right: %s\n", root.value, root.left.value, root.right.value);
        }
    }

    public boolean add(T value) {
        Node node = new Node(value);
        if (root == null) {
            root = node;
            root.color = Color.BLACK;
        } else if (tryAdd(node)) {
            root = rebalance(root);
        } else return false;
        return true;
    }

    private boolean tryAdd(Node newNode) {
        Stack<Node> stack = new Stack<>();
        Node current = root;
        stack.add(current);
        boolean added = false;
        while (!added) {
            int difference = current.value.compareTo(newNode.value);
            if (difference != 0) {
                if (difference > 0) {
                    if (current.left != null) {
                        stack.add(current.left);
                        current = current.left;
                    } else {
                        current.left = newNode;
                        added = true;
                    }
                } else {
                    if (current.right != null) {
                        stack.add(current.right);
                        current = current.right;
                    } else {
                        current.right = newNode;
                        added = true;
                    }
                }
            } else {
                stack.clear();
                return false;
            }
        } // while
        Node lowerMost;
        while (!stack.isEmpty()) {
            lowerMost = stack.pop();
            if (lowerMost.left != null) lowerMost.left = rebalance(lowerMost.left);
            if (lowerMost.right != null) lowerMost.right = rebalance(lowerMost.right);

        }
        return added;
    } // tryAdd

    private Node rebalance(Node node) {
        if (node.right != null && node.right.color == Color.RED && (node.left == null || node.left.color == Color.BLACK))
            node = swapAntiClockWise(node);

        else if (node.left != null && node.left.color == Color.RED && node.left.left != null && node.left.left.color == Color.RED)
            node = swapClockWise(node);

        else if (node.left != null && node.left.color == Color.RED && node.right != null && node.right.color == Color.RED) {
            node.right.color = node.left.color = Color.BLACK;
            node.color = Color.RED;
        }
        return node;
    }

    private Node swapAntiClockWise(Node node) {
        Node rightChild = node.right;
        Node betweenChild = rightChild.left;
        rightChild.left = node;
        node.right = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private Node swapClockWise(Node node) {
        Node leftChild = node.left;
        Node betweenChild = leftChild.right;
        leftChild.right = node;
        node.left = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }
}
