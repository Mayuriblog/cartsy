package com.cartsy.ecom.api.v1.controller;

import java.util.List;


import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cartsy.ecom.api.v1.model.Deal;
import com.cartsy.ecom.api.v1.model.Order;
import com.cartsy.ecom.api.v1.model.RestResponse;
import com.cartsy.ecom.repository.*;
import com.cartsy.ecom.security.AuthenticatedUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMethod;


@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RestController
@RequestMapping(path="api/v1")
@PropertySource("classpath:application.properties")
public class DealController {
	final static Logger logger = LoggerFactory.getLogger(DealController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private DealRepository dRepo;
	@Autowired
	Environment env;
	

	@PostMapping("private/admin/deals")
	public ResponseEntity create(@RequestBody Deal deal) {
		try {
			logger.info("Creating new deal...");

			dRepo.save(deal);

			logger.info("Successfully created a new deal.");

			logger.debug("Successfully created a new deal. Deal details: " + mapper.writeValueAsString(deal));

			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, deal.getId().toString(), "", ""));
		}catch (DataIntegrityViolationException | ConstraintViolationException e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new RestResponse(400, "Username already taken.", "", "Username already taken."));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}
	
	@PostMapping("private/admin/deals/{id}/images/{view}")
	public ResponseEntity create(@RequestParam("file") MultipartFile file, @PathVariable Integer id, @PathVariable Integer view) {
		try {
			logger.info("Uploading deal images...");
			
			String basePath = env.getProperty("files.basepath");
			
			
			FileRepository.saveFile(basePath 
									+ env.getProperty("files.pathseparator") 
									+ "deals" 
									+ env.getProperty("files.pathseparator") 
									+ id 
									+ env.getProperty("files.pathseparator") 
									+ "images" 
									+ env.getProperty("files.pathseparator"), 
								file, view);
			
			

			logger.info("Successfully uploaded a new deal image.");


			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}
	
	@GetMapping("public/deals")
	public ResponseEntity read() {
		try {
			

			logger.info("Reading deals...");

			List<Deal> a = dRepo.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(a);

		} catch (BadCredentialsException e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new RestResponse(401, "Failure!", "", e.getLocalizedMessage()));

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	@GetMapping("public/deals/{id}")
	public ResponseEntity readById(@PathVariable Integer id) {
		logger.info("Reading order by Id...");

		try {
			logger.debug("Reading order by Id. OrderId :" + id);
			Deal d = dRepo.findById(id).get();
			return ResponseEntity.status(HttpStatus.OK).body(d);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	@GetMapping("public/deals/{id}/images/{view}")
	public ResponseEntity readImagesById(@PathVariable Integer id, @PathVariable Integer view) {
		logger.info("Reading deal images by Id...");

		try {
		   
			
			Resource file = FileRepository.readProductImage(env.getProperty("files.basepath") 
										+ env.getProperty("files.pathseparator") 
										+ "deals"
										+ env.getProperty("files.pathseparator") 
										+ id 
										+ env.getProperty("files.pathseparator") 
										+ "images" 
										+ env.getProperty("files.pathseparator") 
										+ view );
			
			return ResponseEntity.status(HttpStatus.OK).contentType(file.getFile().getName().contains("png")?MediaType.IMAGE_PNG:MediaType.IMAGE_JPEG).body(file);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}

}
