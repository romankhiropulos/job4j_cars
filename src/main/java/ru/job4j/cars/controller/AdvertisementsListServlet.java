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
import java.util.List;
import java.util.Objects;

@WebServlet("/ads")
public class AdvertisementsListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setHeader("cache-control", "no-cache");

        String filter = req.getParameter("filter");
        String strBrandId = req.getParameter("brandId");

        int brandId = Integer.parseInt(Objects.isNull(strBrandId) || "".equals(strBrandId) ? "0" : strBrandId);
        filter = "".equals(filter) ? null : filter;
        List<Advertisement> list = null;
        try {
            if (Objects.nonNull(filter) || brandId != 0) {
                list = (List<Advertisement>) Cars.getInstance().getAdsWithFilter(filter, brandId);
            } else {
                list = (List<Advertisement>) Cars.getInstance().getAllAdvertisements();
            }
            String jsonResponse = JsonUtil.GSON_AD.toJson(list);
            PrintWriter writer = resp.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        } catch (SQLException exception) {
            Cars.getLogger().error("SQL exception: " + exception.getMessage(), exception);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {
            Cars.getLogger().error(exception.getMessage(), exception);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
