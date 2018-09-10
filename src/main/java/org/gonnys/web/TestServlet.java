package org.gonnys.web;

import org.gonnys.dao.OrderDAO;
import org.gonnys.domain.OrderVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        //한글 쓰려면 이거 해줘야함
        request.setCharacterEncoding("UTF-8");
        OrderDAO dao = new OrderDAO();

        String[] values = new String[9];

        Enumeration<String>  names =  request.getParameterNames();


        while(names.hasMoreElements()){

            String name = names.nextElement();

            values = request.getParameterValues(name);

            System.out.println(Arrays.toString(values));

        }
        dao.add(values);


        // 영수증 출력

        String sql = "select mname, mct, mprice, mct*mprice total\n" +
                "from tbl_customer cus, tbl_menu menu\n" +
                "where cus.mno = menu.mno \n";

        List<OrderVO> list = new ArrayList<OrderVO>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            //sql 연결시키기
            Class.forName("oracle.jdbc.OracleDriver");
            //oracle 데이터베이스와 연결
            con = DriverManager.getConnection("jdbc:oracle:thin:@10.10.10.95:1521:XE","kk","12345678");

            //sql문 준비
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            //for문은 데이터 행을 한줄씩 빨아들이고 다음 행으로 넘어간다.
            //데이터 받아오는 코드
            while(rs.next()){
                System.out.println(".............next");
                OrderVO vo = new OrderVO(); //한 라인당 menuvo로 저장해준다.
                vo.setMname(rs.getString("mname"));
                vo.setMct(rs.getInt("mct"));
                vo.setMprice(rs.getInt("mprice"));
                vo.setTotal(rs.getInt("total"));
                list.add(vo); //리스트에 메뉴정보를 넣어준다.
            }
        }catch(Exception e){
            System.out.println(e.getMessage());

            e.printStackTrace();

        }finally {
            if(rs != null){     try{rs.close();}    catch (Exception e){} }
            if(stmt != null){   try{stmt.close();}  catch (Exception e){} }
            if(con != null){    try{con.close();}   catch (Exception e){} }
        }//end finally

        response.setContentType("text/html; charset=UTF-8");
        Writer out = response.getWriter();
        int total=0;
        out.write("<h1>영수증</h1>");
        out.write("<br>");
        out.write("메뉴"+"\t"+"수량"+"\t"+"가격");
        out.write("<br>");
        for(OrderVO vo: list){
            if(vo.getMct()==0){continue;}
            out.write(vo.getMname()+"\t"+vo.getMct()+"\t"+vo.getMprice());
            total += vo.getTotal();
            out.write("<br>");
        }
        out.write("<br>");
        out.write("------------------------------------------");
        out.write("<br>");
        out.write("총 가격: "+total);



















    }

}
