package edu.luc.cs.healthcare.server.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
  private String id;
  private String fName;
  private String lName;
  
  public Student(){
    
  }
  public Student (String id, String fName, String lName){
    this.id = id;
    this.fName = fName;
    this.lName = lName;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getFirstName() {
    return fName;
  }
  public void setFirstName(String fName) {
    this.fName = fName;
  }
  public String getLastName() {
	  return lName;
  }
  public void setLastName(String lName) {
	  this.lName = lName;
  }
  
  
} 