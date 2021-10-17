package ru.job4j.cars.controller;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.jsonserializer.JsonUtil;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.nonNull;

@WebServlet("/ad.do")
@MultipartConfig
public class AdvertisementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String adFieldsStr;
        Map<String, Part> mapPart;
        Advertisement ad;
        Collection<Part> parts = req.getParts();
        try {
            adFieldsStr = req.getParameter("adFields");
            mapPart = requireNonNull((List<Part>) parts, "Parts of request must not be null").stream()
                   .collect(Collectors.toMap(Part::getName, part -> part));
            ad = JsonUtil.GSON_AD.fromJson(
                    requireNonNull(adFieldsStr, "Fields data of advertisement must not be null"),
                    Advertisement.class
            );
            Cars.getInstance().saveAdvertisement(ad);
        } catch (SQLException exception) {
            Cars.getLogger().error("SQL Exception: " + exception.getMessage(), exception);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
            return;
        } catch (NullPointerException exception) {
            Cars.getLogger().error("NP Exception: " + exception.getMessage(), exception);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
            return;
        }

        if (nonNull(mapPart.get("adPhoto"))) {
            try {
                String resources = File.separator + "carphoto";
                String folderName = new File(
                        Thread.currentThread().getContextClassLoader().getResource(resources).toURI()
                ).getAbsolutePath();
                File file = new File(folderName + File.separator + ad.getId() + ".png");
                try (FileOutputStream out = new FileOutputStream(file)) {
                    out.write(mapPart.get("adPhoto").getInputStream().readAllBytes());
                }
            } catch (URISyntaxException exception) {
                Cars.getLogger().error("URI Exception: " + exception.getMessage(), exception);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}
