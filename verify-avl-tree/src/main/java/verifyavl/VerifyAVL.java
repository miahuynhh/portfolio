package verifyavl;

public class VerifyAVL {
    public static boolean verifyAVL(AVLNode root) {
        /* TODO: Edit this with your code */
//        return (bst && balance && correctHeight);
        if (root != null) {
            return balance(root) && height(root) && bst(root);
        }
        return true;
    }
    /* TODO: Add private methods if you want (recommended) */

    private static boolean bst(AVLNode root) {
        if (root != null) {
            return bstLeft(root.left, root.key)
                    && bstRight(root.right, root.key)
                    && bst(root.left)
                    && bst(root.right);
        }
        return true;
    }

    // everything in left subtree smaller than root (max)
    private static boolean bstLeft(AVLNode root, int max) {
        if (root != null) {
            if ((root.left != null &&  root.left.key >= root.key) ||
                    (root.right != null && root.right.key <= root.key) ||
                    root.key >= max) {
                return false;
            } else {
                return bstLeft(root.left, max) && bstLeft(root.right, max);
            }
        }
        return true;
    }

    // everything in right subtree greater than root (min)
    private static boolean bstRight(AVLNode root, int min) {
        if (root != null) {
            if ((root.left != null &&  root.left.key >= root.key) ||
                    (root.right != null && root.right.key <= root.key) ||
                     root.key <= min) {
               return false;
            } else {
                return bstRight(root.left, min) && bstRight(root.right, min);
            }
        }
        return true;
    }

    private static boolean balance(AVLNode root) {
        return balanceHelper(root);
    }

    private static boolean balanceHelper(AVLNode root) {
        if (root != null && root.height != 0) {
            if (root.left != null && root.right != null) {
                return Math.abs(root.left.height - root.right.height) <= 1 &&
                        balanceHelper(root.left) && balanceHelper(root.right);
            } else if (root.left != null) { // but root.right null
                return root.left.height < 1;
            } else if (root.right != null) { // but root.left null
                return root.right.height < 1;
            }
        }
        return true;
    }

    private static boolean height(AVLNode root) {
        if (root != null) {
            return heightHelper1(root);
        }
        return true;
    }

    private static boolean heightHelper1(AVLNode root) {
        if (root != null) {
            return (root.height == heightHelper2(root))
                    && heightHelper1(root.left)
                    && heightHelper1(root.right);
        }
        return true;
    }

    private static int heightHelper2(AVLNode root) {
        if (root != null) {
            return 1 + Math.max(heightHelper2(root.left), heightHelper2(root.right));
        } else {
            return -1;
        }
    }
}