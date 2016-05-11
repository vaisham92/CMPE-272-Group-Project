package org.vaisham.perf.dto;

import java.util.Date;

import org.vaisham.perf.autogen.Builds;
import org.vaisham.perf.autogen.BuildsId;

public class BuildsDTO implements java.io.Serializable {

	private static final long serialVersionUID = 9028250243475852110L;
	private BuildsId id;
	private String description;
	private Date lastModifiedDate;
	private Date createdDate;
	private boolean isDeleted;
	private String comments;

	public BuildsDTO(Builds build) {
		this.id = build.getId();
		this.description = build.getDescription();
		this.lastModifiedDate = build.getLastModifiedDate();
		this.createdDate = build.getCreatedDate();
		this.isDeleted = build.isIsDeleted();
		this.comments = build.getComments();
	}

	public BuildsDTO(BuildsId id, String description,
			Date lastModifiedDate, Date createdDate, boolean isDeleted,
			String comments) {
		this.id = id;
		this.description = description;
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
