package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//Para indicar que essa classe vai ser uma implementação JDBC do me "model.Dao.SellerDao"
public class SellerDaoJDBC implements SellerDao {

    private Connection conn;
    public SellerDaoJDBC (Connection conn){ //estabelendo a conexão
        this.conn = conn;
    }
    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void updat(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs= null;
        try{
            st = conn.prepareStatement(
                            "SELECT seller.*,department.Name as DepName "
                                    + "FROM seller INNER JOIN department "
                                    + "ON seller.DepartmentId = department.Id "
                                    + "WHERE seller.Id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));
                Seller obj = new Seller();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                obj.setEmail(rs.getString("Email"));
                obj.setBaseSalary(rs.getDouble("BaseSalary"));
                obj.getBirthDate(rs.getDate("BirthDate"));
                obj.setDepartment(dep);

                return obj;
            }
             return null;
            }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}