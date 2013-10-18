package edu.luc.cs.healthcare.server.resources;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

	//Application integration     
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Student getStudent(@PathParam("id") String id) {
		return StudentDao.instance.get(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newStudent(Student student) {

		String value;

		// generate an id for the student
		value = (int) (Math.random() * Integer.MAX_VALUE) + "";

		student.setId(value);

		StudentDao.instance.add(value, student);

		return Response.status(201).entity(student).build();
	}

} 