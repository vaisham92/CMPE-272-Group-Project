package org.vaisham.perf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vaisham.perf.autogen.Projects;
import org.vaisham.perf.db.api.IGenericDAO;
import org.vaisham.perf.manage.exceptions.ProjectNotFoundException;
import org.vaisham.perf.serviceinterface.IProjectsService;
import org.vaisham.perf.sql.SqlQuery;
import org.vaisham.perf.utils.factory.ILogger;
import org.vaisham.perf.utils.factory.LoggerManager;

import com.google.gson.Gson;

@Component
public class ProjectsService implements IProjectsService {
	@Autowired
	@Qualifier("GlobalDAO")
	private IGenericDAO iGenericDAO;

	public static ILogger logger = LoggerManager.getLoggerFactory().getLogger(
			ProjectsService.class.getName());

	private List<String> projectNames;

	@Transactional
	public Projects createProject(String name, String description, 
			String jmx, String location, int users, 
			int loadNo, int iterationsNo, String comments) {
		if (doesProjectNameExist(name))
			return null;
		else {
			Projects projects = new Projects();
			projects.setName(name);
			if (projectNames == null)
				projectNames = new ArrayList<String>();
			projectNames.add(name);
			projects.setDescription(description);
			projects.setJmx(jmx);
			projects.setLocation(location);
			projects.setUsers(users);
			projects.setLastModifiedDate(new Date());
			projects.setCreatedDate(new Date());
			projects.setLoadNo(loadNo);
			projects.setIterationsNo(iterationsNo);
			projects.setComments(comments);
			Gson gson = new Gson();
			System.out.println(gson.toJson(projects));
			projects = iGenericDAO.addEntity(projects);
			return projects;
		}
	}

	@Transactional
	public Projects updateProject(int projectId, String name, String description, 
			String jmx, String location, int users, 
			int loadNo, int iterationsNo, String comments) {
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(projectId);
		Projects project = iGenericDAO.getEntity(
				SqlQuery.GET_PROJECT_BY_PROJECT_ID, queryList);
		if (project == null)
			return null;
		else {
			if (!project.getName().equals(name)) {
				if (doesProjectNameExist(name, projectId))
					return null;
				else {
					projectNames.remove(project.getName());
					project.setName(name);
					projectNames.add(name);
				}
			}
			project.setDescription(description);
			project.setJmx(jmx);
			project.setLocation(location);
			project.setUsers(users);
			project.setLastModifiedDate(new Date());
			project.setCreatedDate(new Date());
			project.setLoadNo(loadNo);
			project.setIterationsNo(iterationsNo);
			project.setComments(comments);
			project = iGenericDAO.updateEntity(project);
			return project;
		}
	}

	@Transactional
	public Projects getProject(int projectId) {
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(projectId);
		Projects project = iGenericDAO.getEntity(
				SqlQuery.GET_PROJECT_BY_PROJECT_ID, queryList);
		return project;
	}

	@Transactional
	public List<Projects> getAllProjects() {
		List<Projects> projectsList = iGenericDAO.getEntities(
				SqlQuery.GET_ALL_PROJECTS, null);
		return projectsList;
	}

	@Transactional
	public void deleteProject(int projectId) throws ProjectNotFoundException,
			HibernateException {
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(projectId);
		Projects project = iGenericDAO.getEntity(
				SqlQuery.GET_PROJECT_BY_PROJECT_ID, queryList);
		if (project == null)
			throw new ProjectNotFoundException(projectId);
		else {
			iGenericDAO.deleteEntity(project);
			if(projectNames != null)
			projectNames.remove(project.getName());
		}
	}

	private boolean doesProjectNameExist(String name) {
		return doesProjectNameExist(name, -1);
	}

	private boolean doesProjectNameExist(String name, int projectId) {
		if (projectNames == null) {
			if (projectId == -1) {
				projectNames = iGenericDAO.getEntities(
						SqlQuery.GET_ALL_PROJECT_NAMES, null);
			} else {
				List<Object> queryList = new ArrayList<Object>();
				queryList.add(projectId);
				projectNames = iGenericDAO.getEntities(
						SqlQuery.GET_PROJECT_NAMES_EXCEPT, queryList);
			}
		}
		if (projectNames.contains(name))
			return true;
		else
			return false;
	}
}