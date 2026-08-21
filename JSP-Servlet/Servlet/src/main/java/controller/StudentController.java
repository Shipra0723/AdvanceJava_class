package controller;

import dao.StudentService;
import entity.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/student")
public class StudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.sendRedirect("register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        long regdno = Long.parseLong(req.getParameter("regdno"));

        Student student = new Student(id, name, email, regdno);

        try {
            StudentService.register(student);
            resp.sendRedirect("register.html?msg=success");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("register.html?msg=fail");
        }
    }
}