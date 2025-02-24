package metier;

import dao.Idao;

public class ImetierImpl implements IMetier{

    private Idao dao;

    public ImetierImpl(){

    }

    public ImetierImpl(Idao dao) {
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
