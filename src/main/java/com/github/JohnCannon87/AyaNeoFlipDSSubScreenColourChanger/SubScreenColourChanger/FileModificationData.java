package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileModificationData {
	private static String DEFAULT_FILE_PATH = "C:\\Program Files (x86)\\AYASpace\\frontend\\js";
	private static String DEFAULT_BACKUP_LOCATION = DEFAULT_FILE_PATH + "\\backup";
	private static String DEFAULT_BACKGROUND_CODE = "#12455f";
	private static String DEFAULT_TEXT_CODE = "#2d95c9";
	private static String DEFAULT_TDP_ICON_CODE = "#12455f";
	private static String DEFAULT_GRADIENT_ONE = "34 140 204";
	private static String DEFAULT_GRADIENT_TWO = "30 115 125";
	private static String DEFAULT_GRADIENT_THREE = "14 59 59";
	private static String GRADIENT_ONE_PATTERN = "rgba(%s %s %s / 50%%) 2.91%%";
	private static String GRADIENT_TWO_PATTERN = "rgba(%s %s %s / 50%%) 47.33%%";
	private static String GRADIENT_THREE_PATTERN = "rgba(%s %s %s / 50%%) 91.75%%)";
	private static String DEFAULT_SELECTION = "D";
	private static String HEX_COLOUR_FORMAT = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
	private static String RGB_COLOUR_FORMAT = "^\\d{1,3} \\d{1,3} \\d{1,3}$";

	private String filePath;
	private String backupLocation;
	private String newBackgroundColourCode;
	private String newTextColourCode;
	private String newTDPColourCode;
	private String newGradientOne;
	private String newGradientTwo;
	private String newGradientThree;

	private static String formatGradient(String pattern, String newGradient) {
		Object[] split = newGradient.split(" ");
		return String.format(pattern, split);
	}

	public static FileModificationData createFromArgs(String[] args) {
		String newBackgroundColourCode = args[0];
		String newTextColourCode = args[1];
		String newTDPColourCode = args[2];
		String newGradientOne = args[3];
		String newGradientTwo = args[4];
		String newGradientThree = args[5];

		boolean anyValuesWrong = false;
		anyValuesWrong = anyValuesWrong || checkIfValueMatchesFormat(newBackgroundColourCode, HEX_COLOUR_FORMAT,
				"Background Colour Code", "#000000");
		anyValuesWrong = anyValuesWrong
				|| checkIfValueMatchesFormat(newTextColourCode, HEX_COLOUR_FORMAT, "Text Colour Code", "#000000");
		anyValuesWrong = anyValuesWrong
				|| checkIfValueMatchesFormat(newTDPColourCode, HEX_COLOUR_FORMAT, "TDP Icon Colour Code", "#000000");
		anyValuesWrong = anyValuesWrong
				|| checkIfValueMatchesFormat(newGradientOne, RGB_COLOUR_FORMAT, "Gradient Value 1", "\"000 000 000\"");
		anyValuesWrong = anyValuesWrong
				|| checkIfValueMatchesFormat(newGradientTwo, RGB_COLOUR_FORMAT, "Gradient Value 2", "\"000 000 000\"");
		anyValuesWrong = anyValuesWrong || checkIfValueMatchesFormat(newGradientThree, RGB_COLOUR_FORMAT,
				"Gradient Value 3", "\"000 000 000\"");

		if (anyValuesWrong) {
			System.err.println("Please correct incorrect values from argument before running again");
			return null;
		} else {

			return new FileModificationDataBuilder()
					.filePath(DEFAULT_FILE_PATH)
					.backupLocation(DEFAULT_BACKUP_LOCATION)
					.newBackgroundColourCode(newBackgroundColourCode)
					.newTextColourCode(newTextColourCode)
					.newTDPColourCode(newTDPColourCode)
					.newGradientOne(formatGradient(GRADIENT_ONE_PATTERN, newGradientOne))
					.newGradientTwo(formatGradient(GRADIENT_TWO_PATTERN, newGradientTwo))
					.newGradientThree(formatGradient(GRADIENT_THREE_PATTERN, newGradientThree))
					.build();
		}
	}

	private static boolean checkIfValueMatchesFormat(String stringToCheck, String format, String value,
			String expectedValue) {
		if (valueDoesNotMatchFormat(stringToCheck, format)) {
			System.err.println(String.format("%s is in an incorrect format for %s !\nShould be like: %s",
					stringToCheck, value, expectedValue));
			return true;
		}
		return false;
	}

	public static FileModificationData create(boolean justFilePaths) {
		SubScreenColourChangerApplication.printToUser("Do you wish to use default values for everything ? (y/n)");
		String decision = SubScreenColourChangerApplication.getValueFromUser();
		switch (decision) {
		case "y": {
			return FileModificationData.defaultValues();
		}
		case "n": {
			return FileModificationData.getValuesFromUser(justFilePaths);
		}
		default:
			System.err.println(String.format("Invalid Selection: %s" + "\n", decision));
			return create(justFilePaths);
		}
	}

	public static FileModificationData defaultValues() {
		return new FileModificationDataBuilder()
				.filePath(DEFAULT_FILE_PATH)
				.backupLocation(DEFAULT_BACKUP_LOCATION)
				.newBackgroundColourCode(DEFAULT_BACKGROUND_CODE)
				.newTextColourCode(DEFAULT_TEXT_CODE)
				.newTDPColourCode(DEFAULT_TDP_ICON_CODE)
				.newGradientOne(formatGradient(GRADIENT_ONE_PATTERN, DEFAULT_GRADIENT_ONE))
				.newGradientTwo(formatGradient(GRADIENT_TWO_PATTERN, DEFAULT_GRADIENT_TWO))
				.newGradientThree(formatGradient(GRADIENT_THREE_PATTERN, DEFAULT_GRADIENT_THREE))
				.build();
	}

	public static FileModificationData getValuesFromUser(boolean justFilePaths) {
		FileModificationDataBuilder builder = new FileModificationDataBuilder()
				.filePath(getValueFromUser(DEFAULT_FILE_PATH, "File Path"))
				.backupLocation(getValueFromUser(DEFAULT_BACKUP_LOCATION, "Backup File Location"));
		if (!justFilePaths) {
			builder
					.newBackgroundColourCode(
							getHexColourCodeFromUser(DEFAULT_BACKGROUND_CODE,
									"Background Colour Hex Code (Format #000000)"))
					.newTextColourCode(
							getHexColourCodeFromUser(DEFAULT_TEXT_CODE,
									"Text Colour Hex Code (Format #000000 or #000)"))
					.newTDPColourCode(
							getHexColourCodeFromUser(DEFAULT_TDP_ICON_CODE,
									"TDP Icon Colour Hex Code (Format #000000 or #000)"))
					.newGradientOne(formatGradient(GRADIENT_ONE_PATTERN,
							getRGBColourCodeFromUser(DEFAULT_GRADIENT_ONE,
									"Gradient One rgb Colour Code (Format 000 000 000)")))
					.newGradientTwo(formatGradient(GRADIENT_TWO_PATTERN,
							getRGBColourCodeFromUser(DEFAULT_GRADIENT_TWO,
									"Gradient Two rgb Colour Code (Format 000 000 000)")))
					.newGradientThree(formatGradient(GRADIENT_THREE_PATTERN,
							getRGBColourCodeFromUser(DEFAULT_GRADIENT_THREE,
									"Gradient Three rgb Colour Code (Format 000 000 000)")));
		}
		return builder.build();
	}

	private static String getValueFromUser(String defaultValue, String variableAskingFor) {
		String message = String.format("Requesting Value for %s, Default Value is: %s (Type D For Default Value)",
				variableAskingFor, defaultValue);
		SubScreenColourChangerApplication.printToUser(message);
		String scannerVal = SubScreenColourChangerApplication.getValueFromUser();
		if (DEFAULT_SELECTION.equals(scannerVal)) {
			return defaultValue;
		} else {
			return scannerVal;
		}
	}

	private static String getHexColourCodeFromUser(String defaultValue, String variableAskingFor) {
		String message = String.format("Requesting Value for %s, Default Value is: %s (Type D For Default Value)",
				variableAskingFor, defaultValue);
		SubScreenColourChangerApplication.printToUser(message);
		String scannerVal = SubScreenColourChangerApplication.getValueFromUser();
		if (DEFAULT_SELECTION.equals(scannerVal)) {
			return defaultValue;
		} else if (valueDoesNotMatchFormat(scannerVal, HEX_COLOUR_FORMAT)) {
			System.err.println("Incorrect Format !\n");
			return getHexColourCodeFromUser(defaultValue, variableAskingFor);
		} else {
			return scannerVal;
		}
	}

	private static String getRGBColourCodeFromUser(String defaultValue, String variableAskingFor) {
		String message = String.format("Requesting Value for %s, Default Value is: %s (Type D For Default Value)",
				variableAskingFor, defaultValue);
		SubScreenColourChangerApplication.printToUser(message);
		String scannerVal = SubScreenColourChangerApplication.getValueFromUser();
		if (DEFAULT_SELECTION.equals(scannerVal)) {
			return defaultValue;
		} else if (valueDoesNotMatchFormat(scannerVal, RGB_COLOUR_FORMAT)) {
			System.err.println("Incorrect Format !\n");
			return getRGBColourCodeFromUser(defaultValue, variableAskingFor);
		} else {
			return scannerVal;
		}
	}

	private static boolean valueDoesNotMatchFormat(String scannerVal, String patternString) {
		// Compile the regular expression pattern
		Pattern pattern = Pattern.compile(patternString);

		// Match the given color string against the pattern
		Matcher matcher = pattern.matcher(scannerVal);

		// Return true if the color string matches the pattern, false otherwise
		return !matcher.matches();
	}
}
