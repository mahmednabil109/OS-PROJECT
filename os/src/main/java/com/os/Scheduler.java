package com.os;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {


    Queue<Integer> readyQueue;
    public static final int MAX_CYCLES = 2;
    OS os;
    Parser parser;

    public Scheduler(OS os, Parser parser){
        this.os = os;
        this.parser = parser;
        this.readyQueue = new LinkedList<Integer>();
        this.init(os.getPIDs());
    }

    public void init(int PIDs[]){
        for(int PID : PIDs)
            this.readyQueue.add(PID);
    }

    public void run() throws IOException{
        next: while(!readyQueue.isEmpty()){
            int PID = readyQueue.poll();
            for(int i=0; i<this.MAX_CYCLES; i++){
                if(os.isFinished(PID))
                    continue next;
                parser._execute(os.nextInst(PID), false);
            }
            readyQueue.add(PID);
        }
    }
}
