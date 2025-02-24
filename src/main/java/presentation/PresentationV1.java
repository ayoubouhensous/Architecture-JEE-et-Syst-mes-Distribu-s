package presentation;

import dao.DaoImpl;
import ext.DaoImplV2;
import metier.ImetierImpl;

public class PresentationV1 {

public static void main(String[] args) {

    /* inection de dependance par instanciation static

    DaoImpl dao = new DaoImpl();
    ImetierImpl imetier = new ImetierImpl();
    imetier.setDao(dao); via setter
    System.out.println(imetier.calculer()); */


    /*injection de dependance via constucteur */
    DaoImplV2 dao = new DaoImplV2();
    ImetierImpl imetier=new ImetierImpl(dao);
    System.out.println(imetier.calculer());

}
}
