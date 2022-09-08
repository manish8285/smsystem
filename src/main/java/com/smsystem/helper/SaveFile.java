package com.smsystem.helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;


import com.smsystem.SmsystemApplication;

public  class SaveFile {
	
	
	public static String saveFile(MultipartFile file) {
		String tpath=null;
		String final_filename=null;
		
		ApplicationHome home = new ApplicationHome(SmsystemApplication.class);
		File dir = home.getDir();
		String absolutePath = dir.getAbsolutePath();
		File f = new File(absolutePath+File.separator+"public");
		if(!f.exists()) {
			f.mkdir();
		}
		
		
		
		
		try {
			String randomString =Double.toString(Math.random()).substring(2, 10);
			final_filename = randomString +file.getOriginalFilename();
			tpath= absolutePath+File.separator+"public"+File.separator+"img" +File.separator+final_filename;
			Path path = Paths.get(tpath);
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return final_filename;
	}
	
	public static void deleteFile(String name) {
		try {
			ApplicationHome home = new ApplicationHome(SmsystemApplication.class);
			File dir = home.getDir();
			String absolutePath = dir.getAbsolutePath();
			String spath =absolutePath+File.separator+"public"+File.separator+"img" +File.separator+name;
			Path path = Paths.get(spath);
			Files.deleteIfExists(path);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
