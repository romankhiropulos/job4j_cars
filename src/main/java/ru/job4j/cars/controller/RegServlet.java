package ru.job4j.cars.controller;

import com.google.gson.Gson;
import ru.job4j.cars.entity.User;
import ru.job4j.cars.jsonserializer.JsonUtil;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String userStr = req.getParameter("user");
            Gson gson = new Gson();
            User user = gson.fromJson(userStr, User.class);
            if (Cars.getInstance().findUserByLogin(user.getLogin()) != null) {
                PrintWriter writer = resp.getWriter();
                writer.println("Account already exists!");
                writer.flush();
            } else {
                Cars.getInstance().saveUser(user);
                HttpSession sc = req.getSession();
                sc.setAttribute("user", user);
                String jsonResponse = JsonUtil.GSON_USER.toJson(user);
                PrintWriter writer = resp.getWriter();
                writer.println(jsonResponse);
                writer.flush();
            }
        } catch (SQLException exception) {
            PrintWriter writer = resp.getWriter();
            writer.println("Data base problem");
            writer.flush();
        }
    }
}
