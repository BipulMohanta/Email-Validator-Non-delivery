package com.mav.email.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadFileController {
	
	
	@PostMapping("uploadSingleFile")
	public void uploadSingleFile(@RequestParam("file") MultipartFile file) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("uploadMultiFile")
	public void uploadMultiFile(@RequestParam("file") MultipartFile[] files) {
		try {
			if (files.length > 0) {
				for (MultipartFile file : files) {

					System.out.println("file is present");
					System.out.println("file size:" + file.getBytes().length);

				}
			} else {
				System.out.println("file is empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
