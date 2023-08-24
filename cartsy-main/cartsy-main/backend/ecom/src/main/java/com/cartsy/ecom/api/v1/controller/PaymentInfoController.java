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
import org.springframework.web.bind.annotation.RestController;

import com.cartsy.ecom.api.v1.model.Address;
import com.cartsy.ecom.api.v1.model.PaymentInfo;
import com.cartsy.ecom.api.v1.model.Product;
import com.cartsy.ecom.api.v1.model.RestResponse;
import com.cartsy.ecom.repository.*;
import com.cartsy.ecom.security.AuthenticatedUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMethod;




@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RestController
@RequestMapping(path="api/v1")
public class PaymentInfoController {
	final static Logger logger = LoggerFactory.getLogger(PaymentInfoController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private PaymentInfoRepository pRepo;
	

	@PostMapping("private/paymentinfo")
	public ResponseEntity create(@RequestBody PaymentInfo paymentinfo) {
		try {
			logger.info("Creating new paymentinfo...");

			pRepo.save(paymentinfo);

			logger.info("Successfully created a new paymentinfo.");

			logger.debug("Successfully created a new paymentinfo.  Details: " + mapper.writeValueAsString(paymentinfo));

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
	
	@GetMapping("private/paymentinfo/{id}")
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

			logger.info("Reading paymentinfos by Id...");

			logger.debug("Reading paymentinfos by Id. BuyerId :" + id);
			List<PaymentInfo> a = pRepo.findByEcomUserId(id);
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
	
	@DeleteMapping("private/paymentinfo/{id}")
	public ResponseEntity delete(@PathVariable Integer id) {
		logger.info("Deleting payment info...");

		try {
			logger.info("Deleting paymentInfo. paymentInfoId :" + id);
			pRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occured", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

}
