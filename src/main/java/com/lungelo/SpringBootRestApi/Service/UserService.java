/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.lungelo.SpringBootRestApi.Service;

import com.lungelo.SpringBootRestApi.Model.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author dlami
 */

@Service
public interface UserService {

User findById(long id);
	
	User findByName(String name);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(long id);

	List<User> findAllUsers();
	
	void deleteAllUsers();
	
	boolean isUserExist(User user);

    
}
