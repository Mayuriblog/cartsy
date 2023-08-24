package com.cartsy.ecom.api.v1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cartsy.ecom.api.v1.model.*;
import com.cartsy.ecom.repository.CartRepository;
import com.cartsy.ecom.repository.OrderRepository;
import com.cartsy.ecom.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RestController
@RequestMapping(path = "api/v1/private/orders")
public class OrderController {

	final static Logger logger = LoggerFactory.getLogger(OrderController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private OrderRepository repo;
	@Autowired
	private ProductRepository pRepo;
	@Autowired
	private CartRepository cRepo;

	@PostMapping()
	public ResponseEntity create(@RequestBody Order order) {
		try {
			logger.info("Creating new order...");

			order.setDateOfOrder(new Date(System.currentTimeMillis()));
			
			Integer totalPrice = 0;
			String products = order.getProductIds();
			
			//products is stored as a JSON with productId vs quantity
			
			if(products!=null && !products.isEmpty()) {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Integer> prodMap = mapper.readValue(products, Map.class);
				
				for(String productId: prodMap.keySet() ) {
					totalPrice+= pRepo.findById(Integer.valueOf(productId)).get().getProductSalePrice();
				}
				
				order.setPrice(totalPrice);
				
				repo.save(order);
				
				for(String productId: prodMap.keySet() ) {
					pRepo.updateOrderCount(Integer.valueOf(productId));
				}
				
				Optional<Cart> cart = cRepo.findById(order.getEcomUserId());
				if(cart.isPresent()) {
					cart.get().setProducts("");
					cRepo.save(cart.get());
				}
				
				logger.info("Successfully created a new order.");

				logger.debug("Successfully created a new order. Order details: " + mapper.writeValueAsString(order));

				return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
				
				
			}else {
				logger.error("Error occurred, cart empty.");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new RestResponse(500, "Failure!", "", ""));
			}
			
			
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

	
	


	@GetMapping("/buyer")
	public ResponseEntity readByBuyer(@RequestParam Integer buyerId) {
		logger.info("Reading orders by buyer...");

		try {
			logger.debug("Reading orders by buyer. BuyerId :" + buyerId);

			List<Order> orders = repo.byBuyer(buyerId);
			return ResponseEntity.status(HttpStatus.OK).body(orders);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}
	
	

	@GetMapping("/{id}")
	public ResponseEntity readById(@PathVariable Integer id) {
		logger.info("Reading order by Id...");

		try {
			logger.debug("Reading order by Id. OrderId :" + id);
			Order p = repo.findById(id).get();
			return ResponseEntity.status(HttpStatus.OK).body(p);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}

	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable Integer id, @RequestBody Order order) {
		logger.info("Saving order...");

		try {
			logger.info("Saving order. OrderId :" + id);
			repo.save(order);
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

	
}
