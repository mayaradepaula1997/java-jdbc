package model.dao;

import model.entites.Department;
import model.entites.Seller;

import java.util.List;

public interface SellerDao {
    void insert (Seller obj); // responsavel por inserir no banco de dados esse obj
    void updat (Seller obj);
    void deleteById (Integer id);

    Seller findById (Integer id); //responsavel por pegar o Id e consultar no bando de dados o obj com esse Id, se existir
                                 // vai retorna, senão vai retorna null



    List<Seller> findAll();//responsaval por retorna todos os departamentos

    List<Seller> findByDepartament (Department department);

}

