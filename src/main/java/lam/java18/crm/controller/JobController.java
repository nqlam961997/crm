package lam.java18.crm.controller;

import com.google.gson.Gson;
import lam.java18.crm.model.JobModel;
import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.RoleModel;
import lam.java18.crm.service.impl.JobServiceImpl;
import lam.java18.crm.service.impl.UserServiceImpl;
import lam.java18.crm.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import java.util.function.Function;

@WebServlet(name = "jobs", urlPatterns = {UrlUtils.URL_JOB})
public class JobController extends HttpServlet {
    private Gson gson;
    private JobServiceImpl jobServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        jobServiceImpl = JobServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JobModel jobModel = createModel.apply(req);
        ResponseData responseData;
        if (jobModel != null) {
            responseData = jobServiceImpl.getOne(jobModel.getId());
        } else {
            responseData = jobServiceImpl.getAll();
        }

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JobModel jobModel = createModel.apply(req);
        ResponseData responseData = jobServiceImpl.save(jobModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JobModel jobModel = createModel.apply(req);
        ResponseData responseData = jobServiceImpl.update(jobModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JobModel jobModel = createModel.apply(req);
        ResponseData responseData = jobServiceImpl.deleteById(jobModel.getId());

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    private BiConsumer<HttpServletResponse, String> handleSendResp = (resp, jsonString) -> {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = null;
        try {
            printWriter = resp.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printWriter.print(jsonString);
        printWriter.flush();
    };

    private Function<HttpServletRequest, JobModel> createModel = (req) -> {
        try(BufferedReader reader = new BufferedReader(req.getReader());) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String data = stringBuilder.toString();
            JobModel jobModel = gson.fromJson(data, JobModel.class);
            return jobModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
