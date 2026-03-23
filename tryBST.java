// Assisted by Claude (claude.ai) - Anthropic (free version)
 
import java.util.LinkedList;
import java.util.Queue;

class tNode {
    int key;
    tNode left, right;
 
    tNode(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }
}

public class tryBST{
    public void insert(int key) {
        root = insertRec(root, key);
    }
 
    private tNode insertRec(tNode node, int key) {
        if (node == null) return new tNode(key);
        if (key < node.key)      node.left  = insertRec(node.left,  key);
        else if (key > node.key) node.right = insertRec(node.right, key);
        return node;
    }

    private boolean search(tNode node, int key){
        if( root == null )
            return false;
        
        if ( root.data == key)
            return true;

        if (key > root.data)
            return search(root.right, key);

        return search(root.left, key);
    }

        public void removeEvens() {
        root = removeEvensRec(root);
    }
 
    private tNode removeEvensRec(tNode node) {
        if (node == null) return null;
        // First recurse on children
        node.left  = removeEvensRec(node.left);
        node.right = removeEvensRec(node.right);
        // Then handle this node if even
        if (node.key % 2 == 0) {
            return deleteRec(node, node.key);
        }
        return node;
    }
}
