import java.util.Scanner;

public class GameLogic {
    public void execute(){

        String[] stat;

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        while(userInput.equals("Quit")){
            System.out.println("Enter [New] for new game or [Save] to load saved game:");
            userInput = scanner.nextLine();
            if(userInput.equals("New")){
                stat = new String[]{};
            }
        }
    }
}