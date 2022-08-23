package lam.java18.crm.service.impl;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.StatusModel;
import lam.java18.crm.repository.StatusRepository;
import lam.java18.crm.repository.TaskRepository;
import lam.java18.crm.service.StatusService;

public class StatusServiceImpl implements StatusService {

    public static StatusServiceImpl statusServiceImpl = null;

    private StatusRepository statusRepository;

    private StatusServiceImpl() {
        statusRepository = new StatusRepository();
    }

    @Override
    public ResponseData getAll() {
        return statusRepository.getAll();
    }

    @Override
    public ResponseData getOne(int id) {
        return statusRepository.getOne(id);
    }

    @Override
    public ResponseData deleteById(int id) {
        return statusRepository.deleteById(id);
    }

    @Override
    public ResponseData save(StatusModel model) {
        return statusRepository.save(model);
    }

    @Override
    public ResponseData update(StatusModel model) {
        return statusRepository.update(model);
    }

    public static StatusServiceImpl getInstance() {
        if (statusServiceImpl == null) {
            statusServiceImpl = new StatusServiceImpl();
        }
        return statusServiceImpl;
    }
}
