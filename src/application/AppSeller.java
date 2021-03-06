package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class AppSeller {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
				
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
		
		System.out.println("\n=== Test 05: Seller update ===");
		seller = sellerDao.findById(1);
		seller.setName("Matha Waine");
		sellerDao.update(seller);
		System.out.println("Update completed");
		
		System.out.println("\n=== Test 06: Seller delete ===");
		System.out.println("Enter Id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();
		
	}

}