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
                    <th>Department Name</th>
                    <th>DOB</th>
                    <th>GPA</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${dl.std}" var="st" varStatus="status">
                    <tr>
                        <td>${st.id}</td>
                        <td>${st.name}</td>
                        <td>${st.gender ? "Male" : "Female"}</td>
                        <td>${dl.getDeptName(st.departId)}</td>
                        <td>${st.dob}</td>
                        <td>${st.gpa}</td>
                        <td>
                            <a href="LoadDBJsp?type=0&id=${st.id}">Update</a> |
                            <a href="LoadDBJsp?type=1&id=${st.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <h2>${stU != null ? "Update Student" : "Add New Student"}</h2>
        <form action="LoadDBJsp" method="post">
            Id: <input type="text" name="txtId" id="txtid" 
                       value="${stU != null ? stU.id : ""}" 
                       ${stU != null ? "readonly" : ""} /><br/>

            Name: <input type="text" name="txtName" id="txtname"
                         value="${stU != null ? stU.name : ""}" /><br/>

            Gender: <input type="checkbox" name="chkGender" id="chkgender" 
                           ${stU != null && stU.gender ? "checked" : ""} /> Male<br/>

            Department:
            <select name="sltDept" id="sltdept">
                <option value="">-- Chọn Department --</option>
                <c:forEach items="${dl.depts}" var="dept">
                    <option value="${dept.id}" 
                            ${stU != null && stU.departId == dept.id ? "selected" : ""}>
                        ${dept.name}
                    </option>
                </c:forEach>
            </select><br/>

            DOB: <input type="date" name="txtDob" id="txtdob"
                        value="${stU != null ? stU.dob : ""}" /><br/>

            GPA: <input type="text" name="txtGpa" id="txtgpa" 
                        value="${stU != null ? stU.gpa : ""}" /><br/>

            <input type="submit" name="${stU != null ? 'btnUpdate' : 'btnInsert'}" 
                   value="${stU != null ? 'Update' : 'Insert'}" />
        </form>
    </body>
</html>