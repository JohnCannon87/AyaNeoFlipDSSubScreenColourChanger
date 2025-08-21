package com.github.JohnCannon87.AyaNeoFlipDSSubScreenColourChanger.SubScreenColourChanger;

import java.io.File;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilePathPair {

	private File file;
	private String path;

}
