package lam.java18.crm.service.impl;

import lam.java18.crm.model.JobModel;
import lam.java18.crm.model.ResponseData;
import lam.java18.crm.repository.JobRepository;
import lam.java18.crm.service.JobService;

public class JobServiceImpl implements JobService {

    public static JobServiceImpl jobServiceImpl = null;

    private JobRepository jobRepository;

    private JobServiceImpl() {
        jobRepository = new JobRepository();
    }
    @Override
    public ResponseData getAll() {
        return jobRepository.getAll();
    }

    @Override
    public ResponseData getOne(int id) {
        return jobRepository.getOne(id);
    }

    @Override
    public ResponseData deleteById(int id) {
        return jobRepository.deleteById(id);
    }

    @Override
    public ResponseData save(JobModel model) {
        return jobRepository.save(model);
    }

    @Override
    public ResponseData update(JobModel model) {
        return jobRepository.update(model);
    }

    public static JobServiceImpl getInstance() {
        if (jobServiceImpl == null) {
            jobServiceImpl = new JobServiceImpl();
        }

        return jobServiceImpl;
    }
}
