package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmenDao;
import model.entites.Department;

import java.sql.*;
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
    public void updat(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }
}
