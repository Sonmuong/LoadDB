package Controllers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.DAO;
import Models.*;

public class LoadDB extends HttpServlet {
    private DAO dao;
    
    @Override
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
            out.println("<title>Servlet LoadDB</title>");
        
            out.println("<script>");
            out.println("function confirmDelete(id) {");
            out.println("    if (confirm('Bạn có chắc chắn muốn xóa sinh viên này không?')) {");
            out.println("        window.location.href = 'Delete?id=' + id;");
            out.println("    }");
            out.println("}");
            
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadDB at " + request.getContextPath() + "</h1>");
            
            dao.loadStd();
            dao.loadDept();
            int index = 1;
            
            int id = -Integer.MIN_VALUE;
            String typeParam = request.getParameter("type");
            String idParam = request.getParameter("id");
            
            if (typeParam != null && typeParam.equals("0") && idParam != null) {
                try {
                    id = Integer.parseInt(idParam);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            Student stU = null;
            
            String tbl = "<table border='1'>";
            tbl += "<tr><th>ID</th><th>Name</th><th>Gender</th><th>Checkbox</th><th>Department</th><th>DOB</th><th>GPA</th><th>Action</th></tr>";
            
            for (Student st : dao.getStd()) {
                if (st.getId() == id) {
                    stU = st;
                }
                tbl += "<tr>";
                tbl += "<td id='td_id_" + index + "'>" + st.getId() + "</td>";
                tbl += "<td id='td_name_" + index + "'>" + st.getName() + "</td>";
                tbl += "<td id='td_gender_" + index + "'>" + 
                        (st.isGender() ? "Male" : "Female") + "</td>";
                tbl += "<td id='td_checkbox_" + index + "'><input type='checkbox' " + 
                        (st.isGender() ? "checked" : "") + "></td>";
                tbl += "<td id='td_dept_" + index + "'>" + dao.getDeptName(st.getDepartId()) + "</td>";
                tbl += "<td id='td_dob_" + index + "'>" + st.getDob() + "</td>";
                tbl += "<td id='td_gpa_" + index + "'>" + st.getGpa() + "</td>";
                tbl += "<td><a href='LoadDB?type=0&id=" + st.getId() + "'>Update</a> | ";
          
                tbl += "<a href='javascript:void(0);' onclick='confirmDelete(" + st.getId() + ")'>Delete</a></td>";
                tbl += "</tr>";
                index++;
            }
            
            tbl += "</table>";
            
            out.println(tbl);
            
            String frm = "<h2>" + (stU != null ? "Update Student" : "Insert New Student") + "</h2>";
            frm += "<form action=\"LoadDB\" method=\"post\">\n"
                    + "    Id: <input type='text' name='txtId' id='txtId' value='"
                    + (stU != null ? stU.getId() : "") + "' " 
                    + (stU != null ? "readonly" : "") + "/><br/>\n"
                    + "    Name: <input type='text' name='txtName' id='txtName' value='"
                    + (stU != null ? stU.getName() : "") + "'/><br/>\n"
//               
//                    + "    Gender (Select): <select name='cbxGender' id='cbxGender'>\n"
//                    + "        <option value='M'" + (stU != null && stU.isGender() ? " selected" : "") + ">Male</option>\n"
//                    + "        <option value='F'" + (stU != null && !stU.isGender() ? " selected" : "") + ">Female</option>\n"
                    + "    </select><br/>\n"
                    + "    Gender (Radio): "
                    + "<input type='radio' name='radGender' value='M'" + (stU != null && stU.isGender() ? " checked" : "") + "/> Male "
                    + "<input type='radio' name='radGender' value='F'" + (stU != null && !stU.isGender() ? " checked" : "") + "/> Female<br/>\n"
                    
                    + "    Department (Select): <select name='cbxDept' id='cbxDept'>\n";
            for (Department d : dao.getDept()) {
                frm += "        <option value='" + d.getId() + "'"
                        + (stU != null && stU.getDepartId().equals(d.getId()) ? " selected" : "")
                        + ">" + d.getName() + "</option>\n";
            }
            
//            frm += "    </select><br/>\n"
//                    + "    Department (Radio):<br/>\n";
//            
//            for (Department d : dao.getDept()) {
//                frm += "    <input type='radio' name='radDept' id='radDept" + d.getId() 
//                        + "' value='" + d.getId() + "'"
//                        + (stU != null && stU.getDepartId().equals(d.getId()) ? " checked" : "") 
//                        + "/> " + d.getName() + "<br/>\n";
//            }
            
            frm += "    Dob: <input type='date' name='datDob' id='datDob' value='"
                    + (stU != null ? stU.getDob() : "") + "'/><br/>\n"
                    + "    Gpa: <input type='text' name='txtGpa' id='txtGpa' value='"
                    + (stU != null ? stU.getGpa() : "") + "'/><br/>\n";
            
            if (stU != null) {
                frm += "    <input type='submit' name='btnUpdate' value='Update'/>\n";
                frm += "    <input type='button' value='Cancel' onclick=\"window.location.href='LoadDB'\"/>\n";
            } else {
                frm += "    <input type='submit' name='btnInsert' value='Insert'/>\n";
            }
            
            frm += "</form>";
            
            out.println(frm);
            out.println("<p>Status: " + dao.getStatus() + "</p>");
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
        
        try {
            dao.loadStd();
            
            int id = Integer.parseInt(request.getParameter("txtId"));
            String name = request.getParameter("txtName");

            String genderParam = request.getParameter("radGender");
            if (genderParam == null || genderParam.isEmpty()) {
                genderParam = request.getParameter("cbxGender");
            }
            boolean gender = genderParam.equals("M");

            String deptID = request.getParameter("radDept");
            if (deptID == null || deptID.isEmpty()) {
                deptID = request.getParameter("cbxDept");
            }
            
            String dob = request.getParameter("datDob");
            float gpa = Float.parseFloat(request.getParameter("txtGpa"));
            
            boolean exists = false;
            for (Student std : dao.getStd()) {
                if (std.getId() == id) {
                    exists = true;
                    break;
                }
            }
            
            if (o1 != null && !exists) {
                dao.Insert(id, name, gender, deptID, dob, gpa);
            }
            if (o2 != null && exists) {
                dao.Update(id, name, gender, deptID, dob, gpa);
            }
            
            response.sendRedirect(request.getContextPath() + "/LoadDB");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet to load and display Student data";
    }
}
// btvn ấn delete thì hiện ra hỏi có xoá hay không? 
// đối với update, thay thế gender thành combo bõ và radio box 
// áp dụng update thay thế department từ combobox chuyển sang radio box 
//