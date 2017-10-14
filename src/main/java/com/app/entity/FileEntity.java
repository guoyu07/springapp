package com.app.entity;

import java.io.File;

public class FileEntity {
	File file;

	public FileEntity(File file) {
		this.file = file;
	}

	public File getFileText() {
		return file;
	}

	public void setFileText(File file) {
		this.file = file;
	}
}
