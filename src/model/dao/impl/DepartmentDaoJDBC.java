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
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	
	// Injeção de depenência
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+ "(name) "
					+ "VALUES "
					+ "(?) ",
					Statement.RETURN_GENERATED_KEYS // Retorna o ID do novo departamento inserido
					);
			
			st.setString(1, obj.getName());
			
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
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ? "
					);
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ? ");
			
			st.setInt(1, id);
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					" select department.* , department.Name as DepName " 
					+ "from department inner join seller "
					+ "on seller.DepartmentId = department.Id "
					+ "where department.Id = ? "
					);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dpt = instantiateDepartment(rs);
				//Seller slr = instantiateSeller(rs, dpt);
				return dpt;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	// Método auxiliador
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dpt = new Department();				
		dpt.setId(rs.getInt("Id"));
		dpt.setName(rs.getString("Name"));
		return dpt;
	}
	
	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement( 
					"select id, "
					+ "name from department "
					+ "order by name "
					);
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<Department>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				Department dpt = map.get(rs.getInt("Id"));
				if (dpt == null) {
					dpt = instantiateDepartment(rs);
					map.put(rs.getInt("Id"), dpt);
				}
				list.add(dpt);
			}
			return list;
			
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
