package Project.Service.impl;

import Project.DAO.MouvementDAO;
import Project.Model.Mouvement;
import Project.Service.MouvementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MouvementServiceImpl implements MouvementService{

    @Autowired
    private MouvementDAO mouvementDAO;

    @Override
    public long add(Mouvement mouvement) {
        return mouvementDAO.add(mouvement);
    }

    @Override
    public Mouvement getById(long id) {
        return mouvementDAO.getById(id);
    }

    @Override
    public void delete(Mouvement mouvement) {
        mouvementDAO.delete(mouvement);
    }

    @Override
    public void update(Mouvement mouvement) {
        mouvementDAO.update(mouvement);
    }
}
