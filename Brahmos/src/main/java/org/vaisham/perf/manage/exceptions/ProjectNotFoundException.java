package org.vaisham.perf.manage.exceptions;

public class ProjectNotFoundException extends Exception {

	private static final long serialVersionUID = -7194387248628718720L;
	private int id;

	public ProjectNotFoundException(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getMessage() {
		return "Exception: Project not found with projectId: " + this.id;
	}
}
