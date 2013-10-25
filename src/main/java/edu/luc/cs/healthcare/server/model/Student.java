package edu.luc.cs.healthcare.server.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
	private String id;
	private String firstName;
	private String lastName;

	public Student(){

	}
	public Student (String id, String fName, String lName){
		this.id = id;
		this.firstName = fName;
		this.lastName = lName;
	}
	public String getId() {
		return id;
	}
	public Student setId(String id) {
		this.id = id;
		return this;
	}
	public String getFirstName() {
		return firstName;
	}
	public Student setFirstName(String fName) {
		this.firstName = fName;
		return this;
	}
	public String getLastName() {
		return lastName;
	}
	public Student setLastName(String lName) {
		this.lastName = lName;
		return this;
	}

	public String toJson() {
		final String str = "{\"firstName\":\"" + firstName + "\"," +
				"\"id\":\"" + id + "\",\"lastName\":\"" + lastName + "\"}";
		return str;
	}
} 