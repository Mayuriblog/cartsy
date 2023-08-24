package com.cartsy.ecom.api.v1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.cartsy.ecom.repository.BuyerRepository;
import com.cartsy.ecom.security.AuthenticatedUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RestController
@RequestMapping(path = "api/v1")
public class BuyerController {

	final static Logger logger = LoggerFactory.getLogger(BuyerController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private BuyerRepository repo;

	@PostMapping("public/buyers")
	public ResponseEntity create(@RequestBody Buyer buyer) {
		try {
			logger.info("Creating new buyer...");

			repo.save(buyer);

			logger.info("Successfully created a new buyer.");

			logger.debug("Successfully created a new buyer. Buyer details: " + mapper.writeValueAsString(buyer));

			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

	@GetMapping("private/buyers/{id}")
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

			logger.info("Reading buyer by Id...");

			logger.debug("Reading buyer by Id. BuyerId :" + id);
			Buyer p = repo.findByEcomUserId(id).get(0);
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

	@PutMapping("private/buyers/{id}")
	public ResponseEntity update(@PathVariable Integer id, @RequestBody Buyer buyer) {
		try {
//			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//			if (principal instanceof AuthenticatedUserDetails) {
//
//				Integer authId = ((AuthenticatedUserDetails) principal).getId();
//				if (authId != id) {
//					throw new BadCredentialsException("Different user than authenticated.");
//				}
//
//			} else {
//				throw new BadCredentialsException("Different user than authenticated.");
//			}

			logger.info("Saving buyer...");

			logger.info("Saving buyer. BuyerId :" + id);
			repo.save(buyer);
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

	@DeleteMapping("private/buyers/{id}")
	public ResponseEntity delete(@PathVariable Integer id, Buyer buyer) {
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

			logger.info("Deleting buyer...");

			logger.info("Deleting buyer. BuyerId :" + id);
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
