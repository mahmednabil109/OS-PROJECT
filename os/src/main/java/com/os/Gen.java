package com.os;

public class Gen {
    public int intData;
    public String stringData;

    GenType type;

    public Gen(GenType type, int intData, String stringData) {
        this.type = type;
        this.intData = intData;
        this.stringData = stringData;
    }

    public String toString() {
        if (type == GenType.NUMBER)
            System.out.printf("<NUMBER %d>", intData);
        else
            System.out.printf("<STRING %s>", stringData);
        return "";
    }
}
