package com.app.rest.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.config.JwtTokenUtil;
import com.app.exception.ApplicationException;
import com.app.model.Roles;
import com.app.model.Users;
import com.app.model.request.CreateUserRequest;
import com.app.model.request.JwtRequest;
import com.app.model.response.JwtResponse;
import com.app.response.APIStatus;
import com.app.service.JwtUserDetailsService;
import com.app.service.RoleService;
import com.app.service.UserService;
import com.app.utils.Constant;

@RestController
@CrossOrigin(origins = {Constant.CROSS_ORIGIN_LOCAL_8000, Constant.CROSS_ORIGIN_LOCAL_8001, Constant.CROSS_ORIGIN_LOCAL_8080, Constant.CROSS_ORIGIN_LOCAL_3000, Constant.CROSS_ORIGIN_LOCAL_3001 })
public class AuthRestController {
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    RoleService roleService;
    
    @Autowired
	private PasswordEncoder bcryptEncoder;
 
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> saveUser(@RequestBody CreateUserRequest createUserRequest) throws Exception {
    	Users users = userService.findByUsername(createUserRequest.getUsername());
    	if(users != null) {
    		throw new ApplicationException(APIStatus.ERR_USER_NAME_ALREADY_EXISTS);
    	}
 
    	Roles role = roleService.findById(createUserRequest.getRole());
		Set<Roles> setRoles = new HashSet<Roles>();
		setRoles.add(role);
		users = new Users();
		users.setRoles(setRoles);
		users.setEmail(createUserRequest.getEmail());
		users.setUsername(createUserRequest.getUsername());
		users.setName(createUserRequest.getName());
		users.setStatus(Constant.Status.ACTIVE.getValue());
		users.setPassword(bcryptEncoder.encode(createUserRequest.getPassword()));
		
        return ResponseEntity.ok(userService.save(users));
    	 
    }

    @RequestMapping(value = Constant.AUTH_API, method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

    	 
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        
        Users users = userService.findByUsername(userDetails.getUsername());
        
        final String token = jwtTokenUtil.generateToken(userDetails);
 
        return ResponseEntity.ok(new JwtResponse(
        		users.getId()
        		,token
        		,userDetails.getUsername()
        		,userDetails.getAuthorities().toArray()
        		));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
         
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) { // nếu user bị khoa
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
            //thông tin đăng nhập sai 
        }
    }
}
