package ru.job4j.cars.controller;

import com.google.gson.Gson;
import ru.job4j.cars.entity.User;
import ru.job4j.cars.jsonserializer.JsonUtil;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String userStr = req.getParameter("user");
        Gson gson = new Gson();
        User authUser = gson.fromJson(userStr, User.class);
        User dbUser = null;
        try {
            dbUser = Cars.getInstance().findUserByLoginAndPassword(
                    Objects.requireNonNull(authUser).getLogin(),
                    Objects.requireNonNull(authUser).getPassword()
            );
        } catch (SQLException exception) {
            Cars.getLogger().error("SQL exception: " + exception.getMessage(), exception);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (Objects.nonNull(dbUser)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", dbUser);
            String jsonResponse = JsonUtil.GSON_USER.toJson(dbUser);
            PrintWriter writer = resp.getWriter();
            writer.println(jsonResponse);
            writer.flush();
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}