package mytree;

public class DAA1 extends MyTree {

    // 1. isBST() [20 points]
    public static boolean isBST(MyTree t) {
        return isBST(t, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    // Helper function for isBST
    // Get a boolean value to know whether 't' is BST (Binary Search Tree)
    // whose values are within the range between lowerBound and upperBound
    private static boolean isBST(MyTree t, int lowerBound, int upperBound) {
        // Base case: If the tree is empty, it is a valid BST
        if (t.getEmpty()) {
            return true;
        }
        
        // Get the value of the current node
        int value = t.getValue();
        
        // If the current node's value is out of bounds, return false
        if (value <= lowerBound || value >= upperBound) {
            return false;
        }

        // Recursively check the left and right subtrees with updated bounds
        return isBST(t.getLeft(), lowerBound, value) && isBST(t.getRight(), value, upperBound);
    }

    // 2. printDescending() [10 points]
    public static void printDescending(MyTree t) {
        // Cek apakah pohon kosong
        if (t.getEmpty()) {
            return;
        }

        // Traversal kanan (Right), node (Root), dan kemudian kiri (Left)
        printDescending(t.getRight());  // Traversal kanan terlebih dahulu
        System.out.println(t.getValue());  // Cetak nilai node
        printDescending(t.getLeft());  // Traversal kiri
    }

    // 3. max() [10 points]   
    /**
     * You have to:
     * - handle empty trees
     * - never look at left
     * - never compares values, i.e., the value of t and the right,
     *   because it's not necessary if it's BST.
     * - returns the right value as soon as found
     *
     * @param t is the tree being searched for the max value
     * @return the max value of tree t
     */
    public static int max(MyTree t) {
        // Handle empty tree
        if (t.getEmpty()) {
            throw new IllegalStateException("Tree is empty.");
        }

        // Traverse to the rightmost node for the maximum value
        while (!t.getRight().getEmpty()) {
            t = t.getRight();  // Move to the right subtree
        }
        
        return t.getValue();  // Return the maximum value
    }
}
