import com.google.api.client.json.Json;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CountDownLatch;


public class Patient implements Serializable {

    public String ssn;
    public String firstName;
    public String lastName;
    public String birthDate;
    public String diagnosis;
    public String treatment;
    public int recordNumber;

    public static Chain patientChain;
    private static final Map<String, String> ssnMap;
    static
    {
        ssnMap = new HashMap<>();
        //ssnMap.put("123456789", "01/02/90");
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        int miningDifficulty = 5;
        String ssn = "123456789";
        String firstName = "John";
        String lastName = "Doe";
        String birthDate = "01/02/90";
        String diagnosis = "diag";
        String treatment = "treat";
        int recordNumber = 0;

        FileInputStream serviceAccount = new FileInputStream("src/JSON/operationblockchain-db72b-firebase-adminsdk-yupiv-d20a544616.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://operationblockchain-db72b.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);


        Patient patient = new Patient(ssn, firstName, lastName, birthDate, diagnosis, treatment, recordNumber);

        //  Add new patient to chain, or add new data to existing patient record.
        addPatientData(patient.ssn, patientChain, patient);

        //  Retrieve patient data.
        retrieveData(ssn);

        //Add patient data using command line.
        //userInput();

        //Mine blocks
//        for (int i = 0; i < chain.size(); i++) {
//            chain.get(i).mine(miningDifficulty);
//        }

        //  JSON Blockchain
        //String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
        //System.out.println(blockchainJson);

    }


    public Patient(String ssn, String fName, String lName, String dob, String diagnosis, String treatment, int recordNumber) {

        this.ssn = ssn;
        this.firstName = fName;
        this.lastName = lName;
        this.birthDate = dob;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.recordNumber = recordNumber;

    }

//    public static ArrayList<String> getPatientData(){
//
//    }

    public static void retrieveData(String ssn) throws InterruptedException {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final CountDownLatch latch = new CountDownLatch(1);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String retrievedPatientData = (String)dataSnapshot.child("BlockChain").child(ssn).getValue();
                Block[] blocks = new Gson().fromJson(retrievedPatientData, Block[].class);
                for (Block b : blocks) {
                    String patientData = new GsonBuilder().setPrettyPrinting().create().toJson(b.data);
                    System.out.println("TEST: "+patientData);
                }

                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("onCancelled TEST");
            }

        });
        latch.await();

    }


    public static void addPatientData(String ssn,Chain patientChain, Patient patient) throws InterruptedException {

        Map<String, Object> patientUpdates = new HashMap<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference patientRef = ref.child("BlockChain");

        if (!ssnMap.containsKey(ssn)){
            patientChain.chain.add(new Block(patient, "0"));
        } else patientChain.chain.add(new Block(patient, patientChain.chain.get(patientChain.chain.size()-1).hash));

        String jsonChain = new Gson().toJson(patientChain.chain);
        System.out.println(jsonChain);
        patientUpdates.put(ssn, jsonChain);
        final CountDownLatch latch = new CountDownLatch(1);
        patientRef.updateChildren(patientUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                    latch.countDown();
                } else {
                    System.out.println("Data saved successfully.");
                    latch.countDown();
                }
            }
        });
        latch.await();
    }


}
