<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Student Information</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>Department ID</th>
                    <th>DOB</th>
                    <th>GPA</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${dl.std}" var="st" varStatus="idx">
              
                    <tr>
                        <td id="td_id_${idx.index +1}">${st.id}</td>
                        <td id="td_name_${idx.index +1}">${st.name}</td>
                        <td id="td_gender_${idx.index +1}">${st.gender ? "Male" : "Female"}</td>
                        <td id="td_departId_${idx.index +1}">${st.departId}</td>
                        <td id="td_dob_${idx.index +1}">${st.dob}</td>
                        <td id="td_gpa_${idx.index +1}">${st.gpa}</td>
                        <td> <a id='Update_$(st.id)' href='LoadDBJsp?type=0&id=${st.id}'> update</a></td>
                        <td> <a id='Delete_$(st.id)' href='LoadDBJsp?type=0&id=${st.id}'> delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>