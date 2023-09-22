/**
 * A minimal implementation of a binary search tree. See the python version for
 * additional documentation.
 *
 * You can also see Chapter 6 of <a href="https://www.teach.cs.toronto.edu/~csc148h/winter/notes/">CSC148 Course Notes</a>
 * if you want a refresher on BSTs, but it is required to complete this assignment.
 * @param <T>
 */
import java.util.Random;

public class BST<T extends Comparable<T>> {
    private T root;
    private BST<T> left;
    private BST<T> right;

    public BST(T root) {
        if (root != null) {
            this.root = root;
            this.left = new BST<>();
            this.right = new BST<>();
        }
    }

    public BST() {
        this(null);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T item) {
        if (isEmpty()) {
            return false;
        } else if (item.equals(root)) {
            return true;
        } else if (item.compareTo(root) < 0) {
            return left.contains(item);
        }
        return right.contains(item);
    }

    public void insert(T item) {
        if (isEmpty()) {
            root = item;
            left = new BST<>();
            right = new BST<>();
        } else if (item.compareTo(root) <= 0) {
            left.insert(item);
        } else {
            right.insert(item);
        }
    }

    public void delete(T item) {
        if (isEmpty()) {
            return;
        } else if (item.equals(root)) {
            deleteRoot();
        } else if (item.compareTo(root) < 0) {
            left.delete(item);
        } else {
            right.delete(item);
        }
    }

    private void deleteRoot() {
        if (left.isEmpty() && right.isEmpty()) {
            root = null;
            left = null;
            right = null;
        } else if (left.isEmpty()) {
            root = right.root;
            left = right.left;
            right = right.right;
        } else if (right.isEmpty()) {
            root = left.root;
            left = left.left;
            right = left.right;
        } else {
            root = left.extractMax();
        }
    }

    private T extractMax() {
        if (right.isEmpty()) {
            T maxItem = root;
            root = null;
            left = null;
            right = null;
            return maxItem;
        } else {
            return right.extractMax();
        }
    }

    public int height() {
        if (isEmpty()) {
            return 0;
        } else {
            return Math.max(left.height(), right.height()) + 1;
        }
    }

    public int count(T item) {
        if (isEmpty()) {
            return 0;
        } else if (item.equals(root)) {
            return 1 + left.count(item) + right.count(item);
        } else if (item.compareTo(root) < 0) {
            return left.count(item);
        } else {
            return right.count(item);
        }
    }

    public int getLength() {
        if (isEmpty()) {
            return 0;
        } else {
            return 1 + left.getLength() + right.getLength();
        }
    }

    public static void main(String[] args) {
        BST<Integer> tree = new BST<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int randValue = random.nextInt(100);
            tree.insert(randValue);
        }

        System.out.println("Tree height: " + tree.height());
        System.out.println("Number of elements: " + tree.getLength());
    }
}

}
