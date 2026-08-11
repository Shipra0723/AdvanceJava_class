package controller;

import dao.StudentService;
import entity.Student;

//import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class StudentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id=Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        long regdno = Long.parseLong(req.getParameter("regdno"));

        Student student = new Student(id, name, email, regdno);

        //StudentService service = new StudentService();

        try {
            StudentService.register(student);
            resp.sendRedirect("register.html?msg=Success");
        } catch (Exception e) {
            System.out.print(e.getMessage());
            resp.getWriter().println("Registration Failed");
        }
    }
}