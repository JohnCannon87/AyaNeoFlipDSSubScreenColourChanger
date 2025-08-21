package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.FilePathPair;

public class FileBackup {

	public void backupFiles(List<FilePathPair> filesForModification, String backupLocation) throws IOException {
		File mainDirectory = new File(backupLocation);
		File jsDirectory = new File(backupLocation + File.separator + "js");

		// Check if the destination directory exists, if not create it
		checkAndCreateDirectory(backupLocation, mainDirectory);
		checkAndCreateDirectory(backupLocation + File.separator + "js", jsDirectory);

		// Copy files to the destination directory
		for (FilePathPair filePathPair : filesForModification) {
			File file = filePathPair.getFile();
			try {
				if (fileIsInTheJsDirectory(file)) {
					Files.copy(file.toPath(), new File(jsDirectory, file.getName()).toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				} else {
					Files.copy(file.toPath(), new File(mainDirectory, file.getName()).toPath(),
							StandardCopyOption.REPLACE_EXISTING);
				}
				System.out.println("File copied: " + file.getName());
			} catch (IOException e) {
				System.err.println("Failed to copy file: " + file.getName());
				throw e;
			}
		}
	}

	private boolean fileIsInTheJsDirectory(File file) {
		File parent = file.getParentFile();
		return parent != null && "js".equals(parent.getName());
	}

	private void checkAndCreateDirectory(String backupLocation, File mainDirectory) {
		if (!mainDirectory.exists()) {
			if (!mainDirectory.mkdirs()) {
				System.err.println("Failed to create directory: " + backupLocation);
				return;
			}
			System.out.println("Directory created: " + backupLocation);
		}
	}

}
