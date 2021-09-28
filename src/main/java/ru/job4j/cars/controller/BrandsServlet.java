package ru.job4j.cars.controller;

import ru.job4j.cars.entity.Brand;
import ru.job4j.cars.jsonserializer.JsonUtil;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/brands")
public class BrandsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        List<Brand> brands = (List<Brand>) Cars.getInstance().getAllBrands();
        String jsonResponse = JsonUtil.GSON_BRAND.toJson(brands);
        PrintWriter writer = resp.getWriter();
        writer.write(jsonResponse);
        writer.flush();
    }
}