package dao;

import entity.Student;
import java.sql.*;

public class StudentService {
    static Connection con = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentp3", "root", "Shipra@218");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void register(Student s) {
        String query = "INSERT INTO studentp3.student_data(id, name, email, regdno) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, s.getId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setLong(4, s.getRegdno());

            ps.executeUpdate();
            System.out.println("Student Registered Successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}