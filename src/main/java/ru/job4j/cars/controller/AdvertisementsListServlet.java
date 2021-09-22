package ru.job4j.cars.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.cars.entity.*;
import ru.job4j.cars.jsonserializer.*;
import ru.job4j.cars.service.Cars;
import ru.job4j.cars.to.AdvertisementTo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ads")
public class AdvertisementsListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setHeader("cache-control", "no-cache");

//        - показать объявления за последний день
//        - показать объявления с фото
//        - показать объявления определенной марки.

        String listType = req.getParameter("mode");
        List<Advertisement> list = null;
        List<AdvertisementTo> adsTo = null;
        try {
//            if (Objects.equals(listType, "true") || Objects.equals(listType, "false")) {
//                list = (List<Advertisement>) Cars.getInstance().findAdBySold(Boolean.parseBoolean(listType));
//            } else {
                list = (List<Advertisement>) Cars.getInstance().getAllAdvertisements();
//                adsTo = list.stream().map(ad -> {
//                    AdvertisementTo adTo = new AdvertisementTo();
//                    adTo.setId(ad.getId());
//                    adTo.setCreated(ad.getCreated());
//                    adTo.setDescription(ad.getDescription());
//                    adTo.setCity(ad.getCity());
//                    adTo.setPhoto(ad.getPhoto());
//                    adTo.setPrice(ad.getPrice());
//                    adTo.setSold(ad.isSold());
//                    adTo.setCar(ad.getCar());
//                    adTo.setUserLogin(ad.getOwner().getLogin());
//                    return adTo;
//                }).collect(Collectors.toList());
//            }
        } catch (SQLException exception) {
            PrintWriter writer = resp.getWriter();
            writer.println("Sorry, server has a problem!");
            writer.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (list == null) {
            PrintWriter writer = resp.getWriter();
            writer.println("Sorry, items not found!");
            writer.flush();
        } else {
            String jsonResponse = JsonUtil.GSON_AD.toJson(list);
            PrintWriter writer = resp.getWriter();
            writer.write(jsonResponse);
            writer.flush();
        }
    }
}
