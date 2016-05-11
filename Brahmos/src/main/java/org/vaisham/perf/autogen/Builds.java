package org.vaisham.perf.autogen;

import java.util.Date;

/**
 * @author Vaishampayan Reddy
 */
public class Builds implements java.io.Serializable {

	private static final long serialVersionUID = 796382141534960785L;
	private BuildsId id;
	private String description;
	private byte[] buildInfo;
	private Date lastModifiedDate;
	private Date createdDate;
	private boolean isDeleted;
	private String comments;

	public Builds() {
	}

	public Builds(BuildsId id, String description, Date lastModifiedDate, Date createdDate, boolean isDeleted,
			String comments) {
		this.id = id;
		this.description = description;
		this.lastModifiedDate = lastModifiedDate;
		this.createdDate = createdDate;
		this.isDeleted = isDeleted;
		this.comments = comments;
	}

	public Builds(BuildsId id, String description, byte[] buildInfo, Date lastModifiedDate, Date createdDate,
			boolean isDeleted, String comments) {
		this.id = id;
		this.description = description;
		this.buildInfo = buildInfo;
		this.lastModifiedDate = lastModifiedDate;
		this.createdDate = createdDate;
		this.isDeleted = isDeleted;
		this.comments = comments;
	}

	public BuildsId getId() {
		return this.id;
	}

	public void setId(BuildsId id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getBuildInfo() {
		return this.buildInfo;
	}

	public void setBuildInfo(byte[] buildInfo) {
		this.buildInfo = buildInfo;
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
