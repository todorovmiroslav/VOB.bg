package bg.VOB.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import bg.VOB.WebInitializer;

@Controller
@MultipartConfig
public class VideoController {
	
	@RequestMapping(value = "/uploadVideo", method = RequestMethod.GET)
	public String uploadVideo() {
		return "uploadVideo";
	}
	
	@RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
	public String saveVideo(@RequestParam("videoFile") MultipartFile file) {
		File f = new File(WebInitializer.LOCATION + File.separator + file.getOriginalFilename()); //username+photoname
		try {
			file.transferTo(f);
		} catch (IllegalStateException e) {
			System.out.println("Invalid file saving.");
		} catch (IOException e) {
			System.out.println("Invalid file saving.");
		}
		return "uploadVideo";
	}
}
