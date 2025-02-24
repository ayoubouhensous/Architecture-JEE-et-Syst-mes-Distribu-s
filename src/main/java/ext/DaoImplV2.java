package ext;

import dao.DaoImpl;
import dao.Idao;
import org.springframework.stereotype.Component;

@Component("dao2")
public class DaoImplV2 implements Idao {

    @Override
    public double getData() {
        System.out.println("Version web serivce ");
        double temp = 12;

        return temp;
    }
}
