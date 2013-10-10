package edu.luc.cs.healthcare.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import edu.luc.cs.healthcare.server.dao.StudentDao;
import edu.luc.cs.healthcare.server.model.Student;

public class StudentResource {
	
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String id;
  public StudentResource(UriInfo uriInfo, Request request, String id) {
    this.uriInfo = uriInfo;
    this.request = request;
    this.id = id;
  }
  
  /*
  //Application integration     
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Student getStudent() {
	  Student student = StudentDao.instance.getModel().get(id);
    if(student==null)
      throw new RuntimeException("Get: Student with " + id +  " not found");
    return student;
  }
  
  // for the browser
  @GET
  @Produces(MediaType.TEXT_XML)
  public Student getStudentHTML() {
	Student student = StudentDao.instance.getModel().get(id);
    if(student==null)
      throw new RuntimeException("Get: Student with " + id +  " not found");
    return student;
  }
  
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  public Response putStudent(JAXBElement<Student> student) {
    Student c = student.getValue();
    return putAndGetResponse(c);
  }
  
  @DELETE
  public void deleteStudent() {
    Student c = StudentDao.instance.getModel().remove(id);
    if(c==null)
      throw new RuntimeException("Delete: Student with " + id +  " not found");
  }
  
  private Response putAndGetResponse(Student stu) {
    Response res;
    if(StudentDao.instance.getModel().containsKey(stu.getId())) {
      res = Response.noContent().build();
    } else {
      res = Response.created(uriInfo.getAbsolutePath()).build();
    }
    StudentDao.instance.getModel().put(stu.getId(), stu);
    return res;
  }
  */

} 