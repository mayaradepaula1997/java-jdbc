package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

import java.sql.*;
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

        PreparedStatement st = null;
        try{
            st=conn.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS); // generetaKeys gera uma chave unica pro usuario, com o ID unico
            st.setString(1, obj.getName()); // o nome vai ser do Obj que chegou como parametro
            st.setString(2,obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffeccted = st.executeUpdate(); // executar comando (SQL)

            if (rowsAffeccted > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                 int id = rs.getInt(1);
                 obj.setId(id);

                }
                DB.closeResultSet(rs);
            }
            else {
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>(); //para controlar a não repetição do Departamento

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));
                //toda vez que passar por uma linha ResultSet, vai ser testado (map.get) se o departamento ja existe

                if (dep == null) { //se não
                    dep = instantiateDepartment(rs); // ai sim, vou instanciar ele
                    map.put(rs.getInt("DepartmentId"), dep); //(map.put) salva dentro do meu Map
                }

                Seller obj = instantiateSeller(rs, dep); // instancia todos os vendedores, sem a repetição do Departamento
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