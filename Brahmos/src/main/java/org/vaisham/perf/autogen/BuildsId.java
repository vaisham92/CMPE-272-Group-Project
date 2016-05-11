package org.vaisham.perf.autogen;

/**
 * @author Vaishampayan Reddy
 */
public class BuildsId implements java.io.Serializable {

	private static final long serialVersionUID = 6514906042547488486L;
	private int id;
	private int projectId;

	public BuildsId() {
	}

	public BuildsId(int id, int projectId) {
		this.id = id;
		this.projectId = projectId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BuildsId))
			return false;
		BuildsId castOther = (BuildsId) other;

		return (this.getId() == castOther.getId()) && (this.getProjectId() == castOther.getProjectId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getId();
		result = 37 * result + this.getProjectId();
		return result;
	}

}
