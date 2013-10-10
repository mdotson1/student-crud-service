package edu.luc.cs.healthcare.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import edu.luc.cs.healthcare.server.model.Student;

public class Tester {
	 	static ClientConfig config = new DefaultClientConfig();
	    static Client client = Client.create(config);
	    static WebResource service = client.resource(getBaseURI());
	    		
	public static void deleteAllStudents() {
		ClientResponse c = service.path("rest").path("students")
				.delete(ClientResponse.class);
	}
	
  public static void main(String[] args) {
    
    MultivaluedMap<String, String> formParams = new MultivaluedMapImpl();
    formParams.add("lName", "Dotson");
    formParams.add("fName", "Michael");
    
    Student student = new Student();
    student.setFName("Michael");
    student.setLName("Dotson");
    // post
    ClientResponse c = service.path("rest").path("students")
    		.post(ClientResponse.class, student);
    
    String id = c.getEntity(String.class);
    
    //c = service.path("rest").path("students")
    //		.put(c, student);
    
    // update
    // delete
    //deleteAllStudents();
    
    // create one student via put
   // Student student1 = new Student("678", "Jim", "Jones");
    //ClientResponse response = service.path("rest").path("students")
     //   .path(student1.getId()).accept(MediaType.APPLICATION_XML)
     //   .put(ClientResponse.class, student1);
  
    //System.out.println(service.path("rest").path("students")
    //        .accept(MediaType.TEXT_XML).get(Student.class));
    // create one student via post
    //ClientResponse response2 = service.path("rest").path("students")
    //		.accept(MediaType.APPLICATION_XML).post(ClientResponse.class);
    ////Student student2 = new Student();
    //System.out.println(response2.getEntity(String.class));
    //System.out.println(service.path("rest").path("students")
     //       .accept(MediaType.APPLICATION_XML).get(String.class));
    //System.out.println("here");
    //System.out.println(response2);
    //System.out.println("here");
    /*
    
    
    // Return code should be 201 == created resource
    System.out.println(response.getStatus());
    // Get the Students
    System.out.println(service.path("rest").path("students")
        .accept(MediaType.TEXT_XML).get(String.class));
    // Get JSON for application
    System.out.println(service.path("rest").path("students")
        .accept(MediaType.APPLICATION_JSON).get(String.class));
    // Get XML for application
    System.out.println(service.path("rest").path("students")
        .accept(MediaType.APPLICATION_XML).get(String.class));

    // Get the Student with id 678
    System.out.println(service.path("rest").path("students/678")
        .accept(MediaType.APPLICATION_XML).get(String.class));
    // get Student with id 678
    service.path("rest").path("students/678").delete();
    // Get the all students, id 678 should be deleted
    System.out.println(service.path("rest").path("students")
        .accept(MediaType.APPLICATION_XML).get(String.class));
*/
    /*
    response = service.path("rest").path("students")
        .type(MediaType.APPLICATION_FORM_URLENCODED)
        .post(ClientResponse.class, form);
    System.out.println("Form response " + response.getEntity(String.class));
    // Get the all students, id 678 should be created
    System.out.println(service.path("rest").path("students")
        .accept(MediaType.APPLICATION_XML).get(String.class));
*/
  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:8080/edu.luc.cs.healthcare").build();
  }
} 