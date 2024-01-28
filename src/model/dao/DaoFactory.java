package model.dao;//Classe auxiliar, responsavel por INSTANCIAR os meus DAOS

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory { //operaçoes estaticas para instanciar os DAOS

    public  static SellerDao criarSellerDao() { // criarSellerDao vai ser uma instancia do model.Dao.impl.SellerDaoJDBC

        return new SellerDaoJDBC(DB.getConnection()); // obrigatorio passar a conexão como argumento

    }

    // EXPOR UM MÉTODO QUE RETORNA O TIPO DA INTERFACE, MAS INTERNAMENTE
    //ELA VAI INSTANCIAR UM IMPLEMENTAÇÃO


}
