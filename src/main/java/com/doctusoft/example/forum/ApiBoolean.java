package com.doctusoft.example.forum;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Value;



@Value
@AllArgsConstructor
public class ApiBoolean implements Serializable {
	
	private boolean value;

}
