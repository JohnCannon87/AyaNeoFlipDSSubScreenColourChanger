package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SubScreenColourChangerApplication {

	static Scanner scanner = new Scanner(System.in);
	static FileModificationData fileModificationData = null;
	static FileModifier fileModifier = new FileModifier();

	public static void main(String[] args) {
		entryLoop(scanner);
	}

	private static void entryLoop(Scanner scanner) {
		try {
			printToUser(
					"Please select option:\n"
							+ "1) Modify AYASpace Colour Files\n"
							+ "2) Restore AYASpace Colour Files From Backup\n"
							+ "0) Exit");
			int selection = scanner.nextInt();
			switch (selection) {
			case 1: {
				printToUser("MODIFYING FILES...");
				modifyFiles(scanner);
				break;
			}
			case 2: {
				printToUser("RESTORING BACKUP...");
				restoreBackup(scanner);
				break;
			}
			case 0: {
				printToUser("EXITING BYE :)");
				System.exit(0);
				break;
			}
			default:
				System.err.println(String.format("Invalid Selection: %s" + "\n", selection));
			}
			entryLoop(scanner);
		} catch (FileNotFoundException e) {
			System.err.println(
					"File Not Found Exception, most likely you need to run the application as an administrator");
		} catch (Exception e) {
			e.printStackTrace();
		}
		entryLoop(scanner);

	}

	private static void modifyFiles(Scanner scanner) throws IOException {
		fileModificationData = FileModificationData.create(false);

		printToUser("MODIFYING FILES NOW :)");
		fileModifier.modifyFiles(fileModificationData);
		printToUser("FILE MODIFIED PLEASE RESTART AYA SPACE/CONSOLE :)");
	}

	private static void restoreBackup(Scanner scanner) throws IOException {
		if (fileModificationData == null) {
			fileModificationData = FileModificationData.create(true);
		}
		fileModifier.restoreBackupFiles(fileModificationData);
	}

	public static void printToUser(String message) {
		System.out.println(message + "\n");
	}

	public static String getValueFromUser() {
		String returnValue = scanner.nextLine();
		if (returnValue.isEmpty()) {
			return getValueFromUser();
		} else {
			return returnValue;
		}
	}

}
