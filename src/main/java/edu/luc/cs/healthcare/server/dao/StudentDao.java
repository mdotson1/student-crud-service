package edu.luc.cs.healthcare.server.dao;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
		httpClient = new AsyncHttpClient();
	}

	private final AsyncHttpClient httpClient;

	private static ClientConfig config = new DefaultClientConfig();
	private static Client client = Client.create(config);
	private static WebResource service = client.resource(getBaseURI());
	private static final String SERVER_KEY = "luc_service_key";
	private static final String SERVER_URL = "http://api.openkeyval.org/";
	private static final String SERVER_URL_WITH_KEY = SERVER_URL + SERVER_KEY;

	private static URI getBaseURI() {
		return UriBuilder.fromUri(SERVER_URL).build();
	}

	private boolean contains(final String id) {
		final Iterator<String> ids = idsIterator();

		while (ids.hasNext()) {
			if (ids.next().equals(id)) {
				return true;
			}
		}
		return false;
	}

	private void addIdToServerKey(final String id) 
			throws IllegalArgumentException, IOException {

		String allIds = getAllIdsAsString();
		// has not been set yet
		if (allIds.startsWith("{\"error\"")) {
			allIds = id;
		} else {
			allIds += " " + id;
		}

		httpClient.preparePost(SERVER_URL_WITH_KEY).addParameter("data", allIds)
		.execute();
	}

	private String getAllIdsAsString() {
		try {
			return httpClient.prepareGet(SERVER_URL_WITH_KEY).execute().get()
					.getResponseBody();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Iterator<String> idsIterator() {
		final String[] tokenedIds = getAllIdsAsString().split(" ");

		return Arrays.asList(tokenedIds).iterator();
	}

	public void add(final String id, final Student student) {

		try {
			addIdToServerKey(id);

			httpClient.preparePost(SERVER_URL + id).addParameter("data", 
					student.getFirstName() + " " + student.getLastName()).execute();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Iterator<Student> getAll() {
		final List<Student> students = new ArrayList<Student>();

		final Iterator<String> ids = idsIterator();

		while (ids.hasNext()) {
			students.add(get(ids.next()));
		}

		return students.iterator();
	}

	public Student get(final String id) {
		try {
			if (contains(id)) {
				final String name = httpClient.prepareGet(SERVER_URL + id).execute()
						.get().getResponseBody();

				// Get the response
				final String[] names = name.split(" ");
				return new Student(id, names[0], names[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return null;
	}
} 