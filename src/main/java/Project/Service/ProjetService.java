package Project.Service;

import Project.Model.Projet;

public interface ProjetService {

    public long add(Projet projet);

    public Projet getById(long id);

    public void delete(Projet projet);

    public void update(Projet projet);
}