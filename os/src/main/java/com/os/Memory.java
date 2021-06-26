package com.os;

import java.util.*;

public class Memory {
	
	public Word [] memory ;
	public Memory() {
		// this.memory = new Word[size];
	}


    /*
        process address space
        0: PID
        1: PState 1 --> ready , 0 --> finished
        2: PC
        3: minadder
        4: maxadder
        5: code
           ....
        ?: variable

        code: 5 --> 4 + maline
        variable: 4 + maxline + 1 --> 4 + 2 * maxline

    */ 

    public void init(int numOfPrograms, int maxLines, Vector<List<String>> programs){
        // allocate memo for the programs
        this.memory = new Word[numOfPrograms*((maxLines*2)+5)];

        // load the code, pcb, variable space to memo
        for(int i=0; i<programs.size(); i++){
            int base = i * (2 * maxLines + 5);
            this.memory[base] = new Word("_$PID", i);
            this.memory[base + 1] = new Word("_$PState", 1);
            this.memory[base + 2] = new Word("_$PC", base + 5);
            this.memory[base + 3] = new Word("_$MinAdder", base + 4 + maxLines);
            this.memory[base + 4] = new Word("_$MaxAdder", base + 4 + 2 * maxLines);
            int j=0;
            for(j=0; j<programs.get(i).size(); j++){
                this.memory[base + j +  5] = new Word(
                    ("_$line" + j)
                    ,
                    programs.get(i).get(j)
                );
                System.out.println("tmp: " + (base + j + 5) + " " +  programs.get(i).get(j));
            }
            for(; j<maxLines;j++){
                this.memory[base + j +  5] = null;
                System.out.println("tmp: " + (base + j + 5) + " " +  "null");
            }
        }
    }

	public Object load(int pid, String name) {
		int starting = pid * ( 2 * OS.maxLines + 5);
		for(int i = starting; i< starting + (2*OS.maxLines + 5) ; i++) {
            if(memory[i] == null) continue;
			if(memory[i].name.equals(name)) {
				return memory[i].value;
			}
		}
		return null;
	}
	
	public void store(int pid, String name, Object value) {
		Word saveWord = new Word(name, value);
		
		int starting = pid * (2*OS.maxLines + 5) + 4 + OS.maxLines + 1;
        System.out.println("store : " + pid + " " + starting);
		for(int i = starting; i< starting + OS.maxLines;i++) {

            if(memory[i]!=null && memory[i].name.equals(name)){
                memory[i] = saveWord;
                return;
            } else if(memory[i]==null) {
				memory[i] = saveWord;
				return;
			}
		}
		System.out.println("There's no empty place for this instruction");
	}
	
	public boolean exists(int pid, String name) {
		
		int starting = pid*(2*OS.maxLines + 5);
		for(int i = starting; i< starting+(2*OS.maxLines)+5;i++) {
            if(memory[i] == null) continue;
			if(memory[i].name.equals(name)) {
				return true;
			}
		}
		
		return false;

	}
}