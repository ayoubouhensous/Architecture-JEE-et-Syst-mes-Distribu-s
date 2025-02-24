package presentation;

import dao.Idao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(new File("config.txt"));
            String daoClasseName = sc.nextLine();

            Class cdao = Class.forName(daoClasseName);
            Idao idao = (Idao) cdao.getDeclaredConstructor().newInstance();

            String metierClassName = sc.nextLine();

            Class cmetier=Class.forName(metierClassName);
            /*IMetier metier = (IMetier) cmetier.getDeclaredConstructor(Idao.class).newInstance(idao);*/
            IMetier metier=(IMetier) cmetier.getDeclaredConstructor().newInstance();

            Method setDao=cmetier.getDeclaredMethod("setDao",Idao.class);
            setDao.invoke(metier,idao);

            System.out.println(metier.calculer());

        }catch (Exception e) {
            e.printStackTrace();
        }






    }
}
