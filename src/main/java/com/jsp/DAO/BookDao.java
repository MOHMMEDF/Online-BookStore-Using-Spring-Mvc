package com.jsp.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.DTO.Book;

@Repository
public class BookDao {

	@Autowired
	EntityManager manager;

	@Autowired
	EntityTransaction transaction;

	// now find book by id ,we r finding book so return type is Book

	public Book findBookById(int id) {
		return manager.find(Book.class, id);// here we r returning also same line it will return book based on id
	}

	// now find book by title,here finding book by title so return type is
	// listofbook
	public List<Book> findBookByTitle(String title) {
		return manager.createQuery("select b from Book b where b.title LIKE :title", Book.class)// here Book.class is
																								// optional
				.setParameter("title", "%" + title + "%").getResultList();// here we are executing this query//
																			// Executing the query and returning the
																			// result

		// end semicolon at last at line 33 else get error
	}

}
