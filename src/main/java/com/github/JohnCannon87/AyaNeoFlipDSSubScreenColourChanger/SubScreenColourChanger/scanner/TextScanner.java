package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextScanner {

	public List<File> scanFilesForColourCode(List<File> filesToScan, String colourCode) {
		List<File> matchingFiles = new ArrayList<>();

		for (File file : filesToScan) {
			if (containsText(file, colourCode)) {
				matchingFiles.add(file);
				logFileFound(file, colourCode);
			}
		}

		return matchingFiles;
	}

	private void logFileFound(File file, String colourCode) {
		System.out.println(
				String.format("File %s Scanned and Found Colour Code %s to replace", file.getName(), colourCode));
	}

	private boolean containsText(File file, String searchText) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains(searchText)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}
