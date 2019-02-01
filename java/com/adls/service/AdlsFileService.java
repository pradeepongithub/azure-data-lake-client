package com.adls.service;

import java.io.IOException;

public interface AdlsFileService {
	
boolean createDirectory(String name) throws IOException;

boolean createFile(String name) throws IOException;

void uploadFile(String name, String path) throws IOException;

String downloadReadFile(String name) throws IOException;

void updateFile(String name) throws IOException;

void deleteDirectory(String name) throws IOException;

String getFileMetaData(String name) throws IOException;

boolean renameFile(String name, String newName) throws IOException;

String listDirectoryContent(String name) throws IOException;

}
