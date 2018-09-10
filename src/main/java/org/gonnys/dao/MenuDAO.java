package org.gonnys.dao;

import org.gonnys.domain.MenuVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public List<MenuVO> getList() throws Exception{

        String sql = "select * from tbl_menu";
        List<MenuVO> list = new ArrayList<MenuVO>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            //sql 연결시키기
            Class.forName("oracle.jdbc.OracleDriver");
            //oracle 데이터베이스와 연결
            con = DriverManager.getConnection("jdbc:oracle:thin:@10.10.10.95:1521:XE","kk","12345678");

            System.out.println("1-------------------------"+con);
            //sql문 준비
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            //for문은 데이터 행을 한줄씩 빨아들이고 다음 행으로 넘어간다.
            //데이터 받아오는 코드
            while(rs.next()){
                System.out.println(".............next");
                MenuVO vo = new MenuVO(); //한 라인당 menuvo로 저장해준다.
                vo.setMno(rs.getInt("mno"));
                vo.setMname(rs.getString("mname"));
                vo.setPrice(rs.getInt("mprice"));
                vo.setImg(rs.getString("img"));
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



        return list;
    }


}

