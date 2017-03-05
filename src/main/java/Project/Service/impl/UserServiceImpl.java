package Project.Service.impl;

import Project.DAO.UserDAO;
import Project.Model.User;
import Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

        @Autowired
        private UserDAO userDAO;

        @Override
        public long add(User user) {
            return  userDAO.add(user);
        }

        @Override
        public User getById(long id, boolean lazy) {
            return userDAO.getById(id);
        }

        @Override
        public User getByEmail(String email) {
            return userDAO.getByEmail(email);
        }

        @Override
            public void delete(User user) {
                userDAO.delete(user);
            }
}
