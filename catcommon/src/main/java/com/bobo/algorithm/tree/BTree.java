package com.bobo.algorithm.tree;

public interface BTree {

    void addLeftTree(BTree child);

    void addRightTree(BTree child);

    void clearTree();

    int dept();

    BTree getLeftChild();

    BTree getRightChild();

    BTree getRootNode();

    boolean hasLeftTree();

    boolean hasRightTree();

    boolean isEmpty();

    boolean isLeftNode();

    boolean isRightNode();

    void removeLeftNode();

    void removeRightNode();

    int getSize();

    public static void main(String[] args) {

    }
}
