package ru.job4j.cars.controller;

import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.jsonserializer.JsonUtil;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/adview")
public class AdViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setHeader("cache-control", "no-cache");

        String adId = req.getParameter("adId");
        Advertisement ad = null;
        try {
            ad = Cars.getInstance().findAdById(Integer.parseInt(adId));
            String jsonResponse = JsonUtil.GSON_AD.toJson(ad);
            PrintWriter writer = resp.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        } catch (SQLException e) {
            PrintWriter writer = resp.getWriter();
            writer.println("Sorry, server has a problem!");
            writer.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
