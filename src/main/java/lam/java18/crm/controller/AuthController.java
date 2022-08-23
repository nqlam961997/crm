package lam.java18.crm.controller;

import com.google.gson.Gson;
import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.TaskModel;
import lam.java18.crm.model.UserModel;
import lam.java18.crm.service.impl.AuthServiceImpl;
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

@WebServlet(urlPatterns = {UrlUtils.URL_LOGIN, UrlUtils.URL_REGISTER, UrlUtils.URL_LOGOUT})
public class AuthController extends HttpServlet {
    private Gson gson;
    private AuthServiceImpl authServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        authServiceImpl = AuthServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtils.URL_LOGIN: {
                processRegister(req, resp);
            }
            case UrlUtils.URL_REGISTER: {
                processLogin(req, resp);
            }
            case UrlUtils.URL_LOGOUT: {
                req.getSession().invalidate();
            }
        }
    }

    private void processLogin(HttpServletRequest req, HttpServletResponse resp) {
        UserModel userModel = createModel.apply(req);
        ResponseData responseData = authServiceImpl.login(userModel);

        if (responseData.isSuccess()) {
            req.getSession().setAttribute("currentUser", responseData.getData());
        }
        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    private void processRegister(HttpServletRequest req, HttpServletResponse resp) {
        UserModel userModel = createModel.apply(req);
        ResponseData responseData = authServiceImpl.register(userModel);

        if (responseData.isSuccess()) {
            req.getSession().setAttribute("currentUser", responseData.getData());
        }

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
