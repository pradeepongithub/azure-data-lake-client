package com.adls.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adls.service.AdlsFileService;
import com.microsoft.azure.datalake.store.ADLStoreClient;
import com.microsoft.azure.datalake.store.DirectoryEntry;
import com.microsoft.azure.datalake.store.IfExists;
import com.microsoft.azure.datalake.store.acl.AclAction;
import com.microsoft.azure.datalake.store.acl.AclEntry;
import com.microsoft.azure.datalake.store.acl.AclScope;
import com.microsoft.azure.datalake.store.acl.AclType;

@Service
public class AdlsFileServiceImpl implements AdlsFileService {
	
@Autowired
private AdlsClientService adlsStoreClient;

@Override
public boolean createDirectory(String name) throws IOException {
	
ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

boolean response = client.createDirectory(name, "777");

AclEntry acl = new AclEntry(AclScope.ACCESS, AclType.GROUP, "digital-platform-core-services", AclAction.ALL);

client.modifyAclEntries(name, Arrays.asList(acl));
	
return response;

}

@Override
public boolean createFile(String name) throws IOException {

ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

client.createFile(name, IfExists.OVERWRITE, "777", true);

return true;
	
}

@Override
public void uploadFile(String name, String path) throws IOException {
	
ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

OutputStream stream = client.createFile(name, IfExists.OVERWRITE, "777", true);

PrintStream out = new PrintStream(stream);

out.write(Files.readAllBytes(new File(path).toPath()));

out.close();
	
}

@Override
public String downloadReadFile(String name) throws IOException {
	
ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

InputStream in = client.getReadStream(name);
BufferedReader reader = new BufferedReader(new InputStreamReader(in));
StringBuffer sb =new StringBuffer();

String line;

while ((line = reader.readLine()) != null) {
  System.out.println(line);
  sb.append(line);
}
reader.close();
System.out.println();
return sb.toString();	
}

@Override
public void updateFile(String name) throws IOException {
	
ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

OutputStream stream = client.getAppendStream(name);
stream.write(getSampleContent());
stream.close();
	
}

@Override
public void deleteDirectory(String name) throws IOException {
	
ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

// delete directory along with all the sub directories and files in it
client.deleteRecursive(name);	
	
}

@Override
public String getFileMetaData(String name) throws IOException {
ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

DirectoryEntry ent = client.getDirectoryEntry(name);
StringBuffer sb = new StringBuffer();
printDirectoryInfo(ent, sb);
return sb.toString();	
}

@Override
public boolean renameFile(String name, String newName) throws IOException {
ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

boolean response = client.rename(name, newName);

client.setPermission(newName, "777");

return response;	

}

@Override
public String listDirectoryContent(String name) throws IOException {
ADLStoreClient client = adlsStoreClient.getAdlsStoreClient();

List<DirectoryEntry> list = client.enumerateDirectory(name, 2000);
System.out.println("Directory listing for directory : " + name);
StringBuffer sb = new StringBuffer();
for (DirectoryEntry entry : list) {
  printDirectoryInfo(entry, sb);
}
return sb.toString();	
}

private static byte[] getSampleContent() {
ByteArrayOutputStream s = new ByteArrayOutputStream();
PrintStream out = new PrintStream(s);
out.println("This is a line");
out.println("This is another line");
out.println("This is yet another line");
out.println("This is yet yet another line");
out.println("This is yet yet yet another line");
out.println("... and so on, ad infinitum");
out.println();
out.close();
return s.toByteArray();

}

private static void printDirectoryInfo(DirectoryEntry ent, StringBuffer sb) {
System.out.format("Name: %s%n", ent.name);
System.out.format("  FullName: %s%n", ent.fullName);
System.out.format("  Length: %d%n", ent.length);
System.out.format("  Type: %s%n", ent.type.toString());
System.out.format("  Group: %s%n", ent.group);
System.out.format("  User: %s%n", ent.user);
System.out.format("  Permission: %s%n", ent.permission);
System.out.format("  mtime: %s%n", ent.lastModifiedTime.toString());
System.out.format("  atime: %s%n", ent.lastAccessTime.toString());
System.out.println();
appendInfo(ent, sb);

}

private static void appendInfo(DirectoryEntry ent, StringBuffer sb) {
	sb.append("Name: ").append(" ").append(ent.name);
	sb.append(System.getProperty("line.separator"));
	sb.append("FullName: ").append(" ").append(ent.fullName);
	sb.append(System.getProperty("line.separator"));
	sb.append("	Length: ").append(" ").append(ent.type.toString());
	sb.append(System.getProperty("line.separator"));
	sb.append("	Type: ").append(" ").append(ent.name);
	sb.append(System.getProperty("line.separator"));
	sb.append("	Group: ").append(" ").append(ent.group);
	sb.append(System.getProperty("line.separator"));
	sb.append("	User: ").append(" ").append(ent.user);
	sb.append(System.getProperty("line.separator"));
	sb.append("	Permission: ").append(" ").append(ent.permission);
	sb.append(System.getProperty("line.separator"));
	sb.append("	mtime: ").append(" ").append(ent.lastModifiedTime.toString());
	sb.append(System.getProperty("line.separator"));
	sb.append("	atime: ").append(" ").append(ent.lastAccessTime.toString());
	sb.append(System.getProperty("line.separator"));
}


}
