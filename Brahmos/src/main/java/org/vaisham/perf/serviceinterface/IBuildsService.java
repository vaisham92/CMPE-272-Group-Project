package org.vaisham.perf.serviceinterface;

import java.util.List;

import org.vaisham.perf.autogen.Builds;
import org.vaisham.perf.dto.BuildsDTO;

public interface IBuildsService {

	public Builds createBuild(int id, int projectId, String description,
			byte[] buildInfo, String comments);

	public Builds updateBuild(int id, int scenarioId, String description,
			byte[] buildInfo, String comments);

	public Builds getBuild(int id, int projectId);

	public List<BuildsDTO> getBuildsByProjectId(int projectId);

	public void deleteBuild(int id, int scenarioId);
}
