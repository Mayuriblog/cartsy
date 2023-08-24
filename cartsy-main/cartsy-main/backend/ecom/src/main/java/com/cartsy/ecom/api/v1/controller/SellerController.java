package com.cartsy.ecom.api.v1.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import com.cartsy.ecom.api.v1.model.*;
import com.cartsy.ecom.repository.SellerRepository;
import com.cartsy.ecom.security.AuthenticatedUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "api/v1")
public class SellerController {

	final static Logger logger = LoggerFactory.getLogger(SellerController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private SellerRepository repo;

	@PostMapping("public/sellers")
	public ResponseEntity create(@RequestBody Seller seller) {
		try {
			logger.info("Creating new seller...");

			repo.save(seller);

			logger.info("Successfully created a new seller.");

			logger.debug("Successfully created a new seller. Seller details: " + mapper.writeValueAsString(seller));

			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

	@GetMapping("private/sellers/{id}")
	public ResponseEntity readById(@PathVariable Integer id) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof AuthenticatedUserDetails) {

				Integer authId = ((AuthenticatedUserDetails) principal).getId();
				if (authId != id) {
					throw new BadCredentialsException("Different user than authenticated.");
				}

			} else {
				throw new BadCredentialsException("Different user than authenticated.");
			}

			logger.info("Reading seller by Id...");

			logger.debug("Reading seller by Id. SellerId :" + id);
			Seller p = repo.findById(id).get();
			return ResponseEntity.status(HttpStatus.OK).body(p);

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

	@PutMapping("private/sellers/{id}")
	public ResponseEntity update(@PathVariable Integer id, @RequestBody Seller seller) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof AuthenticatedUserDetails) {

				Integer authId = ((AuthenticatedUserDetails) principal).getId();
				if (authId != id) {
					throw new BadCredentialsException("Different user than authenticated.");
				}

			} else {
				throw new BadCredentialsException("Different user than authenticated.");
			}

			logger.info("Saving seller...");

			logger.info("Saving seller. SellerId :" + id);
			repo.save(seller);
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
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

	@DeleteMapping("private/sellers/{id}")
	public ResponseEntity delete(@PathVariable Integer id, Seller seller) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof AuthenticatedUserDetails) {

				Integer authId = ((AuthenticatedUserDetails) principal).getId();
				if (authId != id) {
					throw new BadCredentialsException("Different user than authenticated.");
				}

			} else {
				throw new BadCredentialsException("Different user than authenticated.");
			}

			logger.info("Deleting seller...");

			logger.info("Deleting seller. SellerId :" + id);
			repo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (BadCredentialsException e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new RestResponse(401, "Failure!", "", e.getLocalizedMessage()));

		} catch (Exception e) {
			logger.error("Error occured", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}
}
