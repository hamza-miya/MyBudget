package Project.DAO;

import Project.Model.Compte;
import Project.Model.Mouvement;

import java.util.List;

public interface CompteDAO {

    public Compte getById(long id);

    public void update(Compte compte);

    public List<Mouvement> getMouvementBy(Compte compte, boolean signe);

}
