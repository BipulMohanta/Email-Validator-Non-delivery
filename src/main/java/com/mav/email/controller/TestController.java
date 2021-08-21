package com.mav.email.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author bipul.mohanta
 *
 */
@RestController
@RequestMapping(path = "api/test")
public class TestController {

	@GetMapping("testFunciton")
	public void testFunciton() {
		System.out.println("Controller is working !!!");
	}

	@PostMapping("uploadSingleFileTest")
	public void uploadSingleFileTest(@RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				System.out.println("file is present");
				System.out.println("file size:" + file.getBytes().length);
			} else {
				System.out.println("file is empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping("uploadMultiFileTest")
	public void uploadMultiFileTest(@RequestParam("fileName") String fileName,
			@RequestParam("file") MultipartFile[] files) {
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
