/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.lungelo.SpringBootRestApi.Service;

import com.lungelo.SpringBootRestApi.Model.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

/**
 *
 * @author dlami
 */
@Service("userService")
public class UserServiceImpl {

private static final AtomicLong counter = new AtomicLong();
	
	private static List<User> users;
	
	static{
		users= populateDummyUsers();
	}

	public List<User> findAllUsers() {

   
		return users;
	}
	
	public User findById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public User findByName(String firstName) {
		for(User user : users){
			if(user.getFirstName().equalsIgnoreCase(firstName)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(User user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {

		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
		    User user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	//I should check for email's user, not for name.
	public boolean isUserExist(User user) {
		return findByName(user.getFirstName())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

	private static List<User> populateDummyUsers(){
		List<User> users = new ArrayList<User>();
		users.add(new User(1,"Jon","Dlam", 50000));
		users.add(new User(2,"Robb","Zuma", 65000));
//		users.add(new User(counter.incrementAndGet(),"Sansa","James", 54000));
//		users.add(new User(counter.incrementAndGet(),"Tyrion","Zulu", 402000));
//		users.add(new User(counter.incrementAndGet(),"Deanerys","Idol", 10000));
//		users.add(new User(counter.incrementAndGet(),"Eddard","dwaba", 510000));
//		users.add(new User(counter.incrementAndGet(),"Tormund","Zues", 30000));
//		users.add(new User(counter.incrementAndGet(),"Mance","Jay", 45000));
		return users;
	}

    
}
