package com.os;

import java.io.IOException;

public final class Driver {

    public static void main(String[] args) throws IOException {
        
        // init components of the os
        OS os = new OS();
        Parser parser = new Parser(os);
        Scheduler scheduler = new Scheduler(os, parser);

        //list of programes
        String [] programs = new String[]{
            "Program 1.txt", 
            "Program 2.txt",
            "Program 3.txt"
        };

        os.loadProgrames(programs);

        scheduler.run();


        // for(String programName : programs){
        //     System.out.printf("Executing program < %s > \n", programName);
        //     parser.parse(programName);
        // }

        os.closeInputStream();
        
    }
}
