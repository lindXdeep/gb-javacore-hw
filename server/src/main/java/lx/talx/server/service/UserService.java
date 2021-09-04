package lx.talx.server.service;

import java.util.List;

import lx.talx.server.model.User;

public interface UserService {
  void add(User user);

  List<User> listUsers();

  User getUserByUserName(String username);

  User getUserByEmail(String email);

  void delete(User user);
}
