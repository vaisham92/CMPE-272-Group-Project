package org.vaisham.perf.core;

import java.io.FileNotFoundException;

public interface IJmeterCSVReader {
	public String extractBuildDataFromFile(String filepath) throws FileNotFoundException;
}
