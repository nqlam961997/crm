package lam.java18.crm.controller;

import com.google.gson.Gson;
import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.UserModel;
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

@WebServlet(name = "users", urlPatterns = {UrlUtils.URL_USER})
public class UserController extends HttpServlet {
    private Gson gson;
    private UserServiceImpl userServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        userServiceImpl = UserServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = createModel.apply(req);
        ResponseData responseData;
        if (userModel != null) {
            responseData = userServiceImpl.getOne(userModel.getId());
        } else {
            responseData = userServiceImpl.getAll();
        }
        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = createModel.apply(req);
        ResponseData responseData = userServiceImpl.save(userModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = createModel.apply(req);
        ResponseData responseData = userServiceImpl.update(userModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = createModel.apply(req);
        ResponseData responseData = userServiceImpl.deleteById(userModel.getId());

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

    private Function<HttpServletRequest, UserModel> createModel = (req) -> {
        try(BufferedReader reader = new BufferedReader(req.getReader());) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String data = stringBuilder.toString();
            UserModel userModel = gson.fromJson(data, UserModel.class);
            return userModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
}
