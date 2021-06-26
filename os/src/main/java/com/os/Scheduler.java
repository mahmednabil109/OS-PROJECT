package com.os;

import java.io.IOException;
import java.util.Hashtable;
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

        Hashtable<Integer, Integer> count = new Hashtable<>();

        // manage the RR 
        next: while(!readyQueue.isEmpty()){
            int PID = readyQueue.poll();
            
            if(count.get(PID) == null)
                count.put(PID, 1);
            else
                count.put(PID, count.get(PID) + 1);
            
            System.out.printf("PROCESS CHOOSEN %d\n", PID);
            os.changeState(PID, 1);
            for(int i=0; i<this.MAX_CYCLES; i++){
                if(os.isFinished(PID)){
                    os.changeState(PID, 0);
                    System.out.printf("PROCESS FINISHED %d, %d\n", PID, count.get(PID));
                    continue next;
                }
                parser._execute(PID, os.nextInst(PID), false);
            }

            if(os.isFinished(PID)){
                os.changeState(PID, 0);
                System.out.printf("PROCESS FINISHED %d, %d\n", PID, count.get(PID));
                continue next;
            }
            
            os.changeState(PID, 1);
            readyQueue.add(PID);
        }
    }
}
