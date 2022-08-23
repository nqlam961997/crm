package lam.java18.crm.controller;

import com.google.gson.Gson;
import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.StatusModel;
import lam.java18.crm.service.impl.StatusServiceImpl;
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

@WebServlet(name = "status", urlPatterns = {UrlUtils.URL_STATUS})
public class StatusController extends HttpServlet {
    private Gson gson;
    private StatusServiceImpl statusServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        statusServiceImpl = StatusServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatusModel statusModel = createModel.apply(req);
        ResponseData responseData;
        if (statusModel != null) {
            responseData = statusServiceImpl.getOne(statusModel.getId());
        } else {
            responseData = statusServiceImpl.getAll();
        }
        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatusModel statusModel = createModel.apply(req);
        ResponseData responseData = statusServiceImpl.save(statusModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatusModel statusModel = createModel.apply(req);
        ResponseData responseData = statusServiceImpl.update(statusModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StatusModel statusModel = createModel.apply(req);
        ResponseData responseData = statusServiceImpl.deleteById(statusModel.getId());

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

    private Function<HttpServletRequest, StatusModel> createModel = (req) -> {
        try(BufferedReader reader = new BufferedReader(req.getReader());) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String data = stringBuilder.toString();
            StatusModel statusModel = gson.fromJson(data, StatusModel.class);
            return statusModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
