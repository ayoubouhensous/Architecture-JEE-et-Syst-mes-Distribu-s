package presentation;

import dao.DaoImpl;
import metier.ImetierImpl;

public class PresentationV1 {

public static void main(String[] args) {

    /* inection de dependance par instanciation static */

    DaoImpl dao = new DaoImpl();
    ImetierImpl imetier = new ImetierImpl();
    imetier.setDao(dao);
    System.out.println(imetier.calculer());




}
}
