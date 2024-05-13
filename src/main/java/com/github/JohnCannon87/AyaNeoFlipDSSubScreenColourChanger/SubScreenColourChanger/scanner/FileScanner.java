package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {

	public List<File> scanForJsFiles(String directoryPath) {
		List<File> fileList = new ArrayList<>();
		File directory = new File(directoryPath);

		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && fileIsAJSFile(file)) {
						fileList.add(file);
					} else if (file.isDirectory()) {
						// Ignore no directory traversal should be needed
					}
				}
			}
		} else {
			System.err.println("Directory does not exist or is not a directory: " + directoryPath);
		}

		return fileList;
	}

	private void logFoundFile(File file) {
		System.out.println(String.format("Found File: %s for modification", file.getName()));
	}

	private boolean fileIsAJSFile(File file) {
		return file.getName().endsWith(".js");
	}

}