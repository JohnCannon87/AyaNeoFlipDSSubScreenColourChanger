package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.FilePathPair;

public class FileScanner {

	public List<FilePathPair> scanForJsFiles(String directoryPath) {
		List<FilePathPair> fileList = new ArrayList<>();
		File directory = new File(directoryPath);

		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && fileIsAJSFile(file)) {
						fileList.add(createFilePathPair(file));
					} else if (file.isDirectory() && file.getName().equals("js")) {
						fileList.addAll(scanForJsFiles(file.getAbsolutePath()));
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

	private FilePathPair createFilePathPair(File file) {
		return FilePathPair.builder()
				.file(file)
				.path(file.getAbsolutePath())
				.build();
	}

}