import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Seller;

public class Program {

    public static void main(String[] args) {

        //model.entites.Department obj = new model.entites.Department(1,"Maria");

        //model.entites.Seller seller = new model.entites.Seller(21, "Bob", "bob@gmail.com" , new Date(),3000.0, obj);
        //System.out.println(seller);

        SellerDao sellerDao = DaoFactory.criarSellerDao(); //injeção de dependencia

        Seller seller = sellerDao.findById(3);


        System.out.println(seller);

    }
}
