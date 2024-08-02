package model;

import java.util.List;

public interface IUserRepo {
    void addUSer(IUser user);
    IUser getUserByID(String userID);
    void updateUser(IUser user);
    void deleteUser(IUser user);
    List<IUser> getAllUsers();
}
