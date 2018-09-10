<%@ page import="org.gonnys.dao.MenuDAO" %>
<%@ page import="org.gonnys.domain.MenuVO" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: 5CLASS-184
  Date: 2018-09-10
  Time: 오후 2:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  MenuDAO dao = new MenuDAO();
  List<MenuVO> menuVOList = dao.getList();
%>


<html>
<head>
  <title>일본 음식 키오스크</title>
</head>
<body>
<div style="text-align:center;">
<form action="test" method="post">
<h1>★맛있는 일본 음식 고르기★</h1>
  <br>
  <% for(MenuVO vo : menuVOList){ %>
  <h2><%=vo.getMname()%></h2>

  <img src="img/<%=vo.getImg()%>" style="width: 300px; height: 300px;">

  <h3><%=vo.getPrice()%>원</h3>
  <label>주문수량</label>
  <select class="form-control" name="mct">
    <option value="<%=vo.getMno()%>_0" selected>0</option>
    <option value="<%=vo.getMno()%>_1">1</option>
    <option value="<%=vo.getMno()%>_2">2</option>
    <option value="<%=vo.getMno()%>_3">3</option>
    <option value="<%=vo.getMno()%>_4">4</option>
    <option value="<%=vo.getMno()%>_5">5</option>
  </select>
<br><br><br>
  <%}%>
<br>
  <button>주문하기</button>
</form>
</div>
</body>
</html>
