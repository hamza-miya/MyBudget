package Project.Service.impl;


import Project.DAO.ProjetDAO;
import Project.Model.Projet;
import Project.Service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {

    @Autowired
    private ProjetDAO projetDAO;

    @Override
    public long add(Projet projet) {
        return projetDAO.add(projet);
    }

    @Override
    public Projet getById(long id) {
        return projetDAO.getById(id);
    }

    @Override
    public void delete(Projet projet) {
        projetDAO.delete(projet);
    }

    @Override
    public void update(Projet projet) {
        projetDAO.update(projet);
    }
}
