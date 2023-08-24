package com.cartsy.ecom.api.v1.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cartsy.ecom.api.v1.model.Buyer;
import com.cartsy.ecom.api.v1.model.Order;
import com.cartsy.ecom.api.v1.model.Product;
import com.cartsy.ecom.api.v1.model.RestResponse;
import com.cartsy.ecom.api.v1.model.Seller;
import com.cartsy.ecom.repository.BuyerRepository;
import com.cartsy.ecom.repository.OrderRepository;
import com.cartsy.ecom.repository.ProductRepository;
import com.cartsy.ecom.repository.SellerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RestController
@RequestMapping(path = "api/v1/private/admin")
public class AdminController {

	final static Logger logger = LoggerFactory.getLogger(AdminController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private SellerRepository sellerRepo;
	@Autowired
	private BuyerRepository buyerRepo;
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("orders/page")
	public ResponseEntity readOrdersByPage(@RequestParam Integer pageNo, @RequestParam(required = false) Integer pageSize) {
		logger.info("Reading paginated orders...");

		int PAGE_SIZE = pageSize != null ? pageSize : 100;
		try {
			logger.debug("Reading paginated orders. Page size :" + pageSize);

			Pageable page = PageRequest.of(pageNo, PAGE_SIZE);
			List<Order> orders = orderRepo.findAll(page).getContent();
			return ResponseEntity.status(HttpStatus.OK).body(orders);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	@GetMapping("orders/count")
	public ResponseEntity countOrders(@RequestParam(required = false) Long timestamp) {
		logger.info("Reading count of orders...");

		
		try {
			logger.debug("Reading count of orders.");

			Long count;
			if(timestamp!=null) {
				Date creationDate = new Date(timestamp);
				count = (long) orderRepo.filterByCreationDate(creationDate).size();
			}else {
				count = orderRepo.count();
			}
			
			
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200,count.toString(),"",""));

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	
	@GetMapping("buyers/page")
	public ResponseEntity readBuyersByPage(@RequestParam Integer pageNo, @RequestParam(required = false) Integer pageSize) {
		logger.info("Reading paginated buyers...");

		int PAGE_SIZE = pageSize != null ? pageSize : 100;
		try {
			logger.debug("Reading paginated buyers. Page size :" + pageSize);

			Pageable page = PageRequest.of(pageNo, PAGE_SIZE);
			List<Buyer> buyers = buyerRepo.findAll(page).getContent();
			return ResponseEntity.status(HttpStatus.OK).body(buyers);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	@GetMapping("buyers/count")
	public ResponseEntity countBuyers(@RequestParam(required = false) Long timestamp) {
		logger.info("Reading count of buyers...");

		
		try {
			logger.debug("Reading count of buyers.");

			Long count;
			if(timestamp!=null) {
				Date creationDate = new Date(timestamp);
				count = (long) buyerRepo.filterByCreationDate(creationDate).size();
			}else {
				count = buyerRepo.count();
			}
			
			
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200,count.toString(),"",""));

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	@GetMapping("buyers/search")
	public ResponseEntity readBySearch(@RequestParam String searchText) {
		logger.info("Searching buyers...");

		try {

			logger.debug("Searching buyers. Search text :" + searchText);

			List<Buyer> buyers = buyerRepo.search(searchText);
			return ResponseEntity.status(HttpStatus.OK).body(buyers);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}
	
	@GetMapping("sellers/page")
	public ResponseEntity readSellersByPage(@RequestParam Integer pageNo, @RequestParam(required = false) Integer pageSize) {
		logger.info("Reading paginated sellers...");

		int PAGE_SIZE = pageSize != null ? pageSize : 100;
		try {
			logger.debug("Reading paginated sellers. Page size :" + pageSize);

			Pageable page = PageRequest.of(pageNo, PAGE_SIZE);
			List<Seller> sellers = sellerRepo.findAll(page).getContent();
			return ResponseEntity.status(HttpStatus.OK).body(sellers);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	@GetMapping("sellers/count")
	public ResponseEntity countSellers(@RequestParam(required = false) Long timestamp) {
		logger.info("Reading count of sellers...");

		
		try {
			logger.debug("Reading count of sellers.");

			Long count;
			if(timestamp!=null) {
				Date creationDate = new Date(timestamp);
				count = (long) sellerRepo.filterByCreationDate(creationDate).size();
			}else {
				count = sellerRepo.count();
			}
			
			
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200,count.toString(),"",""));

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}

	@GetMapping("products/page")
	public ResponseEntity readProductsByPage(@RequestParam Integer pageNo, @RequestParam(required = false) Integer pageSize) {
		logger.info("Reading paginated products...");

		int PAGE_SIZE = pageSize != null ? pageSize : 100;
		try {
			logger.debug("Reading paginated products. Page size :" + pageSize);

			Pageable page = PageRequest.of(pageNo, PAGE_SIZE);
			List<Product> products = productRepo.findAll(page).getContent();
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	@GetMapping("products/count")
	public ResponseEntity countProducts(@RequestParam(required = false) Long timestamp) {
		logger.info("Reading count of products...");

		
		try {
			logger.debug("Reading count of products.");

			Long count;
			if(timestamp!=null) {
				Date creationDate = new Date(timestamp);
				count = (long) productRepo.filterByCreationDate(creationDate).size();
			}else {
				count = productRepo.count();
			}
			
			
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200,count.toString(),"",""));

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
}
