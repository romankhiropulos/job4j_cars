package ru.job4j.cars.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.entity.User;
import ru.job4j.cars.jsonserializer.JsonUtil;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/ad.do")
public class AdvertisementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        Map<String, Part> mapPart = Objects.requireNonNull((List<Part>) req.getParts()).stream()
                .collect(Collectors.toMap(Part::getName, part -> part));
        Advertisement ad = JsonUtil.GSON_AD.fromJson(
                new String(
                        Objects.requireNonNull(mapPart.get("adFields")).getInputStream().readAllBytes()
                ),
                Advertisement.class
        );

//        User curUser = (User) req.getSession().getAttribute("user");
        try {
            Cars.getInstance().saveAdvertisement(ad);

//        File file = new File(folder + File.separator + nameImg + ".png");

//        try (FileOutputStream out = new FileOutputStream(file)) {
//            out.write(item.getInputStream().readAllBytes());
//        }
//        } catch (FileUploadException exception) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException exception) {
            PrintWriter writer = resp.getWriter();
            writer.println("Data base problem");
            writer.flush();
        }
    }
}
