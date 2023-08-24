package com.cartsy.ecom.api.v1.controller;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cartsy.ecom.api.v1.model.Review;
import com.cartsy.ecom.api.v1.model.RestResponse;
import com.cartsy.ecom.repository.*;
import com.cartsy.ecom.security.AuthenticatedUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(path="api/v1")
public class ReviewController {
	final static Logger logger = LoggerFactory.getLogger(ReviewController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private ReviewRepository rRepo;
	

	@PostMapping("private/reviews")
	public ResponseEntity create(@RequestBody Review review) {
		try {
			logger.info("Creating new review...");
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof AuthenticatedUserDetails) {

				Integer authId = ((AuthenticatedUserDetails) principal).getId();
				if (authId != review.getEcomUser()) {
					throw new BadCredentialsException("Different user than authenticated.");
				}
			} else {
				throw new BadCredentialsException("Different user than authenticated.");
			}

			rRepo.save(review);

			logger.info("Successfully created a new review.");

			logger.debug("Successfully created a new review. Review details: " + mapper.writeValueAsString(review));

			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
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
	
	@GetMapping("private/reviews/{productId}")
	public ResponseEntity readByProductId(@PathVariable Integer productId) {
		try {
			

			logger.info("Reading reviews by Id...");

			logger.debug("Reading reviews by Id. Product Id :" + productId);
			
			List<Review> r = rRepo.findByProductId(productId);
			return ResponseEntity.status(HttpStatus.OK).body(r);

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
	
	@DeleteMapping("private/review/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
		logger.info("Deleting review...");

		try {
			logger.info("Deleting review. AddressId :" + id);
			rRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occured", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

}
