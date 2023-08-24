package com.cartsy.ecom.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class FileRepository {
	private static final Logger logger = LoggerFactory.getLogger(FileRepository.class);
	

	public static boolean saveFile(String filePath, MultipartFile data, Integer view) {
		try {
			System.out.print("file path" + filePath) ;
			Files.createDirectories(Paths.get(filePath));
			
			Files.copy(data.getInputStream(), Paths.get(filePath).resolve(view + data.getOriginalFilename().substring(data.getOriginalFilename().lastIndexOf("."))));
			return true;
			
		}catch(Exception e) {
			logger.error( "Error saving the file.",e);
			return false;
			
		}
	}


	public static Resource readFile(String path) {
		try {
			Path filePath = Paths.get(path);
			
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			}
			
			return null;
				
		}catch(Exception e) {
			logger.error( "Error reading the file.",e);
		}
		return null;
		
	}


	public static Resource readProductImage(String pathWithoutExtension) {
		try {
			Path filePathPng = Paths.get(pathWithoutExtension+ ".png");
			Path filePathJpg = Paths.get(pathWithoutExtension+ ".jpg");
			Path filePathJpeg = Paths.get(pathWithoutExtension+ ".jpeg");
			
			
			Resource resourcePng = new UrlResource(filePathPng.toUri());
			Resource resourceJpg = new UrlResource(filePathJpg.toUri());
			Resource resourceJpeg = new UrlResource(filePathJpeg.toUri());

			if(resourcePng.exists()) {
				return resourcePng;
			}
			if(resourceJpg.exists()) {
				return resourceJpg;
			}
			if(resourceJpeg.exists()) {
				return resourceJpeg;
			}
			
			return null;
				
		}catch(Exception e) {
			logger.error( "Error reading the file.",e);
		}
		return null;
		
	}
	
	

	

}
