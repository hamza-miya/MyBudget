package Project.Service;

import Project.Model.Mouvement;

public interface MouvementService {

    public long add(Mouvement mouvement);

    public Mouvement getById(long id);

    public void delete(Mouvement mouvement);
}