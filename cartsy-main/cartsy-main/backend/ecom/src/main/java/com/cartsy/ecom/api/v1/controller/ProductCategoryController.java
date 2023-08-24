package com.cartsy.ecom.api.v1.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartsy.ecom.api.v1.model.ProductCategory;
import com.cartsy.ecom.api.v1.model.RestResponse;
import com.cartsy.ecom.repository.ProductCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RestController
@RequestMapping(path = "api/v1/private/categories")
public class ProductCategoryController {

	final static Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

	@Autowired
	ProductCategoryRepository repo;

	final static ObjectMapper mapper = new ObjectMapper();

	@PostMapping()
	public ResponseEntity create(@RequestBody ProductCategory productCategory) {

		try {
			logger.info("Creating new product category...");

			repo.save(productCategory);

			logger.info("Successfully created a new product category.");

			logger.debug("Successfully created a new product category. Product category details: "
					+ mapper.writeValueAsString(productCategory));

			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

	@GetMapping()
	public ResponseEntity read() {
		try {
			logger.info("Reading all products categories...");
			List<ProductCategory> productCategories = repo.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(productCategories);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}

	@GetMapping("/{id}")
	public ResponseEntity readById(@PathVariable Integer id) {
		logger.info("Reading product category by Id...");

		try {
			logger.debug("Reading product category by Id. CategoryId :" + id);
			ProductCategory p = repo.findById(id).get();
			return ResponseEntity.status(HttpStatus.OK).body(p);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}

	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable Integer id, @RequestBody ProductCategory productCategory) {
		logger.info("Saving product category...");

		try {
			logger.info("Saving product category. CategoryId :" + id);
			repo.save(productCategory);
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Integer id, ProductCategory productCategory) {
		logger.info("Deleting product category...");

		try {
			logger.info("Deleting product. CategoryId :" + id);
			repo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occured", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

}
