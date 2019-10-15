import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Sorting {
  int i = 0;
  int size;

  public static void main(String[] args) {
    int length;
    if(args.length > 0){
      length = Integer.parseInt(args[0]);
    } else {
        System.out.println("FEIL! Riktig bruk av programmet: "
                           +"java Main <tall>");
        return;
    }
    Sorting sorting = new Sorting(length);
    sorting.testRandom();
    sorting.testRising();
    sorting.testFalling();
  }

  public Sorting(int size){
    this.size = size;
  }

  public int[] makeRandom(){
    int[] test = new int[size];
    for(int i = 0; i < test.length; i++){
      Random rand = new Random();
      //tallene blir mellom verdiomraadet 0-9
      test[i] = rand.nextInt();
    }
    return test;
  }

  public int[] risingList(){
    int[] test = new int[size];
    for(int i = 0; i < size; i++){
      test[i] =i;
    }
    return test;
  }

  public int[] fallingList(){
    return reverse(risingList());
  }

  private int[] reverse(int[] array) {
    int j = array.length-1;
    for (int i = 0; i < array.length / 2; i++, j--) {
        swap(array, i, j);
    }
    return array;
  }

  //Liste med random verdier
  public void testRandom(){
    System.out.println("\nArray with random values: ");
    System.out.println("TIME FOR SELECTION SORT: ");
    System.out.println(selectionTest(makeRandom()));
    System.out.println("TIME FOR INSERTION SORT: ");
    System.out.println(insertionTest(makeRandom()));
    System.out.println("TIME FOR QUICK SORT: ");
    System.out.println(quicksortTest(makeRandom()));
    System.out.println("TIME FOR BUCKET SORT: ");
    System.out.println(bucketTest(makeRandom()));
    System.out.println("TIME FOR ARRAYS.SORT: ");
    long t = System.nanoTime();
    Arrays.sort(makeRandom());
    double tid = (System.nanoTime()-t)/10000000.0;
    System.out.println(tid);
  }

  //Sortert liste med økende verdier
  public void testRising(){
    System.out.println("\nAlready sorted array with rising values");
    System.out.println("TIME FOR SELECTION SORT: ");
    System.out.println(selectionTest(risingList()));
    System.out.println("TIME FOR INSERTION SORT: ");
    System.out.println(insertionTest(risingList()));
    System.out.println("TIME FOR QUICK SORT: ");
    System.out.println(quicksortTest(risingList()));
    System.out.println("TIME FOR BUCKET SORT: ");
    System.out.println(bucketTest(risingList()));
    System.out.println("TIME FOR ARRAYS.SORT: ");
    long t = System.nanoTime();
    Arrays.sort(risingList());
    double tid = (System.nanoTime()-t)/10000000.0;
    System.out.println(tid);

  }

  //Sortert liste med fallende verdier
  public void testFalling(){
    System.out.println("\nAlready sorted array with falling values");
    System.out.println("TIME FOR SELECTION SORT: ");
    System.out.println(selectionTest(fallingList()));
    System.out.println("TIME FOR INSERTION SORT: ");
    System.out.println(insertionTest(fallingList()));
    System.out.println("TIME FOR QUICK SORT: ");
    System.out.println(quicksortTest(fallingList()));
    System.out.println("TIME FOR BUCKET SORT: ");
    System.out.println(bucketTest(fallingList()));
    System.out.println("TIME FOR ARRAYS.SORT: ");
    long t = System.nanoTime();
    Arrays.sort(fallingList());
    double tid = (System.nanoTime()-t)/10000000.0;
    System.out.println(tid);

  }

  public void swap(int[] array, int a, int b){
    int tmp = array[a];
    array[a] = array[b];
    array[b] = tmp;
  }

  public void generalPrint(int[] array){
    System.out.println("\nTesting sorting algorithm ... ");
    System.out.println("Unsorted array: ");
    System.out.println(Arrays.toString(array));
  }

  public int[] selectionSort(int[] array){

    int length = array.length;

    for(int i = 0; i < length-1; i++){
      //System.out.println("Iteration " + (i+1) + ":");

      //finner minste tallet i det usorterte arrayet
      int min = i;

      for(int j = i+1; j < length; j++){

        if(array[j] < array[min]){
          min = j;
        }
      }

      //swapper det minste (funnet) elementet med det forste elementet.
      swap(array, min, i);
      //System.out.println(Arrays.toString(array));
    }
    return array;
  }

  //Tester selection sort
  public double selectionTest(int[] array){
    //generalPrint(array);
    //System.out.println("\nSorted array using selection-sort: ");
    long t = System.nanoTime();
    selectionSort(array);
    double tid = (System.nanoTime()-t)/10000000.0;
    return tid;
  }
  public int[] insertionSort(int[] array){

    int length = array.length;

    for(int i = 1; i < length; i++){
      //System.out.println("Iteration " + i + ":");

      int key = array[i];
      int j = i-1;

      //flytter elementene som er storre enn key ett hakk opp
      while(j>= 0 && key < array[j]){
        array[j+1] = array[j];
        j = j-1;
      }
      array[j+1] = key;
      //System.out.println(Arrays.toString(array));
    }
    return array;
  }

  //Tester insertion sort
  public double insertionTest(int[] array){
    //generalPrint(array);
    //System.out.println("\nSorted array using insertion-sort: ");
    long t = System.nanoTime();
    insertionSort(array);
    double tid = (System.nanoTime()-t)/10000000.0;
    return tid;
  }

  public void correctInPlaceQuickSort(int[] array, int from, int to){

    int left;
    while(from < to){
      left = inPlacePartition(array, from, to);

      if(left-from < to-left){
        correctInPlaceQuickSort(array, from, left-1);
        from = left+1;
      } else{
        correctInPlaceQuickSort(array, left+1, to);
        to = left-1;
      }
    }
  }

  public void inPlaceQuickSort(int[] array, int from, int to){
    if(from >= to){
      return;
    }
    int left = inPlacePartition(array, from, to);
    inPlaceQuickSort(array, from, left-1);
    inPlaceQuickSort(array,left+1, to);
  }

  public int inPlacePartition(int[] array, int from, int to){
    int pivot = array[to];
    int left = from;
    int right = to -1;

    while(left <= right){
      i++;
    //  System.out.println("Iteration " + i + ":");

      while(left <= right && array[left] <= pivot){
        left++;
      }
      while(right >= left && array[right] >= pivot){
        right--;
      }
      if(left < right){
        swap(array, left, right);
      }
    //System.out.println(Arrays.toString(array));

    }
    swap(array, left, to);
  //  System.out.println(Arrays.toString(array));

    return left;
  }

  //Tester quick sort
  public double quicksortTest(int[] array){
    //generalPrint(array);
  //  System.out.println("\nSorted array using quicksort: ");
    long t = System.nanoTime();
    correctInPlaceQuickSort(array, 0, array.length-1);
    double tid = (System.nanoTime()-t)/10000000.0;
    return tid;
  }

public int[] bucketSort(int[] array){

  int min = array[0], max = array[0];
  for(int value : array){
    if(value > max){
      max = value;
    }
    if(value < min){
      min = value;
    }
  }

  List<List<Integer>> bucket = new ArrayList<List<Integer>>(max);
  for (int i = 0; i <= max; i++) {
    bucket.add(new ArrayList<Integer>());
  }

  for(int i = 0; i < array.length; i++){
    int key = array[i];
    bucket.get(key).add(key);
    array[i] = 0;
  }

  int index = 0;
  for(int i = 0; i <= max; i++){
  //  System.out.println("Iteration " + i + ": ");
    List<Integer> list = bucket.get(i);
    while(!list.isEmpty()){
      int value = list.remove(0);
      array[index++] = value;
    }
  //  System.out.println(Arrays.toString(array));

  }
  return array;
}

  //Tester bucket sort
  public double bucketTest(int[] array){
  //  generalPrint(array);
  //  System.out.println("\nSorted array using bucketsort: ");
    long t = System.nanoTime();
    bucketSort(array);
    double tid = (System.nanoTime()-t)/10000000.0;
    return tid;
  }
}
