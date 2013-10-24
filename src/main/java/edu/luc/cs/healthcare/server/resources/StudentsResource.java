package edu.luc.cs.healthcare.server.resources;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@GET
	@Path("{id}")
	public Response getStudent(@PathParam("id") String id) {
		final Student student = StudentDao.instance.get(id);
		if (student != null) {
			final String json = student.toJson();
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newStudent(Student student) {

		final String value;

		// generate an id for the student
		value = (int) (Math.random() * Integer.MAX_VALUE) + "";

		student.setId(value);

		StudentDao.instance.add(value, student);

		return Response.status(Status.CREATED).entity(student).build();
	}

} 