package Project.DAO.impl;

import Project.DAO.ProjetDAO;
import Project.Model.Projet;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class ProjetDAOImpl implements ProjetDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long add(Projet projet) {
        Serializable id = sessionFactory.getCurrentSession().save(projet);
        return (Long) id;
    }

    @Override
    public Projet getById(long id) {
        Projet projet =  sessionFactory.getCurrentSession().get(Projet.class, id);
        return projet;
    }

    @Override
    public void delete(Projet projet) {
        sessionFactory.getCurrentSession().remove(projet);
    }

    @Override
    public void update(Projet projet) {
        sessionFactory.getCurrentSession().update(projet);
    }
}
