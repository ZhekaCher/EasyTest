package Data;

import com.google.gson.Gson;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Serialization {
    public static void serialize(Test test){
        //System.getenv("SystemDrive") + System.getenv("HOMEPATH") + "\\Documents\\MarkTracker\\conf.cnf"
        //File file = new File( System.getenv("SystemDrive") + System.getenv("HOMEPATH") + "\\Documents\\EasyTest" + test.getName() + ".test");
        File file = null;
        try {
            file = new File( Global.getTestsDir().toURL().getPath() + test.getName() + ".test");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        ObjectOutputStream objectOutputStream;
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(gson.toJson(test));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static ArrayList <Test> deserializeAll(){
        ArrayList<Test> tests = new ArrayList<>();

        Gson gson = new Gson();
        try {
            for (File file : Global.getTestsDir().listFiles()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                try {
                    tests.add(gson.fromJson((String) objectInputStream.readObject(), Test.class));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tests;
    }
}
