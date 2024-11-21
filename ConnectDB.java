package proyectoparcial2;
import java.sql.*;

public class ConnectDB {
    private static Connection conn=null;
    
    //constructor
    private ConnectDB(){
        String url="jdbc:mysql://localhost:3306/tallermantenimiento";
        String driver="com.mysql.cj.jdbc.Driver";
        String usuario="root";
        String password="";
        
        try{
            Class.forName(driver);
            conn=DriverManager.getConnection(url,usuario,password);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            
        }
    }//constructor
    
    //metodo conectar
    public static Connection getConnection(){
        if (conn==null){
            new ConnectDB();
        }
        return conn;
    }
    
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}//end class
