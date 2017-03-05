package Project.Service;

import Project.Model.User;

public interface UserService {

    public long add(User user);

    public User getById(long id, boolean lazy);

    public User getByEmail(String email);

    public void delete(User user);
}