package generator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.*;
import java.nio.file.*;

public class Main {
    public static void cloneRepository(String repositoryUrl, String destinationDirectory) {
        try {
            Git.cloneRepository().setURI(repositoryUrl).setDirectory(new File(destinationDirectory)).call().close();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteFile(String filePath) {
    	File file = new File(filePath);
    	if (file.exists()) {
    		if (file.isDirectory())
    			deleteDirectory(file);
    		else
    			file.delete();
    	}
    }
    
    private static void deleteDirectory(File directory) {
	    File[] contents = directory.listFiles();
	    if (contents != null) {
	        for (File file : contents) {
	        	if (file.isDirectory())
	                deleteDirectory(file);
	        	else 
	        		file.delete();
	    	}
	    }
	    directory.delete();
    }
    
    public static void copyFile(String sourcePath, String destinationPath) {
    	File sourceFile = new File(sourcePath);
    	File destinationFile = new File(destinationPath);
    	
    	if (sourceFile.isDirectory())
    		copyDirectory(sourceFile, destinationFile);
    	else
    		copyFile(sourceFile, destinationFile);
    }
    
    private static void copyDirectory(File sourceDirectory, File destinationDirectory) {
    	if (!destinationDirectory.exists())
    		destinationDirectory.mkdir();
    	
    	File[] contents = sourceDirectory.listFiles();
    	for (File file : contents) {
    		if (file.isDirectory())
    			copyDirectory(file, new File(destinationDirectory, file.getName()));
    		else
    			copyFile(file, new File(destinationDirectory, file.getName()));
    	}
    }
    
    private static void copyFile(File sourceFile, File destinationFile) {
    	if (destinationFile.exists()) {
    		if (destinationFile.isDirectory())
    			deleteDirectory(destinationFile);
    		else
    			destinationFile.delete();
    	}
    	
    	try {
			Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    public static void main(String[] args) {
        String repositoryUrl = "https://github.com/MS-101/aosd-project";
        String cloneDirectory = "clone";
        String configDirectory = "config";
        String securityDirectory = "src/security";

        deleteFile(cloneDirectory);
        cloneRepository(repositoryUrl, cloneDirectory);
        copyFile(cloneDirectory + "/" + configDirectory, configDirectory);
        copyFile(cloneDirectory + "/" + securityDirectory, securityDirectory);
        deleteFile(cloneDirectory);
    }
}
