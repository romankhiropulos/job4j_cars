package ru.job4j.cars.controller;

import com.google.gson.Gson;
import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.entity.User;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/ad.do")
public class AdvertisementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        String strCIds = req.getParameter("cIds");
        Gson gson = new Gson();
        String[] cIds = gson.fromJson(strCIds, String[].class);
        User curUser = (User) req.getSession().getAttribute("user");
        try {
            Cars.getInstance().saveAdvertisement(
                    new Advertisement()
            );
        } catch (SQLException exception) {
            PrintWriter writer = resp.getWriter();
            writer.println("Data base problem");
            writer.flush();
        }
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }
}
