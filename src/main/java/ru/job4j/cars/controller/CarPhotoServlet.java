package ru.job4j.cars.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/carphoto")
public class CarPhotoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String resources = File.separator + "carphoto";
        String folderName;
        try {
            folderName = new File(
                    Thread.currentThread().getContextClassLoader().getResource(resources).toURI()
            ).getAbsolutePath();
            String nameKey = req.getParameter("namekey");
            File downloadFile = new File(folderName + File.separator + nameKey + ".png");
            if (!downloadFile.exists()) {
                downloadFile = new File(folderName +  File.separator + "notfound.png");
            }
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
            try (FileInputStream stream = new FileInputStream(downloadFile)) {
                resp.getOutputStream().write(stream.readAllBytes());
            } catch (IOException ioException) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (URISyntaxException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }
}
