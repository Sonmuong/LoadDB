package Controllers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.*;
import Models.*;

@WebServlet(name = "LoadDBJsp", urlPatterns = {"/LoadDBJsp"})
public class LoadDBJsp extends HttpServlet {
    private DAO dao;
    
    public void init(){
        dao = new DAO();
    }
    
    @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    Object o1 = request.getParameter("type");
    Object o2 = request.getParameter("id");

    if (o1 != null) {
        if ((o1 + "").equals("0")) {
            
            if (o2 != null)
                request.setAttribute("update", o2 + "");
            else
                request.removeAttribute("update");
        }

        if ((o1 + "").equals("1")) {
            try {
                int id = Integer.parseInt(o2 + "");
                dao.Delete(id);
            } catch (Exception e) {
            }
        }
    }
    dao.loadStd();
    dao.loadDept();
}
    
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
        doGet(request,response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
// thay thế gender bằng combobox và radiobox dùng jsp và servlet
// thay thế department bằng radiobox (nếu có thể thay thế bằng group check box, chú ý xử lý java script để chỉ có 1 depart được tích
// làm trên cả update và create