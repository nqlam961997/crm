package lam.java18.crm.service;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.RoleModel;

public interface JdbcService<T> {
    ResponseData getAll();

    ResponseData getOne(int id);

    ResponseData deleteById(int id);

    ResponseData save(T model);

    ResponseData update(T model);
}
