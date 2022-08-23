package lam.java18.crm.controller;

import com.google.gson.Gson;
import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.TaskModel;
import lam.java18.crm.service.impl.TaskServiceImpl;
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

@WebServlet(name = "tasks", urlPatterns = {UrlUtils.URL_TASK})
public class TaskController extends HttpServlet {
    private Gson gson;
    private TaskServiceImpl taskServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        taskServiceImpl = TaskServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskModel taskModel = createModel.apply(req);
        ResponseData responseData;
        if (taskModel != null) {
            responseData = taskServiceImpl.getOne(taskModel.getId());
        } else {
            responseData = taskServiceImpl.getAll();
        }
        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskModel taskModel = createModel.apply(req);
        ResponseData responseData = taskServiceImpl.save(taskModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskModel taskModel = createModel.apply(req);
        ResponseData responseData = taskServiceImpl.update(taskModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskModel taskModel = createModel.apply(req);
        ResponseData responseData = taskServiceImpl.deleteById(taskModel.getId());

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

    private Function<HttpServletRequest, TaskModel> createModel = (req) -> {
        try(BufferedReader reader = new BufferedReader(req.getReader());) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String data = stringBuilder.toString();
            TaskModel taskModel = gson.fromJson(data, TaskModel.class);
            return taskModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
