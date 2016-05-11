package org.vaisham.perf.routes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaisham.perf.autogen.Builds;
import org.vaisham.perf.autogen.Projects;
import org.vaisham.perf.core.IJmeterCSVReader;
import org.vaisham.perf.core.JmeterCSVReader2;
import org.vaisham.perf.dto.BuildsDTO;
import org.vaisham.perf.manage.exceptions.ProjectNotFoundException;
import org.vaisham.perf.serviceinterface.IBuildsService;
import org.vaisham.perf.serviceinterface.IProjectsService;
import org.vaisham.perf.utils.GsonHelper;

import com.sun.jersey.multipart.FormDataParam;

@Path("")
@Component
public class BrahmosHandler {

	private static GsonHelper gsonHelper = new GsonHelper();
	private static IJmeterCSVReader jmeterCSVReader = new JmeterCSVReader2();

	@Autowired
	public IProjectsService iProjectsSerice;

	@Autowired
	public IBuildsService iBuildsService;

	@GET
	@Path("/test/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output = "Brahmos says : " + msg;
		return Response.status(200).entity(output).build();
	}

	/** Projects function **/
	@GET
	@Path("/projects")
	@Produces("application/json")
	public Response getAllProjects() {
		List<Projects> projects = iProjectsSerice.getAllProjects();
		String output = gsonHelper.convertToJson(projects);
		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/projects/new")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response createNewProject(
			@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("location") String location,
			@FormDataParam("users") int users,
			@FormDataParam("loadNo") int loadNo,
			@FormDataParam("iterationsNo") int iterationsNo,
			@FormDataParam("comments") String comments) {
		UUID uuid = UUID.randomUUID();
		String filePath = "D://1.csv" + uuid.toString() + ".jmx";
		saveFile(fileInputStream, filePath);
		Projects projects = iProjectsSerice.createProject(name, description, 
				filePath, location, users, loadNo, iterationsNo, comments);
		String output = gsonHelper.convertToJson(projects);
		return Response.status(201).entity(output).build();
	}

	@POST
	@Path("/projects/{projectId}/update")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response updateProject(
			@PathParam("projectId") int projectId,
			@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("location") String location,
			@FormDataParam("users") int users,
			@FormDataParam("loadNo") int loadNo,
			@FormDataParam("iterationsNo") int iterationsNo,
			@FormDataParam("comments") String comments) {
		UUID uuid = UUID.randomUUID();
		String filePath = "D://csv" + uuid.toString() + ".jmx";
		saveFile(fileInputStream, filePath);
		Projects projects = iProjectsSerice.updateProject(projectId, name, description, 
				filePath, location, users, loadNo, iterationsNo, comments);
		String output = gsonHelper.convertToJson(projects);
		return Response.status(200).entity(output).build();
	}

	@DELETE
	@Path("/projects/{projectId}")
	public Response deleteProject(
			@PathParam("projectId") int projectId) {
		try {
			iProjectsSerice.deleteProject(projectId);
			return Response.status(200).entity("").build();
		} catch (HibernateException e) {
			return Response.status(500).entity("").build();
		} catch (ProjectNotFoundException e) {
			return Response.status(400).entity("").build();
		}
	}

	@GET
	@Path("/projects/{projectId}")
	@Produces("application/json")
	public Response getProjects(@PathParam("projectId") int projectId) {
		Projects projects;
		projects = iProjectsSerice.getProject(projectId);
		String output = gsonHelper.convertToJson(projects);
		return Response.status(200).entity(output).build();
	}

	/** End project functions **/

	/** Start builds functions **/
	@GET
	@Path("projects/{projectId}/builds")
	@Produces("application/json")
	public Response getBuildsForScenario(@PathParam("projectId") int projectId) {
		List<BuildsDTO> buildsDTO = iBuildsService.getBuildsByProjectId(projectId);
		String output = gsonHelper.convertToJson(buildsDTO);
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("projects/{projectId}/builds/{buildNumber}")
	@Produces("application/json")
	public Response getBuildForScenarioAndBuild(
			@PathParam("projectId") int projectId,
			@PathParam("buildNumber") int id) {
		Builds build = iBuildsService.getBuild(id, projectId);
		//System.out.println(gsonHelper.convertToJson(build));
		return Response.status(200).entity(gsonHelper.convertToJson(build)).build();
	}

	@POST
	@Path("projects/{projectId}/builds/new")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response addNewBuild(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("id") int id,
			@PathParam("projectId") int projectId,
			@FormDataParam("description") String description,
			@FormDataParam("comments") String comments) {
		String filePath = "D://1.csv";
		saveFile(fileInputStream, filePath);
		try {
			Builds build = iBuildsService.createBuild(id, projectId,
					description, jmeterCSVReader.extractBuildDataFromFile(filePath).getBytes(), comments);
			BuildsDTO buildsDTO = new BuildsDTO(build);
			String output = gsonHelper.convertToJson(buildsDTO);
			return Response.status(200).entity(output).build();
		} catch (FileNotFoundException e) {
			return Response.status(500).entity("error").build();
		}
	}

	/** End builds functions **/

	/** End Result Analyzer functions **/
	@POST
	@Path("/resultAnalyzer")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("application/json")
	public Response getDataForResultAnalyzer(
			@FormDataParam("file") InputStream fileInputStream) {
		UUID uuid = UUID.randomUUID();
		String filePath = "/home/ubuntu/" + uuid.toString() +  ".csv";
		saveFile(fileInputStream, filePath);
		try {
			return Response.status(200).entity(jmeterCSVReader.extractBuildDataFromFile(filePath)).build();
		} catch (FileNotFoundException e) {
			return Response.status(500).entity("error").build();
		}
	}
	/** End Result Analyzer functions **/

	private String getMonthByIndex(int index) {
		switch(index) {
		case 0:
			return "January";
		case 1:
			return "February";
		case 2:
			return "March";
		case 3:
			return "April";
		case 4:
			return "May";
		case 5:
			return "June";
		case 6:
			return "July";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "October";
		case 10:
			return "November";
		case 11:
			return "December";
		default: 
				return null;
		}
	}
	private void saveFile(InputStream uploadedInputStream, String serverLocation) {
		
		try {
			OutputStream outpuStream = new FileOutputStream(new File(
					serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean createDirectoryInFilepath(String filepath, String directoryName) {
		File file = new File(filepath + "\\" + directoryName);
		if (!file.exists()) {
			if (file.mkdir()) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	private boolean doesDirectoryExists(String filepath) {
		File file = new File(filepath);
		return file.exists();
	}
	
	private String getFilePathForBuilds(int projectId, int scenarioId, int buildNumber) {
		return projectId + "\"" + scenarioId + "\"" + buildNumber;
	}
}