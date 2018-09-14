

public class Chain {

    public static void main(String[] args) {

        Block genesisBlock = new Block("Patient one test data", "0");
        System.out.println("Hash for block 1 : " + genesisBlock.hash);

        Block secondBlock = new Block("Patient two test data",genesisBlock.hash);
        System.out.println("Hash for block 2 : " + secondBlock.hash);

        Block thirdBlock = new Block("Patient three test data",secondBlock.hash);
        System.out.println("Hash for block 3 : " + thirdBlock.hash);

    }

}
