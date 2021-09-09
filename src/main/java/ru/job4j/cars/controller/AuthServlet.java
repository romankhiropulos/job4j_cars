package ru.job4j.cars.controller;

import com.google.gson.Gson;
import ru.job4j.cars.entity.User;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String userStr = req.getParameter("user");
        Gson gson = new Gson();
        User authUser = gson.fromJson(userStr, User.class);
        User dbUser = Cars.getInstance().findUserByLoginAndPassword(
                Objects.requireNonNull(authUser).getLogin(),
                Objects.requireNonNull(authUser).getPassword()
        );

        if (Objects.nonNull(dbUser)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", dbUser);
            String jsonResponse = gson.toJson(dbUser);
            PrintWriter writer = resp.getWriter();
            writer.println(jsonResponse);
            writer.flush();
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}