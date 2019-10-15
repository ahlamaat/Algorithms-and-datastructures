public interface BSTOper {
  public void add(int v);
  public boolean remove(int v);
  public int size();
  public boolean existsInTree(int v);
  public void addAll(int[] integers);
  public int findNearestSmallerThan(int v);
  public int[] sortedArray(); //inorder
  public int[] findInRange(int low, int high);
}
