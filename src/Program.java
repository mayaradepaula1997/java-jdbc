import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

import java.util.List;

public class Program {

    public static void main(String[] args) {

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

    }
}
