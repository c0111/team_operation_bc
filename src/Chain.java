import java.util.ArrayList;
import java.util.Scanner;

public class Chain {

    public static ArrayList<Block> chain = new ArrayList<Block>();

    public static void main(String[] args) {

        Boolean isEnteringData = true;
        chain.add(new Block("Test data block one.", "0"));

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

        System.out.println(chain);

    }

}


//                    Block genesisBlock = new Block("Patient one test data", "0");
//                    System.out.println("Hash for block 1 : " + genesisBlock.hash);
//
//                    Block secondBlock = new Block("Patient two test data", genesisBlock.hash);
//                    System.out.println("Hash for block 2 : " + secondBlock.hash);
//
//                    Block thirdBlock = new Block("Patient three test data", secondBlock.hash);
//                    System.out.println("Hash for block 3 : " + thirdBlock.hash);