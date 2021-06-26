package com.os;

public class Word {
	public String name;
	public Object value;
	
	public Word (String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String toString(){
		return ("< "+ name + ", " + value.toString() +" >");
	}
}