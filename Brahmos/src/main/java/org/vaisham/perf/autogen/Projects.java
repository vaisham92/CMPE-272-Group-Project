package org.vaisham.perf.autogen;

import java.util.Date;

/**
 * @author Vaishampayan Reddy
 */
public class Projects implements java.io.Serializable {

	private static final long serialVersionUID = -8045668728719973370L;
	private Integer id;
	private String name;
	private String description;
	private String jmx;
	private String location;
	private int users;
	private int loadNo;
	private int iterationsNo;
	private Date lastModifiedDate;
	private Date createdDate;
	private boolean isDeleted;
	private String comments;

	public Projects() {
	}

	public Projects(String name, String description, String jmx, String location, int users, int loadNo,
			int iterationsNo, Date lastModifiedDate, Date createdDate, boolean isDeleted, String comments) {
		this.name = name;
		this.description = description;
		this.jmx = jmx;
		this.location = location;
		this.users = users;
		this.loadNo = loadNo;
		this.iterationsNo = iterationsNo;
		this.lastModifiedDate = lastModifiedDate;
		this.createdDate = createdDate;
		this.isDeleted = isDeleted;
		this.comments = comments;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJmx() {
		return this.jmx;
	}

	public void setJmx(String jmx) {
		this.jmx = jmx;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getUsers() {
		return this.users;
	}

	public void setUsers(int users) {
		this.users = users;
	}

	public int getLoadNo() {
		return this.loadNo;
	}

	public void setLoadNo(int loadNo) {
		this.loadNo = loadNo;
	}

	public int getIterationsNo() {
		return this.iterationsNo;
	}

	public void setIterationsNo(int iterationsNo) {
		this.iterationsNo = iterationsNo;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
