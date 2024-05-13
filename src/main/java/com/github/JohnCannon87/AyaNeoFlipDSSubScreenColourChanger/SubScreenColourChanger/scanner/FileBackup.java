package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileBackup {

	public void backupFiles(List<File> filesForModification, String backupLocation) throws IOException {
		File directory = new File(backupLocation);

		// Check if the destination directory exists, if not create it
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				System.err.println("Failed to create directory: " + backupLocation);
				return;
			}
			System.out.println("Directory created: " + backupLocation);
		}

		// Copy files to the destination directory
		for (File file : filesForModification) {
			try {
				Files.copy(file.toPath(), new File(directory, file.getName()).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				System.out.println("File copied: " + file.getName());
			} catch (IOException e) {
				System.err.println("Failed to copy file: " + file.getName());
				throw e;
			}
		}
	}

}
