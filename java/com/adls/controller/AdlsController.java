package com.adls.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adls.service.AdlsFileService;

@RestController
@RequestMapping(path = "/api/v1/adls/file")
public class AdlsController {
	
@Autowired
private AdlsFileService service;

@PostMapping(value="/dr")
public boolean createDirectory(String name) throws IOException {
	
return service.createDirectory(name);
	
}

@PostMapping(value="/fl")
public boolean createFile(String name) throws IOException {
	
return service.createFile(name);
	
}

@PostMapping(value="/up")
public boolean uploadFile(String name) throws IOException {

String path ="/home/inblr01-09/poc-workspace/adls-client/src/main/resources/files/DriverShiftTrips.csv";	
service.uploadFile(name, path);
return true;
	
}

@PostMapping(value="/dw")
public String readDownload(String name) throws IOException {

return service.downloadReadFile(name);

}

@PutMapping(value="/uf")
public boolean updateFile(String name) throws IOException {
	
service.updateFile(name);	
	
return true;
}

@PostMapping(value="/md")
public String getFileMetaData(String name) throws IOException {
	
return service.getFileMetaData(name);

}

@PutMapping(value="/re")
public boolean renameFile(String name, String newName) throws IOException {
	
return service.renameFile(name, newName);	
	
}

@PostMapping(value="/ls")
public String listDirectoryContent(String name) throws IOException {
	
return service.listDirectoryContent(name);

}

@DeleteMapping
public boolean deleteDirectory(String name) throws IOException {
	
service.deleteDirectory(name);

return true;

}



}
