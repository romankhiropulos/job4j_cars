package ru.job4j.cars.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/carphoto")
public class CarPhotoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String folderName = "carphoto" + File.separator;
        String nameKey = req.getParameter("namekey");
        File downloadFile = new File(folderName + nameKey + ".png");
//        for (File file : new File("webapp\\carphoto\\").listFiles()) {
//            if (key.equals(file.getName())) {
//                downloadFile = file;
//                break;
//            }
//        }
        if (!downloadFile.exists()) {
            downloadFile = new File(folderName + "notfound.png");
        }
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
        try (FileInputStream stream = new FileInputStream(downloadFile)) {
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}
