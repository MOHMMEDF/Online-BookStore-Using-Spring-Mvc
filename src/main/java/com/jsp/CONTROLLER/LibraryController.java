package com.jsp.CONTROLLER;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.DAO.BookDao;
import com.jsp.DAO.OrderDao;
import com.jsp.DAO.UserDao;
import com.jsp.DTO.Book;
import com.jsp.DTO.Order;
import com.jsp.DTO.User;

@Controller // this annotation tells that this is a controller class all this here acts an
			// an API
public class LibraryController {

	@Autowired // this annotation tells that inject here its methods its objects
	BookDao bookdao;

	@Autowired
	OrderDao orderdao;

	@Autowired
	UserDao userdao;

	// to get register.jsp file
	@GetMapping("/register")
	public String showRegisterPage() {
		return "register"; // here no need to add .jsp because already we mentioned in VIEWRESOLVER else
						   // got EXCEPTION
	}

	// to save user data coming from register.jsp page
	@PostMapping("/create")
	public String registerUser(@ModelAttribute User user) { // @ModelAttribute means it will assign all the values to
															// variable of User class coming
		// from register.jsp thats why in register.jsp page name should be same as User
		// class var name
		userdao.saveUser(user);

		return "redirect:/login"; // here it will redirect to login page sends a new request
								  // SYNTAX:redirect[COLON][SLASH][FILENAME]
	}

	// to get login.jsp file
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	// to verify login credentials
	@PostMapping("/loginhere")
	public String loginUser(@RequestParam String email, @RequestParam String password,
							HttpSession session, HttpServletRequest request) {

		User user = userdao.findUserByEmailAndPassword(email, password);

		if (user != null) {
			session.setAttribute("user", user);
			return "home";
		} else {
			request.setAttribute("error", "INVALID EMAIL OR PASSWORD !.....");
			return "login";
		}
	}

	// to add book to the cart
	@PostMapping("/cart/addToCart")
	public String addToCart(@RequestParam int bookid, HttpSession session) {

		User loggeduser = (User) session.getAttribute("user"); // this user obj we are setted to session at line 51 means
															   // from session we r checking user is there

		if (loggeduser != null) {
			// ✅ FIX: Only create new cart if it's null (previously always overriding cart)
			if (loggeduser.getCart() == null) {
				loggeduser.setCart(new ArrayList<Book>()); // creating empty cart list only once
			}

			Book book = bookdao.findBookById(bookid); // fetch book by id
			loggeduser.getCart().add(book); // we r adding book now

			userdao.updateUser(loggeduser); // now we r updating DB

			session.setAttribute("cartMessage", "Books added to cart successfully !...");
		}

		return "home";
	}

	// to search for a book
	@GetMapping("/search")
	public String searchBook(@RequestParam String title, Model model) {

		List<Book> books;
		title = title.trim(); // trim to avoid extra spaces

		if (title.isEmpty()) {
			model.addAttribute("message", "please enter a book title to search");
		} else {
			books = bookdao.findBookByTitle(title);
			model.addAttribute("books", books);
		}

		return "home";
	}

	// to get cart.jsp page
	@GetMapping("/cart")
	public String getCart(HttpSession session, Model model) {

		User loggedUser = (User) session.getAttribute("user");

		//  Pass cart books to JSP for display
		if (loggedUser != null && loggedUser.getCart() != null) {
			model.addAttribute("cartBooks", loggedUser.getCart());
		} else {
			model.addAttribute("message", "Your cart is empty");
		}

		return "cart";
	}

	// to get home.jsp
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}

	// to place order
	@PostMapping("/placeOrder")
	public String placeOrder(HttpSession session) {

		User loggedUser = (User) session.getAttribute("user");

		//  check if cart is empty
		if (loggedUser == null || loggedUser.getCart() == null || loggedUser.getCart().isEmpty()) {
			session.setAttribute("orderError", "Your cart is empty");
			return "redirect:/cart";
		}

		// creating a new order
		Order order = new Order();
		order.setUser(loggedUser); // setting all values to it

		// creating a new List to avoid confusion
		List<Book> orderedBooks = new ArrayList<Book>();
		double total = 0;

		for (Book book : loggedUser.getCart()) {
			total = total + book.getPrice();
			orderedBooks.add(book); // adding all books from the cart into new List
		}

		// setting the new list to the order.
		order.setBooks(orderedBooks);
		order.setTotalamount(total);
		order.setStatus("Placed"); //  make "Placed" consistent

		// storing the order object into DB (order is placed)
		orderdao.placeOrder(order);

		// clearing the books from the cart(clearing the book list)
		loggedUser.getCart().clear();

		// and updating the user object.
		userdao.updateUser(loggedUser);

		// Set session attribute for success message
		session.setAttribute("orderPlacedMessage", "ORDER PLACED SUCCESSFULLY..!!");

		return "redirect:/home";
	}

	// to get viewOrder.jsp
	// we are getting the order list for the logged user and adding it to Model.
	@GetMapping("/view")
	public String getAllOrders(HttpSession session, Model model) {

		User loggedUser = (User) session.getAttribute("user");
		List<Order> orders = orderdao.getOrderListByUserId(loggedUser.getUserid());

		model.addAttribute("orders", orders);
		return "viewOrder";
	}

	@GetMapping("/vieworder")
	public String getViewOrder() {
		return "viewOrder";
	}

	// for cancelOrder
	@PostMapping("/cancelOrder")
	public String cancelOrder(@RequestParam int orderid, HttpSession session) {

		orderdao.cancelOrder(orderid);

		//  success message in session
		session.setAttribute("cancelMessage", "Order cancelled successfully!");

		return "redirect:/view";
	}

	// for Logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();
		return "login";
	}
}
