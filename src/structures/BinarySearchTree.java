package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    if (getFromTree(t, root) == null) {
      return false;
    }
    return true;
  }

  /**
   * remove the data from the tree.
   */
  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }
  
  private T getFromTree(T t, BSTNode<T> node) {
    if (node == null) {
      return null;
    }
    if (t.compareTo(node.getData()) < 0) {
      return getFromTree(t, node.getLeft());
    }
    if (t.equals(node.getData())) {
      return node.getData();
    }
    return getFromTree(t, node.getRight());
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    return getFromTree(t, root);
  }


  /**
   * add data into the tree.
   */
  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {
    return getMinimum(root);
  }
  
  private T getMinimum(BSTNode<T> node) {
    if (isEmpty()) {
      return null;
    }
    if (node.getLeft() == null) {
      return node.getData();
    }
    return getMinimum(node.getLeft());
  }


  @Override
  public T getMaximum() {
    return getMaximum(root);
  }
  
  private T getMaximum(BSTNode<T> node) {
    if (isEmpty()) {
      return null;
    }
    if (node.getRight() == null) {
      return node.getData();
    }
    return getMaximum(node.getRight());
  }

  @Override
  public int height() {
    return height(root);
  }
  
  private int height(BSTNode<T> node) {
    if (node == null) {
      return -1;
    }
    return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
  }


  public Iterator<T> preorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue, root);
    return queue.iterator();
  }
  
  private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      queue.add(node.getData());
      preorderTraverse(queue, node.getLeft());
      preorderTraverse(queue, node.getRight());
    }
  }


  /**
   * in-order traversal.
   */
  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }
  
  public Iterator<T> inorderIterator(BSTNode<T> node) {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, node);
    return queue.iterator();
  }


  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }
  
  private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData());
    }
  }


  @Override
  public boolean equals(BSTInterface<T> other) {
    if (other == null) {
      throw new NullPointerException();
    }
    return equals(root, other.getRoot());
  }

  private boolean equals(BSTNode<T> node, BSTNode<T> other) {
    if (node == null && other == null) {
      return true;
    }
    if ((node != null && other == null) || (node == null && other != null)) {
      return false;
    }
    if (!node.getData().equals(other.getData())) {
      return false;
    }
    return equals(node.getLeft(), other.getLeft()) && equals(node.getRight(), other.getRight());
  }

  @Override
  public boolean sameValues(BSTInterface<T> other) {
    if (other == null) {
      throw new NullPointerException();
    }
    Iterator<T> tree = this.inorderIterator();
    Iterator<T> tree2 = other.inorderIterator();
    while (tree.hasNext() && tree2.hasNext()) {
      if (!tree.next().equals(tree2.next())) {
        return false;
      }
    }
    if (tree.hasNext() || tree2.hasNext()) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isBalanced() {
    return size() >= Math.pow(2, height()) && size() <= Math.pow(2, height() + 1);
  }

  @Override
  @SuppressWarnings("unchecked")

  public void balance() {
    T[] values = (T[]) new Comparable[size()];
    Iterator<T> iter = this.inorderIterator();
    int i = 0;
    while (iter.hasNext()) {
      values[i] = iter.next();
      i++;
    }
    root = balanceHelper(values, 0, size() - 1);
  }
  
  private BSTNode<T> balanceHelper(T[] arr, int low, int high) {
    if (low > high) {
      return null;
    }
    int mid = (low + high) / 2;
    BSTNode<T> node = new BSTNode<T>(arr[mid], balanceHelper(arr, low, mid - 1), balanceHelper(arr, mid + 1, high));
    return node;  
  }


  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  /**
   * toDotFormat.
   * @param root root of tree.
   * @return type T.
   */
  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> "
            + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count
            + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot += cursor.getData().toString() + " -> "
            + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count
            + ";\n";
        count++;
      }

    }
    dot += "};";
    return dot;
  }

  /**
   * main method.
   * @param args arguments.
   */
  public static void main(String[] args) {
    for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
      tree.add(r);
    }
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}