import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


  //Realizability
  //Optimal time schedule
  //Latest start, slack and critical tasks??


public class Project {
  int nrOfTasks;
  int earliestCompletion = 0;
  HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
  Task startTask;
  Stack<Task> schedule = new Stack<>();

  public void readFile(File file) throws FileNotFoundException{
    Scanner sc = new Scanner(file);
    String[] lines = sc.nextLine().split("\\s+");
    nrOfTasks = Integer.parseInt(lines[0]);

    while(sc.hasNextLine()){
      //splitter paa alle whitspaces
      lines = sc.nextLine().split("\\s+");
      if(lines.length > 0 && lines[0].trim().length() != 0){
        int id = Integer.parseInt(lines[0]);
        String name = lines[1];
        int time = Integer.parseInt(lines[2]);
        int manpower = Integer.parseInt(lines[3]);
        int dependency = Integer.parseInt(lines[4]);

        ArrayList<Integer> dependencies = new ArrayList<Integer>();
        int index = 5;
        while(dependency != 0){
          dependencies.add(dependency);
          dependency = Integer.parseInt(lines[index]);
          index++;
        }

        Task task = new Task(id, name, time, manpower, dependencies);
        if(dependencies.size() == 0){
          startTask = task;
        }
        tasks.put(id, task);
      }
    }
    //lager outedges og inedges
     for(Task task : tasks.values()) {
      for(Integer dependent : task.dependencies){
        Task predecessor = tasks.get(dependent);
        task.inedges.add(predecessor);
        predecessor.outedges.add(task);
        predecessor.nrOutedges++;
      }
    }
  }



  private class Task{
    int id, time, staff;
    String name;
    int earliestStart, latestStart;
    List<Task> outedges;
    int nrOutedges;
    List<Task> inedges = new ArrayList<Task>();
    List<Integer> dependencies;
    int cntPredecessors;
    boolean critical = false;
    int slack;
    int state = 0;

    Task(int id, String name, int time, int staff, ArrayList dependencies){
      this.id = id;
      this.name = name;
      this.time = time;
      this.staff = staff;
      latestStart = 0;
      outedges = new ArrayList<Task>();
      this.dependencies = dependencies;
      cntPredecessors = dependencies.size();
    }
  }

  //realizable -- leter etter lokke
  public boolean hasCycle(Task task, ArrayList<Task> log){
    log.add(task);
    //0 = usett, 1 = ferdig, -1 = igang

    if(task.state == -1){
      System.out.println("Cycle found: ");

      for(int i = 0; i < log.size(); i++){
        if(log.get(i).id == task.id){
          for(int j = i; j < log.size()-1; j++){
            System.out.println(log.get(j).id + " ");
          }
        }
      }
      System.out.println(task.id);
      return true;
    } else if(task.state == 0){
      task.state = -1;
      for(Task neighbour : task.outedges){
        if(hasCycle(neighbour, log)){
          return true;
        }
      }
    } else if(task.outedges.isEmpty()){
      task.state = 1;
      log.remove(task);
    }
    task.state = 1;
    log.remove(task);
    return false;
  }
  /*
  public boolean hasCycle(Task task, String cycle){
    //0 = usett, 1 = ferdig, -1 = igang

    String actualCycle = "";

    if(task.state == -1){
      cycle += Integer.toString(task.id);
      String cycleArr[] = cycle.split(" ");
      for(int i = 0; i < cycle.length(); i++){
        if(cycle.charAt(i) == (char) task.id){
          actualCycle = cycle.substring(i, cycle.length()-1);
        }
      }
      System.out.println("Found a cycle: ");
      System.out.println(actualCycle);
      return true;

    } else if(task.state == 0){
      cycle += Integer.toString(task.id) + " ";
      task.state = -1;

      for(Task neighbour : task.outedges){
        if(hasCycle(neighbour, cycle)){
          return true;
        }
      }
      task.state = 1;
    }
    return false;
  }
*/
  //realizable
  public boolean realizable(){
    //String cycle = "";
    ArrayList<Task> cycle = new ArrayList<>();
    if(!hasCycle(startTask, cycle)){
      System.out.println("No cycles detected!\n");
      return true;
    }

    return false;
  }

  //Optimal time schedule
  public void printInfo(){
    int currentStaff = 0;
    for(int i = 0; i <= earliestCompletion; i++){
      boolean alreadyPrinted = false;

      for(Task task : tasks.values()){
        if(task.earliestStart == i){

          if(!alreadyPrinted){
            System.out.println("Time: " + i);
            alreadyPrinted = true;
          }
          System.out.println("     Starting: " + task.id);
          currentStaff += task.staff;
        } else if(task.earliestStart + task.time == i){
          if(!alreadyPrinted){
            System.out.println("Time: " + i);
            alreadyPrinted = true;
          }
          System.out.println("     Finished: " + task.id);
          currentStaff -= task.staff;
        }
      }
      if(alreadyPrinted){
        System.out.println("     Current staff: " + currentStaff + "\n");
      }
    }
    System.out.println("**** Shortest possible project execution is " + earliestCompletion + " **** \n");

    System.out.println("**** Tasks in this project ****\n");
    for(Task task : tasks.values()){
      String theOutedges = "";
      for(Task outTask : task.outedges){
        theOutedges += outTask.id + " ";
      }
      System.out.println("Identity number: " + task.id + "\n     Name: " + task.name +
      "\n     Time needed to finish task: " + task.time + "\n     Manpower: " +
      task.staff + "\n     Earliest start time: " + task.earliestStart +
      "\n     Latest start time: " + task.latestStart + "\n     Slack: " + task.slack +
      "\n     Tasks that depend on THIS task: " + theOutedges + "\n     Critical: " +
      task.critical);
    }
  }
  //optimal time schedule og earliest start
  public void topSortEarliest(){
    Task t;
    for(Task task : tasks.values()){
      t = task;

      if(t.cntPredecessors == 0){
        schedule.push(t);
      }
    }

    while(!schedule.empty()){
      t = schedule.pop();

     for(Task task : t.outedges){

        task.cntPredecessors--;

        if(task.earliestStart < (t.earliestStart + t.time)){
          task.earliestStart = t.earliestStart+t.time;
        }

        if(task.earliestStart + task.time > this.earliestCompletion){
          earliestCompletion = task.earliestStart + task.time;
        }

          if(task.cntPredecessors == 0){
            schedule.push(task);

          }
      }
    }

  }

  //optimal time schedule og slack
  public void topSortLatest(){
    Task t;
    for(Task task : tasks.values()){
      t = task;
      task.latestStart = earliestCompletion;
      if(t.nrOutedges == 0){
        t.latestStart = this.earliestCompletion - t.time;
        schedule.push(t);
      }
    }

    while(!schedule.empty()){
      t = schedule.pop();

     for(Task task : t.inedges){

        task.nrOutedges--;

        if(task.latestStart > (t.latestStart-task.time)){
          task.latestStart = t.latestStart - task.time;
        }

        if(task.nrOutedges == 0){
          schedule.push(task);
          }
      }
    }

    //slack og critical
    for(Task task : tasks.values()){
      task.slack = task.latestStart - task.earliestStart;
      if(task.slack == 0){
        task.critical = true;
      }
    }
  }
  //time schedule
  public void timeSchedule(){
    Task theTask;
    int time = 0;

    if(!realizable()){
      System.out.println("Couldn't find optimal time schedule due to cycle(s)!");
    } else{
      System.out.println("**** Optimal schedule ****");
      //(setter earliest og latest hvis jeg vet at prosjektet er realizble)
      topSortEarliest();
      topSortLatest();

      //printer info hvis og bare hvis prosjektet er realizble.
      printInfo();

    }

  }
}
