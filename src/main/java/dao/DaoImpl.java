package dao;

public class DaoImpl implements Idao{

    @Override
    public double getData() {
        System.out.println("Version base de donnes ");
        double temp = 10;
        return temp;
    }
}
