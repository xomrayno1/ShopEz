package com.app.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class AppUtils {
 
	public static String uploadFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		File file = new File(Constant.PATH_RESOURCE);
		String absolutePath = file.getAbsolutePath();
		File fileDir = new File(absolutePath + "/" + Constant.PATH_UPLOAD);
		if(!fileDir.mkdir()) {
			fileDir.mkdirs();
		}
	 
		String imageName = System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(absolutePath +"/"+ Constant.PATH_UPLOAD + "/"+  imageName));
		stream.write(multipartFile.getBytes());
		stream.close();
 
		return imageName;
	}
 
}
