package dal;
import java.sql.*;
import java.util.List;
import java.util.Vector;
import Models.*;

public class DAO {
    private String status = "ok";
    private Connection con;
    private List<Student> std;
    private List<Department> dept;
    
    public DAO() {
        con = new DBContext().connection;
    }
    
    public void loadStd() {
        std = new Vector<>();
        String sql = "select * from Student";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student st = new Student();
                st.setId(rs.getInt("id"));
                st.setName(rs.getString("name"));
                st.setGender(rs.getBoolean("gender"));
                st.setDepartId(rs.getString("departId"));
                st.setDob(rs.getDate("dob"));
                st.setGpa(rs.getFloat("GPA"));
                std.add(st);
            }
        } catch (Exception e) {
            status = "error at read Student: " + e.getMessage();
        }
    }
    
    public void loadDept() {
        dept = new Vector<>();
        String sql = "select * from department";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dept.add(new Department(rs.getString("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            status = "error at read Department: " + e.getMessage();
        }
    }
    
    public String getDeptName(String id) {
        for (Department d : dept) {
            if (d.getId().equals(id)) return d.getName();
        }
        return null;
    }
    
    public void Insert(int id, String name, boolean gender, String deptID, String dob, float gpa) {
        String sql = "insert into Student values (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setBoolean(3, gender);
            ps.setString(4, deptID);
            ps.setString(5, dob);
            ps.setFloat(6, gpa);
            ps.execute();
        } catch(Exception e) {
            status = "Error at Insert: " + e.getMessage();
        }
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<Student> getStd() {
        return std;
    }
    
    public void setStd(List<Student> std) {
        this.std = std;
    }
    
    public List<Department> getDept() {
        return dept;
    }
    
    public void setDept(List<Department> dept) {
        this.dept = dept;
    }

    public void Update(int id, String name, boolean gender, String deptID, String dob, float gpa) {
           String sql = "update Student set id =?, name=?, gender=?, departID=?,dob=?,"
                   +"gpa=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setBoolean(3, gender);
            ps.setString(4, deptID);
            ps.setString(5, dob);
            ps.setFloat(6, gpa);
            ps.setInt(7,id);
            ps.execute();
        } catch(Exception e) {
            status = "Error at Update Student: " + e.getMessage();
        }
    }
}