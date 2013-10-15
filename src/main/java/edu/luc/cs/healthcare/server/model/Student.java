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
  public String getFName() {
    return fName;
  }
  public void setFName(String fName) {
    this.fName = fName;
  }
  public String getLName() {
	  return lName;
  }
  public void setLName(String lName) {
	  this.lName = lName;
  }
  
  
} 