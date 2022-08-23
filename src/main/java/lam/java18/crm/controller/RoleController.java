package lam.java18.crm.controller;

import com.google.gson.Gson;
import lam.java18.crm.model.ResponseData;
import lam.java18.crm.model.RoleModel;
import lam.java18.crm.service.impl.RoleServiceImpl;
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

@WebServlet(name = "roles", urlPatterns = { UrlUtils.URL_ROLE })
public class RoleController extends HttpServlet {

    private Gson gson;
    private RoleServiceImpl roleServiceImpl;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        roleServiceImpl = RoleServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleModel roleModel = createModel.apply(req);
        ResponseData responseData;
        if (roleModel != null) {
            responseData = roleServiceImpl.getOne(roleModel.getId());
        } else {
            responseData = roleServiceImpl.getAll();
        }
        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleModel roleModel = createModel.apply(req);
        ResponseData responseData = roleServiceImpl.save(roleModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleModel roleModel = createModel.apply(req);
        ResponseData responseData = roleServiceImpl.update(roleModel);

        String json = gson.toJson(responseData);
        handleSendResp.accept(resp, json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleModel roleModel = createModel.apply(req);
        ResponseData responseData = roleServiceImpl.deleteById(roleModel.getId());

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

    private Function<HttpServletRequest, RoleModel> createModel = (req) -> {
        try(BufferedReader reader = new BufferedReader(req.getReader());) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String data = stringBuilder.toString();
            RoleModel roleModel = gson.fromJson(data, RoleModel.class);
            return roleModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

}
