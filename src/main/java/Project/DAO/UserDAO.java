package Project.DAO;

import Project.Model.User;

public interface UserDAO {

    public Long add(User user);

    public User getById(long id, boolean lazy);

    public User getByEmail(String email);

    public void delete(User user);
}
