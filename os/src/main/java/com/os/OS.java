package com.os;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OS {

    Memory memory;
    private String globalPath;
    private Scanner sc;
    public static int maxLines;
    private int numOfPrograms;
    private Vector<List<String>> programs;



    public OS(){
        sc = new Scanner(System.in);
        this.programs = new Vector<>();
        this.memory = new Memory();
    }

    public int getPrograms(){
        return this.programs.size();
    }


    public void loadProgrames(String programs[]){
        int max = 0; // this.memory.put(var, value);
        this.globalPath = System.getProperty("user.dir");
    	try{
    		for(String program: programs) {
    			List<String> instructions = Files.readAllLines(Paths.get(globalPath, program));
                this.programs.add(instructions);
    			if(instructions.size() > max) 
    				max = instructions.size();
    			
    		}
            
        }catch(IOException e){
            e.printStackTrace();
        }
    	numOfPrograms = programs.length;
    	maxLines= max;

        this.initMemory();

    }

    public String nextInst(int PID) {
        int base = PID * (2 * this.maxLines + 5);
        int pc = (int) this.memory.memory[base + 2].value;
        String inst = (String) this.memory.memory[pc++].value;
        this.memory.memory[base + 2] = new Word("PC", pc);
        return inst;
    }


    public boolean isFinished(int PID) {
        // 34 + 11 -> 45
        int base = PID * (2 * this.maxLines + 5);
        int pc = (int) this.memory.memory[base + 2].value;
        if(((int) this.memory.memory[base + 1].value) == 0) 
            return true;
        if(pc >= (base + (this.maxLines + 5)))
            return true;
        if(this.memory.memory[pc] == null)
            return true;
        return false;

    }

    public void changeState(int PID, int state){
        int base = PID * (2 * this.maxLines + 5);
        this.memory.memory[base + 1] = new Word("_$PState", state);
    }


    public void store(int PID, String var, Gen value){
        this.memory.store(PID, var, value);
    }

    public Gen load(int PID, String var){
        return (Gen) this.memory.load(PID, var);
    }

    public void print(String statment){
        System.out.println(statment);
    }

    public String input(){
        String val = sc.nextLine();
        return val;
    }

    public void initMemory(){
        this.memory.init(this.numOfPrograms, this.maxLines, this.programs);
    }

    public boolean exists(int pid, String var){
        return this.memory.exists(pid, var);
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
