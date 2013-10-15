package edu.luc.cs.healthcare.server.dao;

import java.util.HashMap;
import java.util.Map;

import edu.luc.cs.healthcare.server.model.Student;


public enum StudentDao {
  instance;
  
  private Map<String, Student> contentProvider = new HashMap<String, Student>();
  
  private StudentDao() {
    
  }
  
  public Map<String, Student> getModel(){
    return contentProvider;
  }
  
} 