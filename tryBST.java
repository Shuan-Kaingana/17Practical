//Claude AI was used to help structure and fix errors in this code

import java.util.LinkedList;
import java.util.Queue;

class tNode {
 
    private long    key;
    private String  data;
    private tNode   parent;
    private tNode   left;
    private tNode   right;
 
    public tNode() {
        key = 0; data = ""; parent = null; left = null; right = null; }
 
    public tNode(long k, String d) {
        key = k; data = d; parent = null; left = null; right = null; }
 
    public long   getKey()    { return key; }
    public String getData()   { return data; }
    public tNode  getLeft()   { return left; }
    public tNode  getRight()  { return right; }
    public tNode  getParent() { return parent; }
 
    public void setKey(long k)     { key    = k; }
    public void setData(String d)  { data   = d; }
    public void setLeft(tNode n)   { left   = n; }
    public void setRight(tNode n)  { right  = n; }
    public void setParent(tNode n) { parent = n; }
}

class Tree {

    private static final int N = 1 << 21;
    private int   length;
    private tNode root;
 
    public Tree() { root = null; length = 0; }
 
    public void  setRoot(tNode n) { root = n; }
    public tNode getRoot()        { return root; }

    public boolean isBST() {
        return isBSTRec(root, Long.MIN_VALUE, Long.MAX_VALUE); }
 
    private boolean isBSTRec(tNode here, long min, long max) {
        if (here == null) return true;
        if (here.getKey() <= min || here.getKey() >= max) return false;
        return isBSTRec(here.getLeft(),  min,           here.getKey())
            && isBSTRec(here.getRight(), here.getKey(), max); }

    public void insert(tNode n) {
        if (root == null) {
            root = n;
            root.setParent(null); }
        else
            insert(root, n);
        length++; }
 
    public void insert(long k, String d) {
        tNode n = new tNode(k, d);
        insert(n); }
 
    private void insert(tNode here, tNode n) {
        if (n.getKey() < here.getKey())
            if (here.getLeft() == null) {
                n.setParent(here);
                here.setLeft(n); }
            else insert(here.getLeft(), n);
        else
            if (here.getRight() == null) {
                n.setParent(here);
                here.setRight(n); }
            else insert(here.getRight(), n); }

    public void clear() { root = null; length = 0; }

    public void populatePerfect(long low, long high) {
        Queue<long[]> q = new LinkedList<>();
        q.add(new long[]{ low, high });
        while (!q.isEmpty()) {
            long[] range = q.poll();
            long lo = range[0], hi = range[1];
            if (lo > hi) continue;
            long mid = lo + (hi - lo) / 2;
            insert(mid, Long.toString(mid));
            q.add(new long[]{ lo,      mid - 1 });
            q.add(new long[]{ mid + 1, hi      }); } }

    private tNode find(long k) {
        tNode here = root;
        while (here != null && here.getKey() != k)
            here = (k < here.getKey()) ? here.getLeft() : here.getRight();
        return here; }

    private void updateTree(tNode n, tNode p) {
        if (n.getParent() == null) {
            root = p;
            if (root != null) root.setParent(null); }
        else if (n.getKey() < n.getParent().getKey())
            n.getParent().setLeft(p);
        else
            n.getParent().setRight(p); }

    public void tDelete(long k) {
        tNode n = find(k);
        if (n == null) { return; }
 
        if (n.getLeft() == null)
            updateTree(n, n.getRight());
        else if (n.getRight() == null)
            updateTree(n, n.getLeft());
        else {
            tNode here = n.getLeft();
            while (here.getRight() != null)
                here = here.getRight();
            updateTree(here, here.getLeft());
            n.setKey(here.getKey());
            n.setData(here.getData()); }
 
        length--; }

    public void removeEvens() { removeEvensRec(root); }
 
    private void removeEvensRec(tNode here) {
        if (here == null) return;
        tNode l = here.getLeft();
        tNode r = here.getRight();
        removeEvensRec(l);
        removeEvensRec(r);
        if (here.getKey() % 2 == 0)
            tDelete(here.getKey()); }

    public int     size()    { return length; }
    public boolean isEmpty() { return length == 0; }
    public boolean isFull()  { return length == N; }

    public void display() {
        if (root == null) System.out.println("Tree is empty.");
        else              display(root); 
    }
 
    private void display(tNode n) {
        if (n != null) {
            display(n.getLeft());
            System.out.print("In Tree: Key=" + n.getKey() + " Data=" + n.getData());
            if (n.getParent() == null)
                System.out.println(" Parent.key=null");
            else
                System.out.println(" Parent.key=" + n.getParent().getKey());
            display(n.getRight()); 
        } 
    }
}