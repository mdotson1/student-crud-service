package edu.luc.cs.healthcare.server.resources;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.luc.cs.healthcare.server.dao.StudentDao;
import edu.luc.cs.healthcare.server.model.Student;

// Will map the resource to the URL students
@Path("/students")
public class StudentsResource {

	@GET
	public Response getAllStudents() {
		String json = "";
		final Iterator<Student> it = StudentDao.instance.getAll();
		while (it.hasNext()) {
			json += it.next().toJson() + "\n";
		}
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newStudent(final Student student) {
		return Response.status(Status.CREATED).entity(
				StudentDao.instance.add(student)).build();
	}
	
	@DELETE
	public Response clearAll() {
		StudentDao.instance.deleteAll();
		
		return Response.ok().build();
	}
} 