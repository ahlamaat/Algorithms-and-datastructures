import java.util.ArrayList;

public class BSTree implements BSTOper {

  Node root;
  int index;
  int nodes;

  public class Node {
    Node left, right;
    int value;

    Node(int value) {
      this.value = value;
      left = null;
      right = null;
    }

    boolean isLeaf(){
      return (left == null && right == null);
    }

    int minValue(){
      if (left == null){
        return value;
      } else{
        return left.minValue();
      }
    }

    void add(int v){
      if(v <= value) {
        if(left == null){
          left = new Node(v);
        } else {
          left.add(v);
        }
      } else{

        if(right == null){
          right = new Node(v);
        } else{
          right.add(v);
        }
      }
    }
  }

  public BSTree(){
    root = null;
    nodes = 0;
    //index = 0;
  }


  public Node findParent(Node currentNode, Node node) {

    if (currentNode == null || root == node) {
      return null;
    } else {

      //hvis den noden vi er paa sitt barn er lik noden, saa er den noden vi er paa
      //forelederen til noden.
      if(currentNode.left == node || currentNode.right == node){
        return currentNode;
      } else if (currentNode.value < node.value){
        return findParent(currentNode.right, node);
      } else{
        return findParent(currentNode.left, node);
      }
    }
  }

  public Node findParent(Node node){
    return findParent(root, node);
  }

  private Node findGrandparent(Node node) {
    Node parent = findParent(node);
    Node grandparent = findParent(parent);
    return grandparent;
  }

  private Node find(Node node, int v){
    if(node != null){
      if(node.value == v){
        return node;
      } else if(node.value < v){
        return find(node.right, v);
      } else{
        return find(node.left, v);
      }
    }
    return null;
  }

  private Node find(int v) {
    Node node = find(root, v);
    if(node != null){
      return node;
    }
    return null;
  }

  @Override
  public void add(int v) {
    if (root == null){
      root = new Node(v);
    } else{
      root.add(v);
    }
    nodes++;
  }

  private Node remove(Node node, int v){
    //om verdien ikke eksisterer, returnerer jeg null (sjekker kun en gang)
    if(node == root){
      if(!existsInTree(v)){
        return null;
      }
    }

    //hvis (sub)treet er tomt
    if (node == null){
      return node;
    }
    //traverserer gjennom treet
    if(v < node.value){
      node.left = remove(node.left, v);
    } else if(v > node.value){
      node.right = remove(node.right, v);
      //hvis jeg fant verdien som skal slettes
    } else{
      //kun ett barn eller ingen barn
      if(node.left == null){
        return node.right;
      } else if(node.right == null){
        return node.left;
      }
      //to barn
      node.value = findSmallestInRight(node).value;

      node.right = remove(node.right, node.value);
    }

      return node;
    }

    @Override
    public boolean remove(int v){
      if(remove(root, v) != null){
        nodes--;
        return true;
      }
      return false;
    }

  public Node findSmallestInRight(Node node){
      return find(node.right.minValue());
  }

  @Override
  //dersom v er mindre enn den minste verdien i lista
  //saa returnerer jeg den minste verdien i lista.
  public int findNearestSmallerThan(int v){
    int[] sorted = sortedArray();
    int index = 0;


    while(sorted[index] < v && index < sorted.length-1){
      index++;
    }

    if(index == 0 || index == sorted.length-1){
      return sorted[index];
    }

    return sorted[index-1];
  }

  @Override
  public int size(){
    return nodes;
  }

  @Override
  public boolean existsInTree(int v){
    if (find(v) == null){
      return false;
    }
    return true;
  }

  @Override
  public void addAll(int[] integers){
    for(int i = 0; i < integers.length; i++) {
      add(integers[i]);
    }
  }

  private int[] inOrder(Node node, int[] list){

    if(node == null){
      return list;
    }



    inOrder(node.left, list);
    list [index++] = node.value;
    inOrder(node.right, list);
    
    if(size() == index){
      index = 0;
    }

    return list;
  }

  @Override
  public int[] sortedArray() {
    int[] sorted = new int[size()];
    return inOrder(root, sorted);
  }

  //public int[] findInRange(int low, int high)
  public ArrayList<Integer> findInRangeHelper(int low, int high){
    int[] sorted = sortedArray();
    ArrayList<Integer> inRange = new ArrayList<>();

    if(low == high){
      System.out.println("Low- and high variables are the same");
      return inRange;
    }

    for(int i = 0; i < sorted.length; i++){
      if(sorted[i] >= low && sorted[i] <= high){
        inRange.add(sorted[i]);
      }
    }
    return inRange;
  }
  @Override
  public int[] findInRange(int low, int high){
    ArrayList<Integer> inRangeList = findInRangeHelper(low, high);
    int[] inRange = new int[inRangeList.size()];

    for (int i = 0; i < inRangeList.size(); i++){
      inRange[i] = inRangeList.get(i);
    }

    return inRange;
  }
}
