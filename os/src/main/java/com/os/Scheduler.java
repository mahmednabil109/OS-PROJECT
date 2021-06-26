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
    }

    public void run() throws IOException{
        // init the ready queue
        for(int i=0; i<this.os.getPrograms();i++)
            this.readyQueue.add(i);

        // manage the RR 
        next: while(!readyQueue.isEmpty()){
            int PID = readyQueue.poll();
            for(int i=0; i<this.MAX_CYCLES; i++){
                if(os.isFinished(PID))
                    continue next;
                System.out.println("pid: " + PID);
                parser._execute(PID, os.nextInst(PID), false);
            }
            readyQueue.add(PID);
        }
    }
}
