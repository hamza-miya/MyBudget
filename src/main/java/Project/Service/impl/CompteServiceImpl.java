package Project.Service.impl;

import Project.DAO.CompteDAO;
import Project.Model.Compte;
import Project.Model.Mouvement;
import Project.Service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteDAO compteDAO;

    @Override
    public Compte getById(long id) {
        return compteDAO.getById(id);
    }

    @Override
    public void update(Compte compte) {
        compteDAO.update(compte);
    }

    @Override
    public List<Mouvement> getMouvementBy(Compte compte, boolean signe) {
        return compteDAO.getMouvementBy(compte, signe);
    }

}
