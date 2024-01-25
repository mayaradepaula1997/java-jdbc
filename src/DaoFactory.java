//Classe auxiliar, responsavel por INSTANCIAR os meus DAOS

public class DaoFactory { //operaçoes estaticas para instanciar os DAOS

    public  static SellerDao criarSellerDao() {
        return new SellerDaoJDBC();
    }

    // EXPOR UM MÉTODO QUE RETORNA O TIPO DA INTERFACE, MAS INTERNAMENTE
    //ELA VAI INSTANCIAR UM IMPLEMENTAÇÃO


}
