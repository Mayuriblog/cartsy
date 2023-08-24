package com.cartsy.ecom.api.v1.controller;

import java.util.Date;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartsy.ecom.api.v1.model.Buyer;
import com.cartsy.ecom.api.v1.model.Cart;
import com.cartsy.ecom.api.v1.model.EcomUser;
import com.cartsy.ecom.api.v1.model.NewUser;
import com.cartsy.ecom.api.v1.model.RestResponse;
import com.cartsy.ecom.api.v1.model.Seller;
import com.cartsy.ecom.repository.*;
import com.cartsy.ecom.security.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMethod;


@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RestController
@RequestMapping(path="api/v1")
public class RegistrationController {
	final static Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private BuyerRepository bRepo;
	@Autowired
	private SellerRepository sRepo;
	@Autowired
	private UserRepository uRepo;
	@Autowired
	private CartRepository cRepo;

	@PostMapping("public/register")
	public ResponseEntity create(@RequestBody NewUser user) {
		try {
			logger.info("Creating new user...");

			EcomUser ecomUser = new EcomUser();
			ecomUser.setEcom_username(user.getUsername());
			ecomUser.setEcom_password(user.getPassword());
			
			if(user.getType().equals(Roles.ROLE_NAMES.ROLE_CARTSY_SELLER.name().toString())) {
				ecomUser.setEcom_role(Roles.ROLE_NAMES.ROLE_CARTSY_SELLER.name().toString());
				uRepo.save(ecomUser);
				if(ecomUser.getId()!=null) {
					Seller s = new Seller();
					
					s.setEcomUserId(ecomUser.getId());
					s.setSellerDoj(new Date(System.currentTimeMillis()));
					
					sRepo.save(s);
				}
				
			}else {
				ecomUser.setEcom_role(Roles.ROLE_NAMES.ROLE_CARTSY_BUYER.name().toString());
				uRepo.save(ecomUser);
				
				if(ecomUser.getId()!=null) {
					Buyer b = new Buyer();
					
					b.setEcomUserId(ecomUser.getId());
					b.setBuyerDoj(new Date(System.currentTimeMillis()));
					
					bRepo.save(b);
					
					
					Cart cart = new Cart();
					cart.setId(ecomUser.getId());
					cRepo.save(cart);
				}
				
				
				
			}

			logger.info("Successfully created a new user.");

			logger.debug("Successfully created a new user. User details: " + mapper.writeValueAsString(user));

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

}
