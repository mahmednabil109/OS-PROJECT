package com.os;


public final class Driver {

    public static void main(String[] args) {
        
        // init components of the os
        OS os = new OS();
        Parser parser = new Parser(os);

        //list of programes
        String [] programs = new String[]{
            "Program 1.txt", 
            "Program 2.txt",
            "Program 3.txt"
        };

        for(String programName : programs){
            System.out.printf("Executing program < %s > \n", programName);
            parser.parse(programName);
        }

        os.closeInputStream();
        
    }
}
