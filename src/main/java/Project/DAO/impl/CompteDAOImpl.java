package Project.DAO.impl;

import Project.DAO.CompteDAO;
import Project.Model.Compte;
import Project.Model.Mouvement;
import Project.Model.User;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class CompteDAOImpl implements CompteDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Compte getById(long id) {
        Compte compte =  sessionFactory.getCurrentSession().get(Compte.class, id);
        return compte;
    }

    public void update(Compte compte) {
        sessionFactory.getCurrentSession().update(compte);
    }

    @Override
    public List<Mouvement> getMouvementBy(Compte compte, boolean signe) {
        javax.persistence.Query query = sessionFactory.getCurrentSession().createQuery(
                "from Mouvement where signe = :signe and id_compte = :idCompte"
        );
        query.setParameter("signe", signe);
        query.setParameter("idCompte", compte);
        return (List<Mouvement>) query.getResultList();
    }

}