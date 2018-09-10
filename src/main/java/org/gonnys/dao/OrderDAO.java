package org.gonnys.dao;

import org.gonnys.domain.OrderVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;

public class OrderDAO {

    public int getOrderNum(){
        final String sql ="select seq_order.nextval from dual";

        final StringBuilder result = new StringBuilder();

        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(sql);

                rs = stmt.executeQuery();
                while(rs.next()){
                    result.append(rs.getInt(1));
                }

            }
        }.executeAll();
        return Integer.parseInt(result.toString());
    }


    public int  add(String[] str) {

        int orderNum = getOrderNum();
        OrderVO orderVO = new OrderVO();

        for(int i=0; i<str.length;i++) {
            String[] str2 = str[i].split("_");
            Connection con = null;
            PreparedStatement stmt = null;
            String sql = "insert into tbl_customer(cno,mno,mct) values (?,?,?)";

            if(str2[1].equals("0")){
                continue;
            }

            System.out.println("str:" + Arrays.toString(str));


            try {

                //sql 연결시키기
                Class.forName("oracle.jdbc.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@10.10.10.95:1521:XE", "kk", "12345678");
                System.out.println(con);
                stmt = con.prepareStatement(sql);



                System.out.println("str2:" + Arrays.toString(str2));
                //mid,mno,score //여기서는 0이 아니라 1부터 시작한다.
                stmt.setInt(1, orderNum);
                stmt.setInt(2, Integer.parseInt(str2[0]));
                stmt.setInt(3, Integer.parseInt(str2[1]));

                //업데이트 실행
                int count = stmt.executeUpdate();
                System.out.println(count);

            } catch (Exception e) {
                System.out.println(e.getMessage());

            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Exception e) {
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                    }
                }
            }//end finally
        }
        return orderNum;
    }//end method

}
