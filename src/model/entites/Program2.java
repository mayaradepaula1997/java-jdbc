package model.entites;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmenDao;

import model.dao.SellerDao;

public class Program2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DepartmenDao departmenDao = DaoFactory.createDepartmentDao(); //injeção de dependencia

        System.out.println("\n=== TEST 1: department Insert =====");
        Department newDepartment = new Department(null,"Moda");
        departmenDao.insert(newDepartment);
        System.out.println("Novo id = " +newDepartment.getId());



        System.out.println("\n=== TEST 2: department Update =====");
        Department department = departmenDao.findById(1);
        department.setName("Food");
        departmenDao.update(department);
        System.out.println("Update completed");



         System.out.println("\n=== TEST 3 : List Delete =======");
         System.out.println("Entre com o id que deseja deletar:");
         int id = sc.nextInt();
         departmenDao.deleteById(id);
         System.out.println("Deletado com sucesso!");



        System.out.println("\n=== TEST 4: findById =======");
        Department dep = departmenDao.findById(1);
        System.out.println(dep);



        System.out.println("\n=== TEST 5 : List findAll =======");
        List<Department> list = departmenDao.findAll();
        for (Department departamento : list){
            System.out.println(departamento);
        }


    }
}
