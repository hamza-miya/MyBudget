package Project.DAO.impl;

import Project.DAO.CompteDAO;
import Project.Model.Compte;
import Project.Model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class CompteDAOImpl implements CompteDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Compte getById(long id) {
        Compte compte =  sessionFactory.getCurrentSession().get(Compte.class, id);
        return compte;
    }
}