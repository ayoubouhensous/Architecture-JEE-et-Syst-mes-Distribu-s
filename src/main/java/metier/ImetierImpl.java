package metier;

import dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("metier")
public class ImetierImpl implements IMetier{

    private Idao dao;



    public ImetierImpl(@Qualifier("dao2") Idao dao) {
        this.dao = dao;
    }
    @Override
    public double calculer() {

        double t =dao.getData();
        double resultado = t*23;

        return resultado;
    }

    public void setDao(Idao dao) {
        this.dao = dao;
    }
}
