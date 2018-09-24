package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {

    public static void CreateTable(String CourseCode,String SubjectName) throws Exception {
        Connection con= null;
        String Table=CourseCode+"_"+SubjectName;

        con = getConnection();
        PreparedStatement insert=con.prepareStatement("CREATE TABLE "+Table+"(RegNo varchar (500),StdName varchar " +
                "(250),AnsString varchar (1000),correct int,incorrect int,untouched int,totalscore int)");

        insert.executeUpdate();


    }
    public static void InsertData(StdInstance instance,String CourseCode,String SubjectName) throws Exception {
        Connection con= null;
        String Table=CourseCode+"_"+SubjectName;

        con = getConnection();
        PreparedStatement insert=con.prepareStatement("INSERT INTO "+Table+"(RegNo,StdName" +
                ",AnsString,correct,incorrect,untouched,totalscore) VALUES ('"+instance.getRegId()+"','"+
                instance.getName()+"','"+instance.getAnsString()+"',"+instance.getCorrect()+","+instance.getIncorrect()
                +","+instance.getUntouched()+","+instance.getTotal()+")");

        insert.executeUpdate();


    }


    public static Connection getConnection() throws Exception{
        try {
            String driver="com.mysql.jdbc.Driver";
            String url="jdbc:mysql://localhost:3306/MCQ";
            String username="root";
            String password="";
            Class.forName(driver);
            Connection conn= DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
