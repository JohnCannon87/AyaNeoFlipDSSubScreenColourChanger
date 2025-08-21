package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner.FileBackup;
import com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner.FileScanner;
import com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner.TextChanger;
import com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger.scanner.TextScanner;

public class FileModifier {

	private FileScanner fileScanner = new FileScanner();
	private TextScanner textScanner = new TextScanner();
	private TextChanger textChanger = new TextChanger();
	private FileBackup fileBackup = new FileBackup();

	private static String BACKGROUND_COLOUR_CODE = "#441511";
	private static String TEXT_COLOUR_CODE = "#e06162";
	private static String TDP_COLOUR_CODE = "#732c2c";
	private static String GRADIENT_ONE = "rgba(204, 34, 34, 0.5) 2.91%";
	private static String GRADIENT_TWO = "rgba(125, 30, 30, 0.5) 47.33%";
	private static String GRADIENT_THREE = "rgba(59, 56, 14, 0.5) 91.75%)";

	public void modifyFiles(FileModificationData fileModificationData) throws IOException {
		List<FilePathPair> jsFiles = fileScanner.scanForJsFiles(fileModificationData.getFilePath());

		backupFilesToBeModified(jsFiles, fileModificationData.getBackupLocation());

		updateColour(jsFiles, BACKGROUND_COLOUR_CODE, fileModificationData.getNewBackgroundColourCode());
		updateColour(jsFiles, TEXT_COLOUR_CODE, fileModificationData.getNewTextColourCode());
		updateColour(jsFiles, TDP_COLOUR_CODE, fileModificationData.getNewTDPColourCode());
		updateColour(jsFiles, GRADIENT_ONE, fileModificationData.getNewGradientOne());
		updateColour(jsFiles, GRADIENT_TWO, fileModificationData.getNewGradientTwo());
		updateColour(jsFiles, GRADIENT_THREE, fileModificationData.getNewGradientThree());
	}

	private void backupFilesToBeModified(List<FilePathPair> jsFiles, String backupLocation) throws IOException {
		List<FilePathPair> filesForModification = new ArrayList();
		filesForModification.addAll(textScanner.scanFilesForColourCode(jsFiles, BACKGROUND_COLOUR_CODE));
		filesForModification.addAll(textScanner.scanFilesForColourCode(jsFiles, TEXT_COLOUR_CODE));
		filesForModification.addAll(textScanner.scanFilesForColourCode(jsFiles, TDP_COLOUR_CODE));
		filesForModification.addAll(textScanner.scanFilesForColourCode(jsFiles, GRADIENT_ONE));
		filesForModification.addAll(textScanner.scanFilesForColourCode(jsFiles, GRADIENT_TWO));
		filesForModification.addAll(textScanner.scanFilesForColourCode(jsFiles, GRADIENT_THREE));
		fileBackup.backupFiles(filesForModification, backupLocation);
	}

	private void updateColour(List<FilePathPair> jsFiles, String oldColourCode, String newColourCode)
			throws IOException {
		List<FilePathPair> filesForModification = textScanner.scanFilesForColourCode(jsFiles, oldColourCode);

		textChanger.modifyFilesReplaceCodeOneWithCodeTwo(filesForModification, oldColourCode, newColourCode);
	}

	public void restoreBackupFiles(FileModificationData fileModificationData) throws IOException {
		List<FilePathPair> jsFiles = fileScanner.scanForJsFiles(fileModificationData.getBackupLocation());
		fileBackup.backupFiles(jsFiles, fileModificationData.getFilePath());
	}
}