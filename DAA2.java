package mytree;

public class DAA2 extends DAA1 {

    // 4. isHeightBalanced() [10 points]
    public static Boolean isHeightBalanced(MyTree t) {
        return isHeightBalanced(t, new int[1]);
    }

    private static Boolean isHeightBalanced(MyTree t, int[] height) {
        if (t.getEmpty()) {
            height[0] = 0;
            return true;
        }

        int[] leftHeight = new int[1];
        int[] rightHeight = new int[1];

        boolean leftBalanced = isHeightBalanced(t.getLeft(), leftHeight);
        boolean rightBalanced = isHeightBalanced(t.getRight(), rightHeight);

        height[0] = Math.max(leftHeight[0], rightHeight[0]) + 1;

        return leftBalanced && rightBalanced && Math.abs(leftHeight[0] - rightHeight[0]) <= 1;
    }

    // 5. insertHB() [10 points]
    public static MyTree insertHB(int n, MyTree t) {
        if (t.getEmpty()) {
            return new MyTree(n, new MyTree(), new MyTree());
        }

        if (n <= t.getValue()) {
            t = new MyTree(t.getValue(), insertHB(n, t.getLeft()), t.getRight());
        } else {
            t = new MyTree(t.getValue(), t.getLeft(), insertHB(n, t.getRight()));
        }

        return rebalance(t);
    }

    // Rebalancing logic (used by insertHB and deleteHB)
    private static MyTree rebalance(MyTree t) {
        int balance = MyTreeOps.height(t.getLeft()) - MyTreeOps.height(t.getRight());

        if (balance > 1) {
            if (MyTreeOps.height(t.getLeft().getLeft()) >= MyTreeOps.height(t.getLeft().getRight())) {
                return rebalanceForRight(t); // single right
            } else {
                MyTree newLeft = rebalanceForLeft(t.getLeft());
                return rebalanceForRight(new MyTree(t.getValue(), newLeft, t.getRight()));
            }
        }

        if (balance < -1) {
            if (MyTreeOps.height(t.getRight().getRight()) >= MyTreeOps.height(t.getRight().getLeft())) {
                return rebalanceForLeft(t); // single left
            } else {
                MyTree newRight = rebalanceForRight(t.getRight());
                return rebalanceForLeft(new MyTree(t.getValue(), t.getLeft(), newRight));
            }
        }

        return t;
    }

    // 6. rebalanceForLeft() [15 points] → single left rotation
    private static MyTree rebalanceForLeft(MyTree t) {
        MyTree right = t.getRight();
        MyTree newRightLeft = right.getLeft();

        return new MyTree(right.getValue(), new MyTree(t.getValue(), t.getLeft(), newRightLeft), right.getRight());
    }

    // 7. rebalanceForRight() [15 points] → single right rotation
    private static MyTree rebalanceForRight(MyTree t) {
        MyTree left = t.getLeft();
        MyTree newLeftRight = left.getRight();

        return new MyTree(left.getValue(), left.getLeft(), new MyTree(t.getValue(), newLeftRight, t.getRight()));
    }

    // 8. deleteHB() [10 points]
    public static MyTree deleteHB(MyTree t, int x) {
        if (t.getEmpty()) {
            return t;
        }

        if (x < t.getValue()) {
            t = new MyTree(t.getValue(), deleteHB(t.getLeft(), x), t.getRight());
        } else if (x > t.getValue()) {
            t = new MyTree(t.getValue(), t.getLeft(), deleteHB(t.getRight(), x));
        } else {
            if (t.getLeft().getEmpty() && t.getRight().getEmpty()) {
                return new MyTree(); // no children
            } else if (t.getLeft().getEmpty()) {
                return t.getRight(); // one child
            } else if (t.getRight().getEmpty()) {
                return t.getLeft(); // one child
            } else {
                MyTree successor = findMin(t.getRight());
                t = new MyTree(successor.getValue(), t.getLeft(), deleteHB(t.getRight(), successor.getValue()));
            }
        }

        return rebalance(t);
    }

    private static MyTree findMin(MyTree t) {
        while (!t.getLeft().getEmpty()) {
            t = t.getLeft();
        }
        return t;
    }
}
