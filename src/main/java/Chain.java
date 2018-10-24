//import com.google.gson.GsonBuilder;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Chain {

    public static ArrayList<Block> chain = new ArrayList<Block>();
    public static ArrayList<String> chainData = new ArrayList<String>();
    public static String[] chainArray;


    public static void main(String[] args) throws IOException, InterruptedException {

        String ssn = "123456789";
        //String firstNAme = args[1];
        //String lastName = args[2];
        //String birthDate = args[3];
        //String diagnosis = args[4];
        //String treatment = args[5];


        FileInputStream serviceAccount = new FileInputStream("src/JSON/operationblockchain-db72b-firebase-adminsdk-yupiv-d20a544616.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://operationblockchain-db72b.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);

        List<String> blockChain = new ArrayList<>();
        blockChain.clear();

        //  Retrieve patient data.
        retrieveData(ssn);


        int miningDifficulty = 5;

        //System.out.println(chainArray);

        //Initial block
        //chain.add(new Block("Test data genesis block", "0"));

        //Generate test blocks
        //for (int i = 0; i < 5; i++) {
        //    chain.add(new Block("Data test block " + Integer.toString(i + 2), chain.get(chain.size()-1).hash));
        //}

        //Add patient data using command line.
        //userInput();

        //Mine blocks
//        for (int i = 0; i < chain.size(); i++) {
//            chain.get(i).mine(miningDifficulty);
//        }

        //Blockchain (Json)
        //String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(chain);
        //System.out.println(blockchainJson);

    }


    public static void retrieveData(String ssn) throws InterruptedException {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final CountDownLatch latch = new CountDownLatch(1);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> patientDataMap = (Map)dataSnapshot.child("blockChain").getValue();
                ArrayList<String> patientData = getPatientData(patientDataMap, ssn);
                System.out.println("TEST: "+patientData);
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("onCancelled TEST");
            }

        });
        latch.await();


    }

    public static ArrayList<String> getPatientData(Map patientMap, String ssn){
        ArrayList<String> patientData = new ArrayList<>();
        System.out.println(patientMap);
        if(patientMap.containsKey(ssn)){
            String[] dataArray = patientMap.get(ssn).toString().split(",");
            for (int d = 1; d <= 5; d++) {
                patientData.add(dataArray[d]);
            }
        } else{
            //  New Patient.
        }
        return patientData;
    }


    public static ArrayList<String> getChainData(){

        chainData.clear();

        for (int i = 0; i < chain.size(); i++) {
            chainData.add(chain.get(i).getData());
        }

        return chainData;
    }

    public static void userInput() {

        Boolean isEnteringData = true;
        //chain.add(new Block("Test data block one.", "0"));

        while(isEnteringData) {

            System.out.println("Please enter patient data.");
            Scanner scan = new Scanner(System.in);
            String Data = scan.nextLine();
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