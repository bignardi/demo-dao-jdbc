package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
				
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== Test 01 findById ===");
		Seller seller = sellerDao.findById(3);		
		System.out.println(seller);		
		
		System.out.println("\n=== Test 02 findByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller slr : list) {
			System.out.println(slr);
		}
	}

}