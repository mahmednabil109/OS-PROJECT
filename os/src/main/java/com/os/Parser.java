package com.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {
    
    private OS os;
    private String globalPath;

    public Parser(OS os){
        this.os = os;
        this.globalPath = System.getProperty("user.dir");
    }

    public OS getOs(){
        return this.os;
    }

    // public void parse(String ProgramName){
    //     try{
    //         List<String> instructions = Files.readAllLines(Paths.get(globalPath, ProgramName));
    //         this.os.initMemory();
    //         for(String instruction : instructions){
    //             this._execute(instruction, false);
    //         }
    //     }catch(IOException e){
    //         e.printStackTrace();
    //     }
    // }

    public Gen _execute(String statment, boolean flag) throws IOException{

        // init the needed variables
        String parsedStatment[] = statment.split(" ");
        Gen value;
        String val, fileName;

        switch (parsedStatment[0]) {
            case "assign":
                value = _execute(join(parsedStatment, 2), false);
                os.store(parsedStatment[1], value);
                break;
            case "print":
                value = _execute(parsedStatment[1], false);
                if (value.type == GenType.NUMBER)
                    os.print(value.intData + "");
                else
                    os.print(value.stringData);
                break;
            case "writeFile":
                fileName = _execute(parsedStatment[1], false).stringData;
                value = _execute(parsedStatment[2], false);
                if (value.type == GenType.NUMBER)
                    os.WriteFile(fileName, value.intData + "");
                else
                    os.WriteFile(fileName, value.stringData);
                break;
            case "add":
                Gen opr1 = _execute(parsedStatment[1], false), opr2 = _execute(parsedStatment[2], false);
                if (opr1.type != GenType.NUMBER || opr2.type != GenType.NUMBER)
                    os.print("[ERROR] Can't add none Numeric Values !!");
                os.store(parsedStatment[1], new Gen(GenType.NUMBER, opr1.intData + opr2.intData, ""));
                // we make the add able to return values to allow for more functionalities
                return os.load(parsedStatment[1]);
            case "input":
                String input = os.input();
                return _execute(input, true);
            case "readFile":
                fileName = _execute(parsedStatment[1], false).stringData;
                val = os.readFile(fileName);
                return new Gen(GenType.STRING, 0, val);
            default:
                Pattern numPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                Pattern varPattern = Pattern.compile("\\w+");

                val = parsedStatment[0];
                if (numPattern.matcher(val).matches()) {
                    return new Gen(GenType.NUMBER, Integer.parseInt(val), "");
                } else if (varPattern.matcher(val).matches()) {
                    if(flag)
                        return new Gen(GenType.STRING, 0, statment);
                    
                    if (!os.exists(val)) {
                       return new Gen(GenType.STRING, 0, val);
                    } else {
                        return os.load(val);
                    }
                }
        }
        return null;
    }

    private static String join(String arr[], int start) {
        StringBuilder result = new StringBuilder("");
        for (; start < arr.length; start++) {
            result.append(arr[start]);
            if (start != arr.length - 1)
                result.append(" ");
        }
        return result.toString();
    }
}
