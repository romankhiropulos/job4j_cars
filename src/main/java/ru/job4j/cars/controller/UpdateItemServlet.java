package ru.job4j.cars.controller;

import com.google.gson.Gson;
import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/adupdate.do")
public class UpdateItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        try {
            String adStr = req.getParameter("ad");
            Gson gson = new Gson();
            Advertisement ad = gson.fromJson(adStr, Advertisement.class);
            Cars.getInstance().updateAdvertisement(ad);
        } catch (SQLException exception) {
            PrintWriter writer = resp.getWriter();
            writer.println("Data base problem");
            writer.flush();
        }
        resp.sendRedirect(req.getContextPath() + "/index.html");
    }
}
