package com.jsp.DAO;
//UserDao just for database operation here we are doing
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.DTO.User;

@Repository//beacuse of this we can use all this method present in this
public class UserDao {

	//now step 1 we have to get dipendency injection automatically which is already present in LibraryConfig class
	
	@Autowired
	EntityManager manager;
	
	@Autowired
	EntityTransaction transaction;
	
	//to the save the user 
	public void saveUser(User user) {
		transaction.begin();
		manager.persist(user);
		transaction.commit();
	}
	
	//now search for the user[FOR LOGIN VERIFICATION]
	
	public User findUserByEmailAndPassword(String email,String password) {
		List<User>userlist= manager.createQuery("select u from User u where u.email=?1 and u.password=?2",User.class)//here User.class is optional
		//just clearify that is an User entity class but not required because we alreday mentioned tablename in Query1
		//now setting to aparmeter here dot(.) means inside manager i.e manager. inside
		.setParameter(1, email)
		.setParameter(2, password)
		.getResultList();
		
		return userlist.isEmpty()? null:userlist.get(0);//here we r using ternary operator
		/*
		 *  What it means:
			userlist.isEmpty() → Checks if the list is empty (has 0 elements)

			If true → return null

			If false → return the first element of the list: userlist.get(0)


		 */

		
		
	}
	//update User object
	public void updateUser(User user) {
		transaction.begin();
		manager.merge(user);//merge has 2 behavior if record present it will update else it will create another record
		transaction.commit();
	}
	//now go to BookDao class step follows
	
}
