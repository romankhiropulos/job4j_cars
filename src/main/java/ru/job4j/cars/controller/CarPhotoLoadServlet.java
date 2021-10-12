package ru.job4j.cars.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@WebServlet("/carphotoload")
public class CarPhotoLoadServlet extends HttpServlet {

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
                downloadFile = new File(folderName + File.separator + "notfound.png");
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

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        req.setCharacterEncoding("UTF-8");

        String nameImg = "10";
//                ad.getPhoto().getName();
//                    req.getParameter("name");

        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);

            String resources = File.separator + "carphoto";
            String folderName = new File(
                    Thread.currentThread()
                            .getContextClassLoader()
                            .getResource(resources)
                            .toURI()
            ).getAbsolutePath();

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folderName + File.separator + nameImg + ".png");
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
