package Project.Service;

import Project.Model.Compte;
import Project.Model.Mouvement;

import java.util.List;

public interface CompteService {

    public Compte getById(long id);

    public void update(Compte compte);

    public List<Mouvement> getMouvementBy(Compte compte, boolean signe);
}