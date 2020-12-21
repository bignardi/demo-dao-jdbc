package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	// Inje��o de depen�ncia
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select seller.* , department.Name as DepName "
					+ "from seller inner join department "
					+ "on seller.DepartmentId = department.Id "
					+ "where seller.Id = ?"					 
					);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dpt = new Department();				
				dpt.setId(rs.getInt("DepartmentId"));
				dpt.setName(rs.getString("DepName"));
				Seller slr = new Seller();
				slr.setId(rs.getInt("Id"));
				slr.setName(rs.getString("Name"));
				slr.setEmail(rs.getString("Email"));
				slr.setBaseSalary(rs.getDouble("BaseSalary"));
				slr.setBirthDate(rs.getDate("BirthDate"));
				slr.setDepartment(dpt);
				return slr;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}	
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
