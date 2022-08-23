package lam.java18.crm.service;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.UserModel;

public interface AuthService {
    ResponseData login(UserModel model);
    ResponseData register(UserModel model);
}
