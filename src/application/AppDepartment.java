package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class AppDepartment {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== Test 01: Department findById ===");
		
		
		System.out.println("\n=== Test 02: Department findByDepartment ===");

		
		System.out.println("\n=== Test 03: Department findAll ===");

		
		System.out.println("\n=== Test 04: Department insert ===");

		
		System.out.println("\n=== Test 05: Department update ===");

		
		System.out.println("\n=== Test 06: Department delete ===");


	}

}
