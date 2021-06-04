package com.os;

import java.util.*;
import java.util.regex.Pattern;

import java.io.IOException;
import java.nio.file.*;

public class App {

    public static Scanner sc = new Scanner(System.in);
    public static Hashtable<String, Gen> Context = new Hashtable<>();

    public static void main(String[] args) {
        // another alternative is to scan a folder with given products
        if (args.length != 0) {
            System.out.println(args.length);
        } else {
            while (true) {
                String pathToProgram = ask();
                if (pathToProgram.toLowerCase().equals("terminate"))
                    break;
                readProgram(pathToProgram);

            }
        }

        sc.close();
    }

    public static String ask() {
        System.out.print("Enter the path of the program: ");
        String path = sc.nextLine();
        return path;
    }

    public static void readProgram(String pathToProgram) {
        // load the content and parse it
        try {
            pathToProgram = App.class.getResource("").getPath() + pathToProgram;
            System.out.println(pathToProgram);
            List<String> lines = Files.readAllLines(Paths.get(pathToProgram));
            Context = new Hashtable<>();
            for (String statment : lines) {
                execute(statment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Gen execute(String statment) throws IOException {
        System.out.println("s: " + statment);
        Pattern strPattern = Pattern.compile("\".*\"", Pattern.DOTALL);

        String parsedStatment[];
        if (!strPattern.matcher(statment).matches())
            parsedStatment = statment.split(" ");
        else
            parsedStatment = new String[] { statment };
        Gen value;
        String val, path;
        switch (parsedStatment[0]) {
            case "assign":
                value = execute(join(parsedStatment, 2));
                Context.put(parsedStatment[1], value);
                break;
            case "print":
                value = execute(parsedStatment[1]);
                if (value.type == GenType.NUMBER)
                    System.out.println(value.intData);
                else
                    System.out.println(value.stringData);
                break;
            case "writeFile":
                value = execute(parsedStatment[1]);
                path = App.class.getResource("").getPath()
                        + value.stringData.substring(1, value.stringData.length() - 1);
                value = execute(parsedStatment[2]);
                System.out.println(Paths.get(path).toString());
                if (value.type == GenType.NUMBER)
                    Files.write(Paths.get(path), (value.intData + "").getBytes());
                else
                    Files.write(Paths.get(path),
                            value.stringData.substring(1, value.stringData.length() - 1).getBytes());
                break;
            case "add":
                Gen opr1 = execute(parsedStatment[1]), opr2 = execute(parsedStatment[2]);
                if (opr1.type != GenType.NUMBER || opr2.type != GenType.NUMBER)
                    // TODO throw ERROR Here
                    ;
                Context.put(parsedStatment[1], new Gen(GenType.NUMBER, opr1.intData + opr2.intData, ""));
                return Context.get(parsedStatment[1]);
            case "input":
                System.out.println("INPUT: [\"data\" for string] ");
                String input = sc.nextLine();
                System.out.println("input" + execute(input).toString());
                return execute(input);
            case "readFile":
                // TODO handle the paths
                value = execute(parsedStatment[1]);
                System.out.println(value.stringData.substring(1, value.stringData.length() - 1));
                path = App.class.getResource("").getPath()
                        + value.stringData.substring(1, value.stringData.length() - 1);
                val = new String(Files.readAllBytes(Paths.get(path)));
                val = "\"" + val + "\"";
                return execute(val);
            default:
                Pattern numPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                Pattern varPattern = Pattern.compile("\\w+");

                val = parsedStatment[0];
                System.out.println("[inout " + val + "]");
                if (numPattern.matcher(val).matches()) {
                    return new Gen(GenType.NUMBER, Integer.parseInt(val), "");
                } else if (strPattern.matcher(val).matches()) {
                    System.out.println("[string matched]");
                    return new Gen(GenType.STRING, 0, val);
                } else if (varPattern.matcher(val).matches()) {
                    if (!Context.containsKey(val)) {
                        // TODO throw ERROR Here
                        ;
                    } else {
                        System.out.println("in " + val);
                        return Context.get(val);
                    }
                }
        }
        return null;
    }

    public static String join(String arr[], int start) {
        StringBuilder result = new StringBuilder("");
        for (; start < arr.length; start++) {
            result.append(arr[start]);
            if (start != arr.length - 1)
                result.append(" ");
        }
        return result.toString();
    }
}
