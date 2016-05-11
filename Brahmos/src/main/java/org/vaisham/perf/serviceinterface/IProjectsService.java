package org.vaisham.perf.serviceinterface;

import java.util.List;

import org.hibernate.HibernateException;
import org.vaisham.perf.autogen.Projects;
import org.vaisham.perf.manage.exceptions.ProjectNotFoundException;

public interface IProjectsService {

	public Projects createProject(String name, String description, 
			String jmx, String location, int users, 
			int loadNo, int iterationsNo, String comments);

	public Projects updateProject(int projectId, String name, String description, 
			String jmx, String location, int users, 
			int loadNo, int iterationsNo, String comments);

	public Projects getProject(int projectId);

	public List<Projects> getAllProjects();

	public void deleteProject(int projectId) throws ProjectNotFoundException, HibernateException;
}
