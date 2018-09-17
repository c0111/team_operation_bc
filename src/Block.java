import java.util.Date;

public class Block {

    private String data;
    public String hash;
    public String previousHash;
    private long timeStamp;
    private int key;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringSHA.applySha256(
                Integer.toString(key) +
                        data +
                        previousHash +
                        Long.toString(timeStamp));
    }

    public void mine(int difficulty) {

        char[] miningDifficultyString = new char[difficulty];

        String mineFor = new String(miningDifficultyString).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(mineFor)) {
            key ++;
            hash = calculateHash();
        }

        System.out.println("Block successfully mined : " + hash);
    }

}
