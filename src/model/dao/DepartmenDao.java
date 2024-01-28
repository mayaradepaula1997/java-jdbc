package model.dao;

import model.entites.Department;

import java.util.List;

public interface DepartmenDao {

    void insert (Department obj); // responsavel por inserir no banco de dados esse obj
    void updat (Department obj);
    void deleteById (Integer id);

    Department findById (Integer id);
    //responsavel por pegar o Id e consultar no bando de dados o obj com esse Id, se existir
    // vai retorna, senão vai retorna null


    List <Department> findAll();
    //responsaval por retorna todos os departamentos
}
