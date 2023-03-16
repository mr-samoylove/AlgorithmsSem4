package org.example;

public class Main
{
    public static void main(String[] args) {
        BinaryTree<Integer> bt = new BinaryTree<>();

        for (int i = 1; i <= 1024; i++) {
            bt.add(i);
        }

        bt.printThreeUppermost();
    }
}