package lam.java18.crm.service.impl;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.UserModel;
import lam.java18.crm.repository.UserRepository;
import lam.java18.crm.service.AuthService;

public class AuthServiceImpl implements AuthService {

    public static AuthServiceImpl authServiceImpl = null;

    private UserRepository userRepository;

    private AuthServiceImpl() {
        userRepository = new UserRepository();
    }

    @Override
    public ResponseData login(UserModel theModel) {
        if (!isValidUser(theModel.getEmail(), theModel.getPassword())) {
            ResponseData responseData = new ResponseData();
            responseData.setMessages("The email or password is not correct! Please try again");

            return responseData;
        }
        return userRepository.findByEmailAndPassword(theModel.getEmail(), theModel.getPassword());
    }

    @Override
    public ResponseData register(UserModel theModel) {
        if (!isValidUser(theModel.getEmail(), theModel.getPassword())) {
            ResponseData responseData = new ResponseData();
            responseData.setSuccess(false);
            responseData.setMessages("The email or password can not be null or blank");
            return responseData;
        }

        boolean userExisted = userRepository.existedByEmail(theModel.getEmail());

        if (userExisted) {
            ResponseData responseData = new ResponseData();
            responseData.setSuccess(false);
            responseData.setMessages("The email address is already in use by another account");
            return responseData;
        }

        ResponseData responseData = userRepository.save(theModel);

        return responseData;
    }

    private boolean isValidUser(String email, String password) {
        boolean isValid = true;
        if (email == null || "".equals(email.trim())) {
            isValid = false;
        }

        if (password == null || "".equals(password.trim())) {
            isValid = false;
        }

        return isValid;
    }

    public static AuthServiceImpl getInstance() {
        if (authServiceImpl == null) {
            authServiceImpl = new AuthServiceImpl();
        }

        return authServiceImpl;
    }
}
