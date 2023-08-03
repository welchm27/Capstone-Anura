import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameLogic {

    public static String filePath = "src/main/resources/SplashPage.txt";
    public static int[] helpLines = {28, 29, 30, 31};

    public void execute(){
        //printSplashPage(filePath)
        //printHelp(filePath, 28, 31)

    }
    public static void printSplashPage(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }
    }
    public static void printHelp (String filePath, int startLine, int endLine ) throws IOException {
        try (BufferedReader reader =  new BufferedReader(new FileReader(filePath))){
            String line;
            int lineNumber = 0;

            while((line = reader.readLine()) != null) {
                if (lineNumber >= startLine && lineNumber <=endLine) {
                    System.out.println(line);
                }
                lineNumber++;
                if(lineNumber > endLine){
                    break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}