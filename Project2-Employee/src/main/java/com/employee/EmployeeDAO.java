package com.employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO {
    Connection con = DBConnection.getConnection();

    public void addEmployee(Employee emp) {

        String sql = "INSERT INTO employee VALUES(?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, emp.getId());
            ps.setString(2, emp.getName());
            ps.setString(3, emp.getDepartment());
            ps.setDouble(4, emp.getSalary());

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Employee Added Successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewEmployees() {

        String sql = "SELECT * FROM employee";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("-------------------------");
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("Department : " + rs.getString("department"));
                System.out.println("Salary : " + rs.getDouble("salary"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void searchEmployee(int id) {

        String sql = "SELECT * FROM employee WHERE id=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("Department : " + rs.getString("department"));
                System.out.println("Salary : " + rs.getDouble("salary"));
            } else {
                System.out.println("Employee Not Found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public void updateEmployee(Employee emp) {

        String sql = "UPDATE employee SET name=?, department=?, salary=? WHERE id=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, emp.getName());
            ps.setString(2, emp.getDepartment());
            ps.setDouble(3, emp.getSalary());
            ps.setInt(4, emp.getId());

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Employee Updated Successfully!");
            } else {
                System.out.println("Employee Not Found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void deleteEmployee(int id) {

        String sql = "DELETE FROM employee WHERE id=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Employee Deleted Successfully!");
            } else {
                System.out.println("Employee Not Found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}