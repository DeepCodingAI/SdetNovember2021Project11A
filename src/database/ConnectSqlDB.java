package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectSqlDB {
    public static Connection connect;
    public static PreparedStatement ps;

    public static Properties loadProperties()throws IOException{
        Properties properties = new Properties();
        InputStream ism = new FileInputStream("src/secret.properties");
        properties.load(ism);
        ism.close();

        return properties;
    }

    public static Connection connectDB() throws IOException, ClassNotFoundException, SQLException {
        Properties prop = loadProperties();
        String driverClass = prop.getProperty("MYSQLJDBC.driver");
        String url = prop.getProperty("MYSQLJDBC.url");
        String userName = prop.getProperty("MYSQLJDBC.userName");
        String password = prop.getProperty("MYSQLJDBC.password");
        Class.forName(driverClass);
        connect = DriverManager.getConnection(url,userName,password);

        return connect;

    }


    public static List<Student> readStudentProfile(){
        List<Student> list = new ArrayList<>();
        Student student = null;
        try{
           Connection connection = connectDB();
           String query = "Select *From Students";
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(query);
           while (resultSet.next()){
                String stName = resultSet.getString("stName");
                String stID = resultSet.getString("stID");
                String stDOB = resultSet.getString("stDOB");
                student = new Student(stName,stID,stDOB);
                list.add(student);
           }
        }catch(Exception ex){

        }
        return list;
    }

    public static void insertProfileIntoSqlTable(Student student, String tableName,
                                                 String columnName1, String columnName2, String columnName3){
        try{
            connect = connectDB();
            ps = connect.prepareStatement("Insert Into "+tableName+"("+columnName1+","+columnName2+","+columnName3+
                    ") Values(?,?,?)");
            ps.setString(1,student.getStName());
            ps.setString(2,student.getStID());
            ps.setString(3,student.getStDOB());
            ps.execute();

        }catch (IOException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //insertProfileIntoSqlTable(new Student("Mike","4521","05-04-2001"),"Students",
        //        "stName","stID","stDOB");
        List<Student> list = readStudentProfile();
        for(Student st:list){
            System.out.println(st.getStName()+ " "+st.getStID()+ " "+st.getStDOB());
        }
    }
}
