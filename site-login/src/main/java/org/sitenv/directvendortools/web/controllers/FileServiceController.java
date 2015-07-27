package org.sitenv.directvendortools.web.controllers;

import java.io.File;
import java.io.IOException;

import org.sitenv.directvendortools.web.services.FileProcessService;
import org.sitenv.directvendortools.web.util.ApplicationConstants;
import org.sitenv.directvendortools.web.util.GenericErrorTO;
import org.sitenv.directvendortools.web.util.ResponseTO;
import org.sitenv.directvendortools.web.util.ResultSetTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileServiceController {
	
	@Autowired
	FileProcessService fileProcessService;
	
	@RequestMapping(value = ApplicationConstants.UPLOAD_CERT, method = RequestMethod.POST)
	public ResponseTO uploadFile(@RequestParam("anchoruploadfile") MultipartFile multipartFile,
							   	 @RequestParam("directEndPoint") String directEndPoint) {

		String[] folderNames = directEndPoint.split("@");
		File newFile = new File(ApplicationConstants.FILE_UPLOAD_DIRECTORY
				+ folderNames[1] + "/" + folderNames[0] + "/"
				+ multipartFile.getOriginalFilename());
		ResponseTO responseTO = null;
		try {

			newFile.getParentFile().mkdirs();
			newFile.createNewFile();
			fileProcessService.writeToFile(multipartFile.getInputStream(), newFile);
			responseTO = new ResponseTO(true);

		} catch (IOException IOException) {
			responseTO = new ResponseTO(new GenericErrorTO(IOException));
		}

		return responseTO;
	}

	@RequestMapping(value = ApplicationConstants.READ_ALL_CERTS, method = RequestMethod.GET)
	public ResponseTO uploadFile(@RequestParam(value = "directEndPoint", required = false) final String directEndPoint) {

		ResultSetTO resultSetTO;
		ResponseTO responseTO;
		try
		{
			resultSetTO = fileProcessService.readAllCerts(directEndPoint);
			responseTO = new ResponseTO(resultSetTO);
		
		}catch (Exception e)
		{
			responseTO = new ResponseTO(new GenericErrorTO(e));
		}
		
		return responseTO;
	}
	
	
	@RequestMapping(value = ApplicationConstants.DOWNLOAD_CERT, method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadCert(@RequestParam(value = "filePath", required = false) final String filePath)
	{
		ResponseEntity<InputStreamResource> certFile = null;
		try
		{
			FileSystemResource file = new FileSystemResource(filePath);
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		    headers.add("Pragma", "no-cache");
		    headers.add("Expires", "0");
		    headers.add("Content-Disposition","attachment; filename=\""+ file.getFilename());
		    
		    return ResponseEntity
		            .ok()
		            .headers(headers)
		            .contentLength(file.contentLength())
		            .contentType(MediaType.parseMediaType(ApplicationConstants.MIME_DER))
		            .body(new InputStreamResource(file.getInputStream()));
			
		}
		catch (Exception exception)
		{
		}
		return certFile;
	}
	
	@RequestMapping(value = ApplicationConstants.DELETE_CERT, method = RequestMethod.GET)
	public ResponseTO deletCert(@RequestParam(value = "filePath", required = false) final String filePath)
	{
		ResponseTO responseTO ;
	    final File file = new File(filePath);
	    responseTO = new ResponseTO(file.delete());
	    return responseTO;
	}

}
