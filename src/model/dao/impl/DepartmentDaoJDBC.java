package model.dao.impl;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmenDao;
import model.entites.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmenDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) { //estabelendo a conexão
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO department "
                            + "(Name) "
                            + "VALUES "
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);// gera uma chave unica ID

            st.setString(1, obj.getName());

            int rowsAffeccted = st.executeUpdate(); // executar comando SQL

            if (rowsAffeccted > 0) { // saber quantas linhas foram alteradas
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");

            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }


    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE department " +
                            "SET Name = ? " +
                            "WHERE Id = ?");

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }



    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM department WHERE Id = ?");
            st.setInt(1, id);

            st.executeUpdate();

        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }



    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department obj = new Department();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }



    @Override
    public List<Department> findAll() {
       PreparedStatement st = null;
       ResultSet rs = null;

       try {
           st= conn.prepareStatement(
                   "SELECT * FROM department ORDER BY Name");

           rs=st.executeQuery();

           List<Department> list = new ArrayList<>();

           while (rs.next ()){
               Department obj = new Department();
               obj.setId(rs.getInt("Id"));
               obj.setName(rs.getString("Name"));
               list.add(obj);
           }
           return list;
       }
       catch (SQLException e) {
           throw new DbException(e.getMessage());
       }
       finally {
           DB.closeStatement(st);
           DB.closeResultSet(rs);
       }
    }


}
