
import java.io.File;
import java.io.FileNotFoundException;

public class Oblig2{
  public static void main(String[] args) throws FileNotFoundException {
    String filnavn = null;

    if (args.length > 0) {
        filnavn = args[0];
    } else {
        System.out.println("FEIL! Riktig bruk av programmet: "
                           +"java Main <fil>");
        return;
    }
    File fil = new File(filnavn);

    Project project = new Project();

    project.readFile(fil);
    project.timeSchedule();
  }
}
