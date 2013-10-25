package edu.luc.cs.healthcare.server.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.luc.cs.healthcare.server.dao.StudentDao;
import edu.luc.cs.healthcare.server.model.Student;

@Path("/students/{id}")
public class StudentIdResource {
	
	@GET
	public Response getStudent(@PathParam("id") String id) {
		final Student student = StudentDao.instance.get(id);
		if (student != null) {
			final String json = student.toJson();
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@DELETE
	public Response deleteStudent(@PathParam("id") final String id) {
		Student deletedStudent = StudentDao.instance.delete(id);
		
		if (deletedStudent != null) {
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	public Response updateStudent(@PathParam("id") final String id,
			final Student student) {
		
		StudentDao.instance.update(id, student);
		
		return Response.ok().build();
	}
}
