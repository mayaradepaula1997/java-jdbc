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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Para indicar que essa classe vai ser uma implementação JDBC do me "model.Dao.SellerDao"
public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) { //estabelendo a conexão
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
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, dep);
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    @Override
    public List<Seller> findByDepartament(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            st.setInt(1, department.getId());

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>(); //Como são varios valores eu tenho que criar uma lista de resultados
            Map<Integer, Department> map = new HashMap<>(); //Controlar a não repetição do Department, utilizando a estrutura Map <chave, valor>

            while (rs.next()) { //percorrer o ResultSet enquando houver um proximo

                Department dep = map.get(rs.getInt("DepartmentId"));// A cada vez que passar pelo WHILE, vou testar se o departamento ja existe

                if (dep == null) { //Se o resultado for "null" e esse departamento não existir, ai sim eu vou instanciar o Department
                    dep = instantiateDepartment(rs); //intanciando o departamento, atraves da função criada "instantiateDepartment"
                    map.put(rs.getInt("DepartmentId"), dep); //Salvando o departamento dentro do meu Map, para que na proxima vez eu verificar que ele ja vai existe

                }

                Seller obj = instantiateSeller(rs, dep); // instanciando o Seller, com a função ja criada "instantiateSeller"
                list.add(obj); // add na lista

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