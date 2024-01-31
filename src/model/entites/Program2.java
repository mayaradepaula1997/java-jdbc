package model.entites;

import model.dao.DaoFactory;
import model.dao.DepartmenDao;
import model.dao.SellerDao;

public class Program2 {

    public static void main(String[] args) {

        DepartmenDao departmenDao = DaoFactory.createDepartmentDao(); //injeção de dependencia

        System.out.println("\n=== TEST 1: department insert =====");
        Department newDepartment = new Department(null,"Moda");
        departmenDao.insert(newDepartment);
        System.out.println("Novo id = " +newDepartment.getId());




    }
}
