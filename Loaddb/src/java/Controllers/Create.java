package Controllers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Models.*;
import java.util.Vector;
import java.util.List;
import java.sql.*;
import dal.*;

public class Create extends HttpServlet {
    private DAO dao;
    
    public void init() {
        dao = new DAO();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Create</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Create at " + request.getContextPath() + "</h1>");
            dao.loadDept();
            
            
            String frm = "<form action=\"Create\" method=\"post\">\n"
                    + "Id:<input type='text' name='txtId' id='txtId'/> <br/>\n"
                    + "Name: <input type='text' name='txtName' id='txtName'/> <br/>\n"
                    
                    // Gender từ checkbox -> combobox (dropdown)
                    + "Gender: <select name='cbxGender' id='cbxGender'>\n"
                    + "    <option value='M'>Male</option>\n"
                    + "    <option value='F'>Female</option>\n"
                    + "</select><br/>\n"
                    
                    // Gender từ checkbox -> radio button
                    // + "Gender: \n"
                    // + "<input type='radio' name='radGender' id='radMale' value='M' checked/> Male\n"
                    // + "<input type='radio' name='radGender' id='radFemale' value='F'/> Female<br/>\n"
                    
                    // Department từ combobox -> radio button
                    + "Department:<br/>\n";
            
            for (Department d : dao.getDept()) {
                frm += "<input type='radio' name='radDept' id='radDept" + d.getId() 
                        + "' value='" + d.getId() + "'/> " + d.getName() + "<br/>\n";
            }
            
            // NẾU MUỐN GIỮ COMBOBOX CHO DEPARTMENT (code gốc):
            // frm += "Department:<select name='cbxDept' id='cbxDept'>";
            // for (Department d : dao.getDept()) {
            //     frm += "<option value='" + d.getId() + "'>" + d.getName();
            //     frm += "</option>";
            // }
            // frm += "</select><br/>\n"
            
            frm += "Dob: <input type='date' name='datDob' id='datDob' value=''/><br/>\n"
                    + "Gpa: <input type='text' name='txtGpa' id='txtGpa' value=''/><br/>\n"
                    + "<input type='submit' name='btnInsert' value='Insert'/>\n"
                    + "</form>";
            
            out.print(frm);
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object o1 = request.getParameter("btnInsert");
        Object o2 = request.getParameter("btnUpdate");
        boolean update = false;
        try {
            int id = Integer.parseInt(request.getParameter("txtId"));
            String name = request.getParameter("txtName");

            boolean gender = request.getParameter("cbxGender").equals("M");
            String deptID = request.getParameter("radDept");

            String dob = request.getParameter("datDob");
            float gpa = Float.parseFloat(request.getParameter("txtGpa"));
            for (Student std : dao.getStd()){
                if (std.getId()==id)update = true; 
            }
            if(o1!=null &&!update)
                dao.Insert(id, name, gender, deptID, dob, gpa);
           if(o2!=null &&update)
                dao.Update(id,name,gender,deptID,dob,gpa);
    
            response.sendRedirect(request.getContextPath() + "/LoadDB");
        } catch(Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

//btvn tuong tu thay the gender tu checkbox-> combobox va radio
//tay the department tu combobox-> radiobox
// Viet gop create vao loadDB