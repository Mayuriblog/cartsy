package com.cartsy.ecom.api.v1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cartsy.ecom.api.v1.model.*;
import com.cartsy.ecom.repository.DealRepository;
import com.cartsy.ecom.repository.FileRepository;
import com.cartsy.ecom.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

import org.springframework.web.bind.annotation.RequestMethod;


@CrossOrigin(origins = "http://localhost:3000", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

@RestController
@RequestMapping(path = "api/v1")
@PropertySource("classpath:application.properties")
public class ProductController {

	final static Logger logger = LoggerFactory.getLogger(ProductController.class);
	final static ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private ProductRepository pRepo;
	@Autowired
	private DealRepository dRepo;
	@Autowired
	Environment env;

	@PostMapping("private/products")
	public ResponseEntity create(@RequestBody Product product) {
		try {
			logger.info("Creating new product...");
			
			Date now = new Date(System.currentTimeMillis());
			
			product.setFirstAvailable(now);
			product.setCreatedDate(now);

			pRepo.save(product);

			logger.info("Successfully created a new product.");

			logger.debug("Successfully created a new product. Product details: " + mapper.writeValueAsString(product));

			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, product.getId().toString(), "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

	@PostMapping("private/products/{id}/images/{view}")
	public ResponseEntity create(@RequestParam("file") MultipartFile file, @PathVariable Integer id, @PathVariable Integer view) {
		try {
			logger.info("Uploading product images...");
			
			String basePath = env.getProperty("files.basepath");
			
			
			FileRepository.saveFile(basePath 
									+ env.getProperty("files.pathseparator") 
									+ id 
									+ env.getProperty("files.pathseparator") 
									+ "images" 
									+ env.getProperty("files.pathseparator"), 
								file, view);
			
			

			logger.info("Successfully uploaded a new product image.");


			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}
	
	@GetMapping("public/products")
	public ResponseEntity read() {
		try {
			logger.info("Reading all products...");
			List<Product> products = pRepo.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}

	@GetMapping("public/products/page")
	public ResponseEntity readByPage(@RequestParam Integer pageNo, @RequestParam(required = false) Integer pageSize) {
		logger.info("Reading paginated products...");

		int PAGE_SIZE = pageSize != null ? pageSize : 100;
		try {
			logger.debug("Reading paginated products. Page size :" + pageSize);

			Pageable page = PageRequest.of(pageNo, PAGE_SIZE);
			List<Product> products = pRepo.findAll(page).getContent();
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}

	@GetMapping("public/products/search")
	public ResponseEntity readBySearch(@RequestParam String searchText) {
		logger.info("Searching products...");

		try {

			logger.debug("Searching products. Search text :" + searchText);

			List<Product> products = pRepo.search(searchText);
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}
	
	@GetMapping("public/products/seller/{sellerId}")
	public ResponseEntity readBySeller(@PathVariable Integer sellerId) {
		logger.info("Reading products by seller...");

		try {

			logger.debug("Reading products by seller. Seller id :" + sellerId);

			List<Product> products = pRepo.filterBySeller(sellerId);
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}

	@GetMapping("public/products/category")
	public ResponseEntity readByCategory(@RequestParam Integer category) {
		logger.info("Reading products by category...");

		try {
			logger.debug("Reading products by category. CategoryId :" + category);

			List<Product> products = pRepo.filterByCategory(category);
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}
	
	@GetMapping("public/products/deal/{id}")
	public ResponseEntity readByDeal(@PathVariable Integer id) {
		logger.info("Reading products by deal...");

		try {
			logger.debug("Reading products by deal. DealId :" + id);

			int[] ids = Arrays.stream(dRepo.findById(id).get().getDealProducts().split(",")).mapToInt(Integer::parseInt).toArray();  
			
			List<Product> products = pRepo.findByIdIn(ids);
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}
	
	@GetMapping("public/products/latest")
	public ResponseEntity readByLatest() {
		logger.info("Reading products by latest...");

		try {

			List<Product> products = pRepo.filterByLatest();
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}
	
	@GetMapping("public/products/bestseller")
	public ResponseEntity readByBestseller() {
		logger.info("Reading products by BestSeller...");

		try {

			List<Product> products = pRepo.filterByBestseller();
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}

	@GetMapping("public/products/{id}/recommended")
	public ResponseEntity readByRecommendation(@PathVariable Integer id) {

		logger.info("Reading products recommendation...");

		try {
			logger.debug("Reading products by recommendation. ProductId :" + id);

			// read the product's category
			Optional<Product> p = pRepo.findById(id);
			List<Product> products = new ArrayList<Product>();
			if (p.isPresent()) {
				products = pRepo.recommendation(p.get().getCategoryId());

			}
			return ResponseEntity.status(HttpStatus.OK).body(products);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}
	}

	@GetMapping("public/products/{id}")
	public ResponseEntity readById(@PathVariable Integer id) {
		logger.info("Reading product by Id...");

		try {
			logger.debug("Reading product by Id. ProductId :" + id);
			Product p = pRepo.findById(id).get();
			return ResponseEntity.status(HttpStatus.OK).body(p);

		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));

		} finally {

		}
	}
	
	@GetMapping("public/products/{id}/images/{view}")
	public ResponseEntity readImagesById(@PathVariable Integer id, @PathVariable Integer view) {
		logger.info("Reading product images by Id...");

		try {
		   
			
			Resource file = FileRepository.readProductImage(env.getProperty("files.basepath") 
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

	@PutMapping("private/products/{id}")
	public ResponseEntity update(@PathVariable Integer id, @RequestBody Product product) {
		logger.info("Saving product...");

		try {
			logger.info("Saving product. ProductId :" + id);
			pRepo.save(product);
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(200, "Success!", "", ""));
		} catch (Exception e) {
			logger.error("Error occurred", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new RestResponse(500, "Failure!", "", e.getLocalizedMessage()));
		} finally {

		}

	}

	@DeleteMapping("private/products/{id}")
	public ResponseEntity delete(@PathVariable Integer id, Product product) {
		logger.info("Deleting product...");

		try {
			logger.info("Deleting product. ProductId :" + id);
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
