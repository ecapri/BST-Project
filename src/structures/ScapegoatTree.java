package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound;


  @Override
  public void add(T t) {
    BSTNode<T> node = new BSTNode<>(t, null, null);
    root = addToSubtree(root, node);
    upperBound++;
    if (height() > log32(upperBound)) {
      node = node.parent;
      while (3 * subtreeSize(node) <= 2 * subtreeSize(node.parent)) {
        node = node.parent;
      }
      node = node.parent;
      BSTNode<T> par = node.parent;
      
      BinarySearchTree<T> tempTree = new BinarySearchTree<>();
      Iterator<T> iter = inorderIterator(node);
      while (iter.hasNext()) {
        tempTree.add(iter.next());
      }
      tempTree.balance();
      BSTNode<T> tempRoot = tempTree.getRoot();
      if (par.getRight() == node) {
        par.setRight(tempRoot);
      } else {
        par.setLeft(tempRoot);
      }
    }
  }

  @Override
  public boolean remove(T element) {
    if (super.remove(element)) {
      if (2 * size() < upperBound) {
        balance();
        upperBound = size();
      }
      return true;
    }
    return false;
  }
  
  private double log32(int i) {
    return Math.log(i) / Math.log(3.0 / 2.0);
  }
}
