package com.cartsy.ecom.security;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartsy.ecom.api.v1.model.EcomUser;
import com.cartsy.ecom.api.v1.model.RestResponse;
import com.cartsy.ecom.repository.UserRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController 
@RequestMapping(path = "api/v1/public")
public class AuthController {

	@Autowired
	UserRepository repo;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthRequest request) {
        try {
        	List<EcomUser> foundUsers = repo.findByUsername(request.getUsername());
    		
    		EcomUser foundUser;
    		if(foundUsers!=null && foundUsers.size()==1) {
    			
    			foundUser = foundUsers.get(0);
    			
    			if(foundUser.getEcom_password().equals(request.getPassword())) {
    				
    				
    				return ResponseEntity.ok()
    		                .body(new RestResponse(200, JwtTokenUtil.generateJwtToken(foundUser),null,null));
    				
    				
    			}else {
    				RestResponse response = new RestResponse(401,null,"Username/Password incorrect.",null);
    				return ResponseEntity.status(401).body(response);
    				
    			}
    			
    			
    		}
    		
    		RestResponse response = new RestResponse(401,null,"Username/Password incorrect.",null);
			return ResponseEntity.status(401).body(response);

    		
    		
        } catch (Exception ex) {
        	ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}

