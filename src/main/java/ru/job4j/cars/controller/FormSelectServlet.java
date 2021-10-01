package ru.job4j.cars.controller;

import ru.job4j.cars.jsonserializer.JsonUtil;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/formselect")
public class FormSelectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                                            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        Map<String, Object> fieldsData = Cars.getInstance().getFormSelect();
        String jsonResponse = JsonUtil.GSON_AD.toJson(fieldsData);
        PrintWriter writer = resp.getWriter();
        writer.write(jsonResponse);
        writer.flush();
    }
}
