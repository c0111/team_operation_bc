import java.util.ArrayList;
import java.util.Scanner;

public class Chain {

    public static ArrayList<Block> chain = new ArrayList<Block>();

    public static void main(String[] args) {

        int miningDifficulty = 5;

        //Initial block
        chain.add(new Block("Test data block one.", "0"));

        //Generate test blocks
        for (int i = 0; i < 25; i++) {
            chain.add(new Block("Data test block" + Integer.toString(i), chain.get(chain.size()-1).hash));
        }

        //Add patient data using command line.
        userInput();


        for (int i=0; i < chain.size(); i++){
            System.out.println(chain.get(i).calculateHash());
        }

        //Mine blocks
        for (int i = 0; i < chain.size(); i++) {
            chain.get(i).mine(miningDifficulty);
        }

        //Blockchain
        System.out.println(chain + "\n");
    }

    public static void userInput() {

        Boolean isEnteringData = true;
        //chain.add(new Block("Test data block one.", "0"));

        while(isEnteringData) {

            System.out.println("Please enter patient data.");
            Scanner scan = new Scanner(System.in);
            String Data = scan.next();
            chain.add(new Block(Data, chain.get(chain.size()-1).hash));

            System.out.println("Continue Entering Data? ( y / n )");
            Scanner scanTwo = new Scanner(System.in);
            String isContinuing = scanTwo.next();

            if (isContinuing.equals("n")) {
                isEnteringData = false;
            }
            else if (!isContinuing.equals("y"))
                throw new IllegalArgumentException("Invalid Input. ");
        }
    }

}