package lam.java18.crm.service.impl;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.RoleModel;
import lam.java18.crm.repository.RoleRepository;
import lam.java18.crm.service.RoleService;

public class RoleServiceImpl implements RoleService {
    public static RoleServiceImpl roleServiceImpl = null;
    private RoleRepository roleRepository;

    private RoleServiceImpl() {
        roleRepository = new RoleRepository();
    }

    public ResponseData getAll() {
        return roleRepository.getAll();
    }

    @Override
    public ResponseData getOne(int id) {
        return roleRepository.getOne(id);
    }

    @Override
    public ResponseData deleteById(int id) {
        return roleRepository.deleteById(id);
    }

    @Override
    public ResponseData save(RoleModel roleModel) {
        return roleRepository.save(roleModel);
    }

    @Override
    public ResponseData update(RoleModel roleModel) {
        return roleRepository.update(roleModel);
    }

    public static RoleServiceImpl getInstance() {
        if (roleServiceImpl == null) {
            roleServiceImpl = new RoleServiceImpl();
        }

        return roleServiceImpl;
    }
}
