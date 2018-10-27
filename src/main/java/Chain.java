import java.util.ArrayList;

public class Chain {

    public static ArrayList<Block> chain = new ArrayList<>();


    public Chain(ArrayList<Block> chain){
        this.chain = chain;
    }


    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        Boolean isValid = true;

        //loop through chain to validate hashes:
        for(int i=1; i < chain.size(); i++) {
            currentBlock = chain.get(i);
            previousBlock = chain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                isValid = false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                isValid = false;
            }
        }
        return isValid;
    }


//    public static void userInput() {
//
//        Boolean isEnteringData = true;
//        //chain.add(new Block("Test data block one.", "0"));
//
//        while(isEnteringData) {
//
//            System.out.println("Please enter patient data.");
//            Scanner scan = new Scanner(System.in);
//            String Data = scan.nextLine();
//            chain.add(new Block(Data, chain.get(chain.size()-1).hash));
//
//            System.out.println("Continue Entering Data? ( y / n )");
//            Scanner scanTwo = new Scanner(System.in);
//            String isContinuing = scanTwo.next();
//
//            if (isContinuing.equals("n")) {
//                isEnteringData = false;
//            }
//            else if (!isContinuing.equals("y"))
//                throw new IllegalArgumentException("Invalid Input. ");
//        }
//    }

}