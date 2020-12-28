package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	// Injeção de depenência
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS // Retorna o ID do novo vendedor inserido
					);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
				
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}	
		
		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					 "UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ? "
					);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
					
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}	
		
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
				Department dpt = instantiateDepartment(rs);
				Seller slr = instantiateSeller(rs, dpt);
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

	// Métodos auxiliares
	private Seller instantiateSeller(ResultSet rs, Department dpt) throws SQLException {
		Seller slr = new Seller();
		slr.setId(rs.getInt("Id"));
		slr.setName(rs.getString("Name"));
		slr.setEmail(rs.getString("Email"));
		slr.setBaseSalary(rs.getDouble("BaseSalary"));
		slr.setBirthDate(rs.getDate("BirthDate"));
		slr.setDepartment(dpt);
		return slr;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dpt = new Department();				
		dpt.setId(rs.getInt("DepartmentId"));
		dpt.setName(rs.getString("DepName"));
		return dpt;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name; "									 
					);
			
			rs = st.executeQuery();		
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();			
			while (rs.next()) {
				Department dpt = map.get(rs.getInt("DepartmentId"));
				if (dpt == null) {
					dpt = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dpt);
				}
				Seller slr = instantiateSeller(rs, dpt);
				list.add(slr);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name; "									 
					);
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();		
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();			
			while (rs.next()) {
				Department dpt = map.get(rs.getInt("DepartmentId"));
				if (dpt == null) {
					dpt = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dpt);
				}
				Seller slr = instantiateSeller(rs, dpt);
				list.add(slr);
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	
}
