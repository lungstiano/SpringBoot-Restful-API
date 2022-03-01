/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.lungelo.SpringBootRestApi.Controller;

import com.lungelo.SpringBootRestApi.Model.User;
import com.lungelo.SpringBootRestApi.Service.UserService;
import com.lungelo.SpringBootRestApi.util.CustomErrorType;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author dlami
 */
@RestController
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping("/api")
public class RestUserController{

public static final Logger logger = LoggerFactory.getLogger(RestUserController.class);

	
	UserService userService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Users---------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
//                List<User> users = new ArrayList<User>();
//		users.add(new User(1,"Lungelo","Dlami", 50000));
//		users.add(new User(2,"Jacob","Zuma", 65000));
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single User------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);

		if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getFirstName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			user.getFirstName() + " already exist."),HttpStatus.CONFLICT);
		}
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}


	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/user/collection", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody List<User> listUser, UriComponentsBuilder ucBuilder) {

		for (User user :listUser) {
			logger.info("Creating User : {}", user);

			if (userService.isUserExist(user)) {
				logger.error("Unable to create. A User with name {} already exist", user.getFirstName());
				return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
						user.getFirstName() + " already exist."),HttpStatus.CONFLICT);
			}
			userService.saveUser(user);
		}

		//HttpHeaders headers = new HttpHeaders();
		//headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());

		return new ResponseEntity<List<User>>(listUser, HttpStatus.CREATED);
	}

	// ------------------- Update a User ------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setContactNumber(user.getContactNumber());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

    
}
