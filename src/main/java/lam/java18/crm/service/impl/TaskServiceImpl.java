package lam.java18.crm.service.impl;

import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.TaskModel;
import lam.java18.crm.repository.TaskRepository;
import lam.java18.crm.service.TaskService;

public class TaskServiceImpl implements TaskService {

    public static TaskServiceImpl taskServiceImpl = null;

    private TaskRepository taskRepository;

    private TaskServiceImpl() {
        taskRepository = new TaskRepository();
    }
    @Override
    public ResponseData getAll() {
        return taskRepository.getAll();
    }

    @Override
    public ResponseData getOne(int id) {
        return taskRepository.getOne(id);
    }

    @Override
    public ResponseData deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public ResponseData save(TaskModel model) {
        return taskRepository.save(model);
    }

    @Override
    public ResponseData update(TaskModel model) {
        return taskRepository.update(model);
    }

    public static TaskServiceImpl getInstance() {
        if (taskServiceImpl == null) {
            taskServiceImpl = new TaskServiceImpl();
        }
        return taskServiceImpl;
    }
}
