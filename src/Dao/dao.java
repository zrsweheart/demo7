package Dao;
import Bean.*;
import DButil.dbutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dao {

//查询所有老年人信息
    public List<old> searchAll( ){
        List<old> list = new ArrayList<old>();
        try {
            Connection conn = dbutil.getConn();
            Statement state = null;
            String sql="select * from old";
            PreparedStatement  pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("搜索全部老年人");
            while(rs.next()){
                old lu = new old();

                lu.setId(Integer.parseInt(rs.getString("id")));
                lu.setName(rs.getString("name"));
                lu.setSfid(rs.getString("sfid"));
                lu.setSex(rs.getString("sex"));
                lu.setCishu(Integer.parseInt(rs.getString("cishu")));

                list.add(lu);

            }
            rs.close();
            pstmt.close();
            conn.close();
        }catch(SQLException e) {

            System.out.println("searchAll发生错误");
            e.printStackTrace();


        }


        return list;

    }

//条件查询
    public List<old> search(int birth1,int birth2,String jigou){
    List<old> list = new ArrayList<old>();
    try {
        Connection conn = dbutil.getConn();
        Statement state = null;
        String sql="select * from old where jigou =? and birth >=? and birth <=?";
        PreparedStatement  pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,jigou);
        pstmt.setInt(2,birth1);
        pstmt.setInt(3,birth2);
        ResultSet rs = pstmt.executeQuery();
        System.out.println("搜索全部老年人");
        while(rs.next()){
            old lu = new old();

            lu.setId(Integer.parseInt(rs.getString("id")));
            lu.setName(rs.getString("name"));
            lu.setSfid(rs.getString("sfid"));
            lu.setSex(rs.getString("sex"));
            lu.setCishu(Integer.parseInt(rs.getString("cishu")));

            list.add(lu);

        }
        rs.close();
        pstmt.close();
        conn.close();
    }catch(SQLException e) {

        System.out.println("searchAll发生错误");
        e.printStackTrace();


    }


    return list;

}
//查询单个老年人的所有评估记录
    public List<old> searchOne( int id ){
        List<old> list = new ArrayList<old>();
        try {
            Connection conn = dbutil.getConn();
            Statement state = null;
            String sql="select * from yipinggu where id="+id;
            PreparedStatement  pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("搜索单个老年人");
            while(rs.next()){
                old lu = new old();

                lu.setSfid(rs.getString("sfid"));
                lu.setPici(Integer.parseInt(rs.getString("pici")));
                lu.setFenshu(Integer.parseInt(rs.getString("fenshu")));
                lu.setTest1(rs.getString("test1"));
                lu.setTest2(rs.getString("test2"));
                lu.setTest3(rs.getString("test3"));
                lu.setTest4(rs.getString("test4"));
                lu.setTest5(rs.getString("test5"));

                list.add(lu);

            }
            rs.close();
            pstmt.close();
            conn.close();
        }catch(SQLException e) {

            System.out.println("searchOne发生错误");
            e.printStackTrace();


        }


        return list;

    }

//查询所有批次信息
    public List<pici> searchPici( ){
    List<pici> list = new ArrayList<pici>();
    try {
        Connection conn = dbutil.getConn();
        Statement state = null;
        String sql="select * from pici ";
        PreparedStatement  pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        System.out.println("搜索所有批次");
        while(rs.next()){
            pici lu = new pici();
            lu.setPici(rs.getInt("pici"));
            lu.setTime(rs.getString("time"));
            lu.setYiping(rs.getInt("yiping"));
            lu.setDaiping(rs.getInt("daiping"));

            list.add(lu);

        }
        rs.close();
        pstmt.close();
        conn.close();
    }catch(SQLException e) {

        System.out.println("searchPici发生错误");
        e.printStackTrace();


    }


    return list;

}

//添加老年人信息
    public static boolean add(String name,String sex ,String address,String phone,String sfid,int cishu,String birth)
    {
        boolean flag=false;
        try {
            PreparedStatement preparedStatement=null;
            Connection connect = dbutil.getConn();
            preparedStatement=connect.prepareStatement("insert into old (name,sex,address,phone,sfid,cishu,birth) values (?,?,?,?,?,?,?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,sex);
            preparedStatement.setString(3,address);
            preparedStatement.setString(4,phone);
            preparedStatement.setString(5,sfid);
            preparedStatement.setInt(6,cishu);
            preparedStatement.setString(7,birth);
            preparedStatement.executeUpdate();
            connect.close();
            preparedStatement.close();
            flag=true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

//创建批次，添加进待评估
    public static void insert_daipinggu(String id, int pici )
    {

        try {
            PreparedStatement preparedStatement=null;
            Connection connect = dbutil.getConn();
            preparedStatement=connect.prepareStatement("insert into daipinggu (id,pici) values (?,?)");
            preparedStatement.setString(1,id);
            preparedStatement.setInt(2,pici);

            preparedStatement.executeUpdate();
            connect.close();
            preparedStatement.close();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

//创建批次，添加批次信息
    public static void insert_pici ( int pici,String time,int yiping,int daiping )
{

    try {
        PreparedStatement preparedStatement=null;
        Connection connect = dbutil.getConn();
        preparedStatement=connect.prepareStatement("insert into pici (pici,time,yiping,daiping) values (?,?,?,?)");
        preparedStatement.setInt(1,pici);
        preparedStatement.setString(2,time);
        preparedStatement.setInt(3,yiping);
        preparedStatement.setInt(4,daiping);
        preparedStatement.executeUpdate();
        connect.close();
        preparedStatement.close();

    }catch(SQLException e){
        e.printStackTrace();
    }

}

//添加单个人员具体评估内容
    public static void insert_pinggu(int id,int pici,String sfid,int fenshu,String test1,String test2,String test3,String test4,String test5)
    {

        try {
            PreparedStatement preparedStatement=null;
            Connection connect = dbutil.getConn();
            preparedStatement=connect.prepareStatement("insert into yipinggu (id,pici,sfid,fenshu,test1,test2,test3,test4,test5) values (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,pici);
            preparedStatement.setString(3,sfid);
            preparedStatement.setInt(4,fenshu);
            preparedStatement.setString(5,test1);
            preparedStatement.setString(6,test2);
            preparedStatement.setString(7,test3);
            preparedStatement.setString(8,test4);
            preparedStatement.setString(9,test5);

            preparedStatement.executeUpdate();
            connect.close();
            preparedStatement.close();

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

//删除待评估中已评估人员
    public static void delete(String  id,int pici) {
        boolean flag = false;
        Connection conn = dbutil.getConn();
        Statement state = null;

        try {
            String sql = "delete from daipinggu where id = '"+id+"' and pici ="+pici;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            int i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            if(i>0) flag = true;
        } catch (SQLException e) {
            System.out.println("删除失败！");
            e.printStackTrace();
        }

    }

//查询所有已评估人员
   public List<old> search2(int pici){
        List<old> list = new ArrayList<old>();
        try {
            Connection conn = dbutil.getConn();
            Statement state = null;



            String sql = "select * from yipinggu where pici="+pici;

            PreparedStatement  pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            System.out.println("已评估搜索运行中");
            while(rs.next()){

                old lu = new old();

                lu.setId(Integer.parseInt(rs.getString("id")));

                list.add(lu);

            }
            rs.close();
            pstmt.close();
            conn.close();
        }catch(SQLException e) {

            System.out.println("发生错误");
            e.printStackTrace();


        }


        return list;

    }

    public List<old> search21(int pici){
        List<old> list = new ArrayList<old>();
        try {
            Connection conn = dbutil.getConn();
            Statement state = null;



            String sql = "select * from yipinggu where pici="+pici;

            PreparedStatement  pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            System.out.println("已评估搜索运行中");
            while(rs.next()){

                old lu = new old();

                lu.setId(Integer.parseInt(rs.getString("id")));

                list.add(lu);

            }
            rs.close();
            pstmt.close();
            conn.close();
        }catch(SQLException e) {

            System.out.println("发生错误");
            e.printStackTrace();


        }


        return list;

    }

//查询所有未评估人员
    public List<old> search3(int pici){
        List<old> list = new ArrayList<old>();
        try {
            Connection conn = dbutil.getConn();
            Statement state = null;

            String sql = "select * from daipinggu where pici="+pici;

            PreparedStatement  pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            System.out.println("未评估搜索运行中");
            while(rs.next()){

                old lu = new old();

                lu.setId(Integer.parseInt(rs.getString("id")));

                list.add(lu);

            }
            rs.close();
            pstmt.close();
            conn.close();
        }catch(SQLException e) {

            System.out.println("发生错误");
            e.printStackTrace();


        }


        return list;

    }

//获取单个人员信息
    public static old getOne(int id){
        old lu = new old();
        try {
            Connection conn = dbutil.getConn();
            Statement state = null;
            String sql="select * from old where id=?";
            PreparedStatement  pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){

                lu.setId(Integer.parseInt(rs.getString("id")));
                lu.setName(rs.getString("name"));
                lu.setSfid(rs.getString("sfid"));
                lu.setSex(rs.getString("sex"));
                lu.setAddress(rs.getString("address"));
                lu.setPhone(rs.getString("phone"));
                lu.setBirth(rs.getInt("birth"));
                lu.setCishu(rs.getInt("cishu"));


            }
            rs.close();
            pstmt.close();
            conn.close();
        }
        catch(SQLException e) {

            e.printStackTrace();
        }
        return lu;

    }

//获取单个报告
    public static old getBaogao(int id,int pici){
    old lu = new old();
    try {
        Connection conn = dbutil.getConn();
        Statement state = null;
        String sql="select * from yipinggu where  id = "+id+" and pici ="+pici;
        PreparedStatement  pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){

            lu.setId(id);
            lu.setPici(pici);
            lu.setFenshu(Integer.parseInt(rs.getString("fenshu")));
            lu.setSfid(rs.getString("sfid"));
            lu.setTest1(rs.getString("test1"));
            lu.setTest2(rs.getString("test2"));
            lu.setTest3(rs.getString("test3"));
            lu.setTest4(rs.getString("test4"));
            lu.setTest5(rs.getString("test5"));


        }
        rs.close();
        pstmt.close();
        conn.close();
    }
    catch(SQLException e) {

        e.printStackTrace();
    }
    return lu;

}

//获取单个批次信息
    public static pici getPici(int pici){
    pici lu = new pici();
    try {
        Connection conn = dbutil.getConn();
        Statement state = null;
        String sql="select * from pici where pici=?";
        PreparedStatement  pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,pici);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            lu.setTime(rs.getString("time"));
            lu.setYiping(rs.getInt("yiping"));
            lu.setDaiping(rs.getInt("daiping"));
        }
        rs.close();
        pstmt.close();
        conn.close();
    }
    catch(SQLException e) {

        e.printStackTrace();
    }
    return lu;

}

//更新总表评估次数
    public static void update(int id,int cishu) {
        try {
            PreparedStatement preparedStatement = null;
            Connection connect = dbutil.getConn();
            preparedStatement = connect.prepareStatement("Update old set cishu=? where id=?");
            preparedStatement.setInt(1, cishu);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
            preparedStatement.execute();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//更新批次表已评估和未评估信息
    public static void update2(int yiping,int daiping,int pici) {
    try {
        PreparedStatement preparedStatement = null;
        Connection connect = dbutil.getConn();
        preparedStatement = connect.prepareStatement("Update pici set yiping=? , daiping = ? where pici=?");
        preparedStatement.setInt(1, yiping);
        preparedStatement.setInt(2, daiping);
        preparedStatement.setInt(3, pici);
        preparedStatement.executeUpdate();
        preparedStatement.execute();
        connect.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//日常生活活动评估换算等级
    public static int judge1(int a){
    int m=0;
    if(a==100){
        m=0;
    }
    else if(a>=65&&a<=95){
        m=1;
    }
    else if(a>=45&&a<=60){
        m=2;
    }
    else if(a<=40){
        m=3;
    }
    return m;
}

//精神状态评估换算等级
    public static int judge2(int a){
    int m=0;
    if(a==0){
        m=0;
    }
    else if(a==1){
        m=1;
    }
    else if(a>=2&&a<=3){
        m=2;
    }
    else if(a>=4&&a<=6){
        m=3;
    }
    return m;
}

//感知觉与沟通评估换算等级
    public static int judge3(int a,int b,int c,int d){
     int m=0;
    switch (a) {
        case 0:
            if((b==0||b==1)&&(c==0||c==1)&&d==0)
            { m=0;}
            else if((b==2||c==2)||d==1)
            {m=1;}
            else if((b==3||c==3)||d==2)
            {m=2;}
            else if((b==4||c==4)||d==3)
            {m=3;}
            break;
        case 1:
            if((b==3||b==4)&&(c==3||c==4)&&(d==2||d==3))
            { m=2;}
            else if((b==4||c==4)||d==3)
            {m=3;}
            break;
        case 2:
            m=3;
            break;
        case 3:
           m=3;
            break;
    }
    return m;
}

//社会参与评估换算等级
    public static int judge4(int a){
    int m=0;
    if(a>=0&&a<=2){
        m=0;
    }
    else if(a>=3&&a<=7){
        m=1;
    }
    else if(a>=8&&a<=13){
        m=2;
    }
    else if(a>=14&&a<=20){
        m=3;
    }
    return m;
}

//总换算等级
    public static int judge(int a,int b,int c,int d){
        int m=-1;
        switch (a) {
            case 0:
                if(b==0&&c==0&&(d==0||d==1))
                { m=0;}
                else
                {m=1;}
                break;
            case 1:
                if((b==2&&c==2&&d==2)||d==3||b==3||c==3)
                {m=2;}
                else
                {m=1;
                }
                break;
            case 2:
                if(b==3||c==3||d==3){
                    m=3;
                }
                else if(b==2&&c==2&&d==2){
                    m=3;
                }
                else {
                    m=2;
                }
                break;
            case 3:
                m=3;
                break;
        }
        return m;
    }
}

