package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDaoJDBC createSellerDao() {
		return (SellerDaoJDBC) new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDaoJDBC createDepartmentDao() {
		return (DepartmentDaoJDBC) new DepartmentDaoJDBC(DB.getConnection());
	}
	
}
