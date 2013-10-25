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

	// this is like get, but does not check if the id exists
	// first
	private Student retrieve(final String id) {
		String name;
		try {
			name = httpClient.prepareGet(SERVER_URL + id).execute()
					.get().getResponseBody();

			final String[] names = name.split(" ");
			return new Student(id, names[0], names[1]);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean contains(final String id) {
		final Iterator<String> ids = allIdsAsIterator();

		while (ids.hasNext()) {
			if (ids.next().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public Student update(final String id, final Student student) {
		try {
			final Student oldStudent = get(id);

			if (oldStudent != null) {
				httpClient.preparePost(SERVER_URL + id).addParameter("data", 
						student.getFirstName() + " " + student.getLastName()).execute();

				return oldStudent;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Student delete(final String id) {

		final Student deletedStudent = get(id);

		if (deletedStudent != null) {
			removeIdFromServerKey(id);
			return deletedStudent;
		} else {
			return null;
		}
	}

	// note: no checks to be sure ID is in server
	private void removeIdFromServerKey(final String id) {
		final String allIds = allIdsAsString();

		try {
			// only one id
			if (allIds.equals(id + " ")) {
				httpClient.preparePost(SERVER_URL_WITH_KEY).addParameter("data", "empty")
				.execute();
			} else {
				
				final int index = allIds.indexOf(id);

				final int newIndex = index + id.length() + 1; // plus 1 for the space
				
				if (allIds.startsWith(id + " ")) {
					httpClient.preparePost(SERVER_URL_WITH_KEY).addParameter("data", allIds.substring(newIndex))
					.execute();
				} else if (allIds.endsWith(id + " ")) {
					httpClient.preparePost(SERVER_URL_WITH_KEY).addParameter("data", allIds.substring(0, index))
					.execute();
				} else { // in the middle
					String newIds = allIds.substring(0, index) + allIds.substring(newIndex);
					httpClient.preparePost(SERVER_URL_WITH_KEY).addParameter("data", newIds)
					.execute();
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addIdToServerKey(final String id) 
			throws IllegalArgumentException, IOException {

		final String allIds = allIdsAsString();
		// has not been set yet
		if (allIds.equals("empty")) {
			httpClient.preparePost(SERVER_URL_WITH_KEY).addParameter("data", id + " ")
			.execute();
		} else {
			httpClient.preparePost(SERVER_URL_WITH_KEY).addParameter("data", allIds + id + " ")
			.execute();
		}
	}

	public void deleteAll() {
		try {
			httpClient.preparePost(SERVER_URL_WITH_KEY).addParameter("data", "empty")
			.execute();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String allIdsAsString() {
		try {
			final String ids = httpClient.prepareGet(SERVER_URL_WITH_KEY).execute().get()
					.getResponseBody();
			if (!ids.equals("empty")) {
				return ids;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "empty";
	}

	private String[] allIdsAsArray() {
		String[] tokenizedIds = allIdsAsString().split(" ");
		// no ids
		if (tokenizedIds[0].equals("empty")) {
			return new String[0];
		} else {
			return tokenizedIds;
		}
	}

	private Iterator<String> allIdsAsIterator() {
		return Arrays.asList(allIdsAsArray()).iterator();
	}
	
	// returns added student
	public Student add(final Student student) {

		// generate an id for the student
		String id;
		do {
			id = (int) (Math.random() * Integer.MAX_VALUE) + "";
		} while (contains(id)); // rare, but can generate same ID
		
		student.setId(id);
		try {
			addIdToServerKey(id);

			httpClient.preparePost(SERVER_URL + id).addParameter("data", 
					student.getFirstName() + " " + student.getLastName()).execute();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return student;
	}

	public Iterator<Student> getAll() {
		final List<Student> students = new ArrayList<Student>();

		final Iterator<String> ids = allIdsAsIterator();

		while (ids.hasNext()) {
			students.add(retrieve(ids.next()));
		}

		return students.iterator();
	}

	// function that checks if an id exists: if not, returns null
	// if yes, returns that student.
	public Student get(final String id) {
		if (contains(id)) {
			return retrieve(id);
		} else {
			return null;
		}
	}
} 