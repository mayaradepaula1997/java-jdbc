import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner  sc = new Scanner(System.in);

        //model.entites.Department obj = new model.entites.Department(1,"Maria");

        //model.entites.Seller seller = new model.entites.Seller(21, "Bob", "bob@gmail.com" , new Date(),3000.0, obj);
        //System.out.println(seller);

        SellerDao sellerDao = DaoFactory.criarSellerDao(); //injeção de dependencia

        System.out.println("\n=== TEST 1:  seller findById =====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2:  seller findByDepartment =====");
        Department department = new Department( 2, null);
        List<Seller> list = sellerDao.findByDepartament(department);
        for (Seller obj : list){
            System.out.println(obj);

        }

        System.out.println("\n=== TEST 3:  seller findAll =====");
        list = sellerDao.findAll();
        for (Seller obj : list){
            System.out.println(obj);
        }

        System.out.println("\n=== TEST 4:  seller  insert =====");
        Seller newSeller = new Seller (null,"Greg","gred@gmail.com",new Date(),4000.0,department);
        sellerDao.insert(newSeller);
        System.out.println("Novo id = " + newSeller.getId());

        System.out.println("\n=== TEST 5:  seller update =====");
        seller =sellerDao.findById(1);
        seller.setName("Martha Waine");
        sellerDao.updat(seller);
        System.out.println("Update completed");

        System.out.println("\n=== TEST 6:  seller delete =====");
        System.out.println("Entre com o Id que vai ser deletado:");
        int id = sc.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Deletado com sucesso!");

    }
}
