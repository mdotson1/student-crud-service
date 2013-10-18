package edu.luc.cs.healthcare.server.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://api.openkeyval.org/" + id);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("data",
					student.getFirstName() + " " + student.getLastName()));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Student get(String id) {
		final HttpClient client = new DefaultHttpClient();
		final HttpGet request = new HttpGet("http://api.openkeyval.org/" + id);

		// Get the response
		final String[] names;
		
		try {
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			final String line = rd.readLine();
			names = line.split(" ");
			return new Student(id, names[0], names[1]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
} 