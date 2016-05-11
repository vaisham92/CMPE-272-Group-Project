package org.vaisham.perf.sql;

/**
 * This is the query class where you write queries to get data.
 * 
 * @author KH1868
 * 
 */
public class SqlQuery {

	// Projects Queries
	public static final String GET_ALL_PROJECTS = "SELECT P FROM Projects P";
	public static final String GET_PROJECT_BY_PROJECT_ID = "Select P from Projects P where P.id = ?";
	public static final String GET_ALL_PROJECT_NAMES = "Select P.name from Projects P";
	public static final String GET_PROJECT_NAMES_EXCEPT = "Select P.name from Projects P where P.id != ?";

	// Builds Queries
	public static final String GET_BUILDS_BY_PROJECT_ID = "Select b from Builds b where b.id.projectId = ?";
	public static final String GET_BUILD_BY_BUILD_ID_AND_PROJECT_ID = "Select b from Builds b where b.id.id = ? and b.id.projectId = ?";
}
