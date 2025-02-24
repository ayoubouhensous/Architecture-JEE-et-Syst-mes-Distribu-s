package dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("dao1")
public class DaoImpl implements Idao{

    @Override
    public double getData() {
        System.out.println("Version base de donnes ");
        double temp = 10;
        return temp;
    }
}
