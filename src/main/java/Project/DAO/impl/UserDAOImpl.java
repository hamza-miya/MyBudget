package Project.DAO.impl;

import Project.DAO.UserDAO;
import Project.Model.User;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long add(User user) {
        Serializable id = sessionFactory.getCurrentSession().save(user);
        return (Long) id;
    }

    @Override
    public User getById(long id, boolean lazy) {
        User user =  sessionFactory.getCurrentSession().get(User.class, id);

        if (lazy) {
            Hibernate.initialize(user.getCompte().getMouvements());
        }

        return user;
    }

    @Override
    public User getByEmail(String email){
        javax.persistence.Query query = sessionFactory.getCurrentSession().createQuery("from User WHERE email = :email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().remove(user);
    }
}