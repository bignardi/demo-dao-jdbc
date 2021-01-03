package application;

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
		Department department = departmentDao.findById(4);
		System.out.println(department);
		
		System.out.println("\n=== Test 02: Department findAll ===");
		List<Department> list = departmentDao.findAll();
		for (Department dpt : list) {
			System.out.println(dpt);
		}
		
		System.out.println("\n=== Test 03: Department insert ===");
		Department dpt = new Department(null, "Tecnology");
		departmentDao.insert(dpt);
		System.out.println("Inserted! New Id = " + dpt.getId());
		
		System.out.println("\n=== Test 04: Department update ===");
		department = departmentDao.findById(1);
		department.setName("Smartphones");
		departmentDao.update(department);
		System.out.println("Update completed");
		
		System.out.println("\n=== Test 05: Department delete ===");
		System.out.println("Enter Id for delete test: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();

	}

}
