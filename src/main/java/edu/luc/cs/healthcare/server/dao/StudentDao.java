package edu.luc.cs.healthcare.server.dao;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.core.UriBuilder;

import com.ning.http.client.AsyncHttpClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import edu.luc.cs.healthcare.server.model.Student;


public enum StudentDao {
	instance;

	private StudentDao() {

	}

	static ClientConfig config = new DefaultClientConfig();
	static Client client = Client.create(config);
	static WebResource service = client.resource(getBaseURI());

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://api.openkeyval.org").build();
	}

	public void add(String id, Student student) {
		AsyncHttpClient client = new AsyncHttpClient();
		try {
			client.preparePost("http://api.openkeyval.org/" + id).addParameter("data", 
					student.getFirstName() + " " + student.getLastName()).execute();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Student get(String id) {
		AsyncHttpClient client = new AsyncHttpClient();
		
		String name = "";
		try {
			name = client.prepareGet("http://api.openkeyval.org/" + id)
					.execute().get().getResponseBody();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// Get the response
		final String[] names = name.split(" ");
		return new Student(id, names[0], names[1]);
	}
} 