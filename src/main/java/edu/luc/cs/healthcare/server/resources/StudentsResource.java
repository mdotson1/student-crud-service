package edu.luc.cs.healthcare.server.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import edu.luc.cs.healthcare.server.dao.StudentDao;
import edu.luc.cs.healthcare.server.model.Student;

// Will map the resource to the URL students
@Path("/students")
public class StudentsResource {

  // Allows to insert contextual objects into the class, 
  // e.g. ServletContext, Request, Response, UriInfo
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  // Return the list of students for applications
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public List<Student> getStudents() {
    List<Student> students = new ArrayList<Student>();
    students.addAll(StudentDao.instance.getModel().values());
    return students; 
  }
  
  // delete all students
  @DELETE
  public void deleteStudents() {
	  StudentDao.instance.getModel().clear();
  }
  
  
  // returns the number of students
  // use http://localhost:8080/edu.luc.cs.healthcare/rest/students/count
  // to get the total number of records
  @GET
  @Path("count")
  @Produces(MediaType.TEXT_PLAIN)
  public String getCount() {
    int count = StudentDao.instance.getModel().size();
    return String.valueOf(count);
  }
  
  @POST
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response newStudent(JAXBElement<Student> student) {
	  
	  String value;
	  
	  Student stu;
	  
	  // generate an id for the student
	  do {
		  value = (int) (Math.random() * Integer.MAX_VALUE) + "";
		  stu = StudentDao.instance.getModel().get(value);
	  } while (stu != null);
	  
	  stu = student.getValue();
	  stu.setId(value);
	  
	  StudentDao.instance.getModel().put(value, stu);
	  
	  return Response.status(201).entity(stu).build();
  }
  
  /*
  // Defines that the next path parameter after students is
  // treated as a parameter and passed to the StudentResources
  // Allows to type http://localhost:8080/edu.luc.cs.healthcare/rest/students/1
  // 1 will be treaded as parameter student and passed to StudentResource
  @Path("{student}")
  public StudentResource getStudent(@PathParam("student") String id) {
    return new StudentResource(uriInfo, request, id);
  }
  */
  
} 