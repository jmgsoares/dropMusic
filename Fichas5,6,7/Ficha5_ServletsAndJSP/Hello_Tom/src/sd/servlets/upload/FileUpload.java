package sd.servlets.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// The MultipartConfig annotation configures everything required for file uploads

@SuppressWarnings("serial")


@MultipartConfig(location = "/Users/alcides/Desktop/upload", maxFileSize = 10485760L) // 10MB.

@WebServlet(urlPatterns = { "/upload/FileUpload" })
public class FileUpload extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("/upload/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        MultipartMap map = new MultipartMap(request, this); // Processes form files
        String text = map.getParameter("name");
        File file = map.getFile("file"); // Returns the filename after successful upload.

        // Now do your thing with the obtained input.
        System.out.println("Text: " + text);
        System.out.println("File: " + file);

        request.getRequestDispatcher("/upload/upload_ok.jsp").forward(request, response);
    }

}