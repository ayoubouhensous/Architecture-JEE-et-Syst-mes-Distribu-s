package metier;

import dao.Idao;

public class ImetierImpl implements IMetier{

    private Idao dao;
    @Override
    public double calculer() {

        double t =dao.getData();
        double resultado = t*23;
        
        return resultado;
    }

}
