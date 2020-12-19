package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
				
		Department dpt = new Department(1, "Books");
		System.out.println(dpt);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Seller slr = new Seller(21, "Bob", "bob@gamil.com", new Date(), 3000.0, dpt);
		System.out.println(slr);
	}

}
