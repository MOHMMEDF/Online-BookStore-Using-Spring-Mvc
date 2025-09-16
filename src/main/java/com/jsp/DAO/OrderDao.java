package com.jsp.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.DTO.Order;

@Repository
public class OrderDao {

	@Autowired
	EntityManager manager;
	
	@Autowired
	EntityTransaction transaction;
	
	//now to place the order this details comes from client sider user side we have to perform database operation
	
	public void placeOrder(Order order) {
		transaction.begin();
		//before to add the order we r mentioning that this order is placed or not status see,
		order.setStatus("PLACED");
		manager.persist(order);//here persisting saving the order object
		transaction.commit();
	}
	//to cancel the order
	
	public void cancelOrder(int orderid) {
		Order order=manager.find(Order.class, orderid);
		//now we are checkng if order is present and its status is equals to PLACED if both true then cancel updating query
		if(order!=null && order.getStatus().equals("PLACED")) {
			order.setStatus("CANCELLED");//here we r setting STATUS CANCELLED IF IT IS PLACED and user want to cancelled
			manager.merge(order);
		}
	}
	
	// to fetch orders for a particular user
	public List<Order> getOrderListByUserId(int userid) {
		
		return manager.createQuery("select o from Order o where o.user.userid=?1 ", Order.class)
		.setParameter(1, userid)
		.getResultList();
	}
}
