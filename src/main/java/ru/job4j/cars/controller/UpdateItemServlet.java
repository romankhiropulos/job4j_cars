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
import java.sql.SQLException;

import static java.util.Objects.requireNonNull;

@WebServlet("/adupdate.do")
public class UpdateItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        try {
            String adStr = req.getParameter("advertisement");
            Advertisement ad = JsonUtil.GSON_AD.fromJson(
                    requireNonNull(adStr, "Data of advertisement must not be null"),
                    Advertisement.class
            );
            Cars.getInstance().updateAdvertisement(ad);
        } catch (SQLException exception) {
            Cars.getLogger().error("SQL Exception: " + exception.getMessage(), exception);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (NullPointerException exception) {
            Cars.getLogger().error("NP Exception: " + exception.getMessage(), exception);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
        }
    }
}
