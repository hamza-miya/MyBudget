package Project.DAO;

import Project.Model.Projet;

public interface ProjetDAO {

    public Long add(Projet projet);

    public Projet getById(long id);

    public void delete(Projet projet);

    public void update(Projet projet);

}
