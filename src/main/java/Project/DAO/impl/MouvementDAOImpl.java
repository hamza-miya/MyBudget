package Project.DAO.impl;

import Project.DAO.MouvementDAO;
import Project.Model.Mouvement;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class MouvementDAOImpl implements MouvementDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long add(Mouvement mouvement) {
        Serializable id = sessionFactory.getCurrentSession().save(mouvement);
        return (Long) id;
    }

    @Override
    public Mouvement getById(long id) {
        Mouvement mouvement =  sessionFactory.getCurrentSession().get(Mouvement.class, id);
        return mouvement;
    }

    @Override
    public void delete(Mouvement mouvement) {
        sessionFactory.getCurrentSession().remove(mouvement);
    }

    @Override
    public void update(Mouvement mouvement) {
        sessionFactory.getCurrentSession().update(mouvement);
    }


}
