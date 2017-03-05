package Project.DAO;

import Project.Model.Mouvement;

public interface MouvementDAO {

    public Long add(Mouvement mouvement);

    public Mouvement getById(long id);

    public void delete(Mouvement mouvement);

}
