package com.os;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OS {

    Hashtable<String, Gen> memory;
    private String globalPath;
    private Scanner sc;


    public OS(){
        sc = new Scanner(System.in);
    }


    public void loadProgrames(String programs[]){

    }


    public int[] getPIDs() {
        return null;
    }

    public String nextInst(int PID) {
        return null;
    }


    public boolean isFinished(int pID) {
        return false;
    }


    public void store(String var, Gen value){
        this.memory.put(var, value);
        this.globalPath = System.getProperty("user.dir");
    }

    public Gen load(String var){
        return this.memory.get(var);
    }

    public void print(String statment){
        System.out.println(statment);
    }

    public String input(){
        String val = sc.nextLine();
        return val;
    }

    public void initMemory(){
        this.memory = new Hashtable<>();
    }

    public boolean exists(String var){
        return this.memory.containsKey(var);
    }

    public String readFile(String fileName) throws IOException{
		String res = new String(Files.readAllBytes(Paths.get(this.globalPath, fileName)));
		
		return res;
    }

    public void WriteFile(String fileName, String value) throws IOException{
		Files.write(Paths.get(this.globalPath, fileName), value.getBytes());
    }

    public void closeInputStream(){
        this.sc.close();
    }
}
