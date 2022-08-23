package lam.java18.crm.service.impl;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.UserModel;
import lam.java18.crm.repository.UserRepository;
import lam.java18.crm.service.UserService;

public class UserServiceImpl implements UserService {

    public static UserServiceImpl userServiceImpl = null;

    private UserRepository userRepository;

    private UserServiceImpl() {
        this.userRepository = new UserRepository();
    }
    @Override
    public ResponseData getAll() {
         return userRepository.getAll();
    }

    @Override
    public ResponseData getOne(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public ResponseData deleteById(int id) {
        return userRepository.deleteById(id);
    }

    @Override
    public ResponseData save(UserModel model) {
        return userRepository.save(model);
    }

    @Override
    public ResponseData update(UserModel model) {
        return userRepository.update(model);
    }

    public static UserServiceImpl getInstance() {
        if (userServiceImpl == null) {
            userServiceImpl = new UserServiceImpl();
        }
        return userServiceImpl;
    }
}
