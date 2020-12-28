package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
				
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== Test 01: Seller findById ===");
		Seller seller = sellerDao.findById(3);		
		System.out.println(seller);		
		
		System.out.println("\n=== Test 02: Seller findByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller slr : list) {
			System.out.println(slr);
		}
		
		System.out.println("\n=== Test 03: Seller findAll ===");
		list = sellerDao.findAll();
		for (Seller slr : list) {
			System.out.println(slr);
		}
		
		System.out.println("\n=== Test 04: Seller insert ===");
		Seller slr = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(slr);
		System.out.println("Inserted! New Id = " + slr.getId());
		
	}

}