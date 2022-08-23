package lam.java18.crm.filter;

import lam.java18.crm.utils.UrlUtils;

import javax.servlet.*;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CrossFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (isLoginUser(req) || isAuthUrl(req)) {
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Access-Control-Allow-Headers", "*");
            resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            chain.doFilter(request, response);
        }
    }

    private boolean isAuthUrl(HttpServletRequest req) {
        var path = req.getServletPath();
        return path.startsWith(UrlUtils.URL_LOGIN) || path.startsWith(UrlUtils.URL_REGISTER);
    }

    private boolean isLoginUser(HttpServletRequest req) {
        return req.getSession().getAttribute("currentUser") != null;
    }

}
