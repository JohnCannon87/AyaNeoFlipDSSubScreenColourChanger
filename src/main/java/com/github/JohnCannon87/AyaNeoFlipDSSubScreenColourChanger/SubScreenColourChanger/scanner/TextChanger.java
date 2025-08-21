package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.FilePathPair;

public class TextChanger {

	public void modifyFilesReplaceCodeOneWithCodeTwo(List<FilePathPair> filesForModification, String oldColourCode,
			String newColourCode) throws IOException {
		for (FilePathPair file : filesForModification) {
			updateFile(oldColourCode, newColourCode, file.getFile());
			logFileUpdated(file.getFile());
		}
	}

	private void updateFile(String oldColourCode, String newColourCode, File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileContent = stringBuilder.toString();
		oldColourCode = "\\Q" + oldColourCode + "\\E";
		fileContent = fileContent.replaceAll(oldColourCode, newColourCode);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(fileContent);
		} catch (IOException e) {
			throw e;
		}
	}

	private void logFileUpdated(File file) {
		System.out.println(String.format("File %s updated with new colour code", file.getName()));
	}

}
