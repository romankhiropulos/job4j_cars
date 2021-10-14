package ru.job4j.cars.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.cars.entity.Advertisement;
import ru.job4j.cars.entity.User;
import ru.job4j.cars.jsonserializer.JsonUtil;
import ru.job4j.cars.service.Cars;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/ad.do")
@MultipartConfig
public class AdvertisementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
//        if (isMultipart) {
//            // Create a factory for disk-based file items
//            FileItemFactory factory = new DiskFileItemFactory();
//
//            // Create a new file upload handler
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            try {
//                // Parse the request
//                List items = upload.parseRequest(req);
//                Iterator iterator = items.iterator();
//                while (iterator.hasNext()) {
//                    FileItem item = (FileItem) iterator.next();
//                    if (!item.isFormField()) {
//                        String fileName = item.getName();
//                        String root = getServletContext().getRealPath("/");
//                        File path = new File(root + "../../web/Images/uploads");
//                        if (!path.exists()) {
//                            boolean status = path.mkdirs();
//                        }
//
//                        File uploadedFile = new File(path + "/" + fileName);
//                        System.out.println(uploadedFile.getAbsolutePath());
//                        item.write(uploadedFile);
//                    }
//                }
//            } catch (FileUploadException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        --------------------------

        String asStr = req.getParameter("adFields");
        Collection<Part> parts = req.getParts();
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

            String resources = File.separator + "carphoto";
            String folderName;

            folderName = new File(
                    Thread.currentThread().getContextClassLoader().getResource(resources).toURI()
            ).getAbsolutePath();
            File file = new File(folderName + File.separator + ad.getId() + ".png");

            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(mapPart.get("adPhoto").getInputStream().readAllBytes());
            }
        } catch (URISyntaxException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (SQLException exception) {
            PrintWriter writer = resp.getWriter();
            writer.println("Data base problem");
            writer.flush();
        }
    }
}
