package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Scanner;

public class SubScreenColourChangerApplication {

	static Scanner scanner = new Scanner(System.in);
	static FileModificationData fileModificationData = null;
	static FileModifier fileModifier = new FileModifier();

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			if (args.length == 1) {
				if ("RESTORE".equals(args[0])) {
					restoreBackup();
				}
			} else if (args.length != 6) {
				System.err.println("Incorrect number of arguments provided, usage should be like this:\n"
						+ "#12455f, #2d95c9, #12455f \"34 140 204\" \"30 115 125\" \"14 59 59\"");
			} else {
				fileModificationData = FileModificationData.createFromArgs(args);

				if (fileModificationData != null) {
					modifyFiles(fileModificationData);
				}
			}
		}
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
				modifyFiles();
				break;
			}
			case 2: {
				printToUser("RESTORING BACKUP...");
				restoreBackup();
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
		} catch (FileNotFoundException | AccessDeniedException e) {
			System.err.println(
					"Exception, most likely you need to run the application as an administrator");
		} catch (Exception e) {
			e.printStackTrace();
		}
		entryLoop(scanner);

	}

	private static void modifyFiles() throws IOException {
		fileModificationData = FileModificationData.create(false);
		modifyFiles(fileModificationData);
	}

	private static void modifyFiles(FileModificationData fileModificationData) throws IOException {
		printToUser("MODIFYING FILES NOW :)");
		fileModifier.modifyFiles(fileModificationData);
		printToUser("FILES MODIFIED PLEASE RESTART AYA SPACE/CONSOLE :)");
	}

	private static void restoreBackup() throws IOException {
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
