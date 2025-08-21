package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.FilePathPair;

public class TextScanner {

	public List<FilePathPair> scanFilesForColourCode(List<FilePathPair> filesToScan, String colourCode) {
		List<FilePathPair> matchingFiles = new ArrayList<>();

		for (FilePathPair file : filesToScan) {
			if (containsText(file, colourCode)) {
				matchingFiles.add(file);
				logFileFound(file, colourCode);
			}
		}

		return matchingFiles;
	}

	private void logFileFound(FilePathPair file, String colourCode) {
		System.out.println(
				String.format("File %s Scanned and Found Colour Code %s to replace", file.getFile().getName(),
						colourCode));
	}

	private boolean containsText(FilePathPair file, String searchText) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file.getFile()))) {
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
