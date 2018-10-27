import java.util.ArrayList;
import java.util.Date;

public class Block {

    ArrayList<String> data;
    public String hash;
    public String previousHash;
    private long timeStamp;
    private int key;


    public Block(Patient patient, String previousHash) {
        this.data = getPatientData(patient);
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public static ArrayList<String> getPatientData(Patient patient){
        ArrayList<String> patientData = new ArrayList<>();
        patientData.add(patient.ssn);
        patientData.add(patient.firstName);
        patientData.add(patient.lastName);
        patientData.add(patient.birthDate);
        patientData.add(patient.diagnosis);
        patientData.add(patient.treatment);
        return patientData;
    }

//    public static int getRecordNumber(Patient patient) {
//        return patient.recordNumber;
//    }


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