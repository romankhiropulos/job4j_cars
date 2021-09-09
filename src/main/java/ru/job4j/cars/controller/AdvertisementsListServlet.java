package ru.job4j.cars.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.List;
import java.util.Objects;

@WebServlet("/ads")
public class AdvertisementsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setHeader("cache-control", "no-cache");

        String listType = req.getParameter("sold");
        List<Advertisement> list = null;
        try {
            if (Objects.equals(listType, "true") || Objects.equals(listType, "false")) {
                list = (List<Advertisement>) Cars.getInstance().findAdBySold(Boolean.parseBoolean(listType));
            } else {
                list = (List<Advertisement>) Cars.getInstance().getAllAdvertisements();
            }
        } catch (SQLException exception) {
            PrintWriter writer = resp.getWriter();
            writer.println("Sorry, server has a problem!");
            writer.flush();
        }

        if (list == null) {
            PrintWriter writer = resp.getWriter();
            writer.println("Sorry, items not found!");
            writer.flush();
        } else {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            String jsonResponse = gson.toJson(list);

            PrintWriter writer = resp.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        }
    }
}
