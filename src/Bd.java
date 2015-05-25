//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Bd {
    static Connection connection;
    static Statement stmt = null;
    String root;
    String pass;
    String serverName;
    String baseName;

    public Bd(String root, String pass, String serverName, String baseName) {
        this.root = root;
        this.pass = pass;
        this.serverName = serverName;
        this.baseName = baseName;
    }

    public void connect() {
        String driverName = "com.mysql.jdbc.Driver";

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException var7) {
            var7.printStackTrace();
        }

        String url = "jdbc:mysql://" + this.serverName + "/" + this.baseName;

        try {
            connection = DriverManager.getConnection(url, this.root, this.pass);
            stmt = connection.createStatement();
            System.out.println("MySQL connect");
        } catch (SQLException var6) {
            System.out.println("MySQL err connect " + var6);

            try {
                Thread.sleep(10000L);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }

            this.connect();
        }
    }

    public void save(String line) throws SQLException, ClassNotFoundException {
        if(connection == null || connection.isClosed()) {
            this.connect();
        }

        try {
            Statement e = stmt;
            synchronized(stmt) {
                //System.out.println(line);
                stmt.executeUpdate(line);
            }
        } catch (Exception var4) {
            System.out.println("Error save " + var4);
        }

    }

    public synchronized Map<String, String> getData(String imei) {
        HashMap map = new HashMap();

        try {
            if(connection == null || connection.isClosed()) {
                this.connect();
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        try {
            if(stmt.isClosed()){
                stmt = connection.createStatement();
            }
        }catch (SQLException e){
            System.out.println("Error reconnect stmt: "+e);
        }

        //   System.out.println("1111111");
        String query = "SELECT * FROM log WHERE imei = \'" + imei + "\' AND lat != \'null\' ORDER BY datetime DESC LIMIT 1";
        ResultSet resultSet;
        try {
            stmt.executeQuery(query);
            resultSet = stmt.executeQuery(query);
            while(resultSet.next()) {
                // System.out.println(resultSet.getString("lat"));
                map.put("lat", resultSet.getString("lat"));
                map.put("lng", resultSet.getString("lng"));
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
            System.out.println("Error select from log");
        }
//{012207005768384,211728,A,5023.266,N,03029.601,E,0.2,129,220315,5,00,F9,67,1,,,,,,,D5,,,80.981,M,3,,}
//{211634,V,,,,,0.7,138,220315,3,00,F9,67,1,,,,,,,D5,,,,M,3,,}

        if(map.size()==0){
            query = "SELECT * FROM loghistory WHERE imei = \'" + imei + "\' AND lat != \'null\' ORDER BY datetime DESC LIMIT 1";
            try {
                stmt.executeQuery(query);
                resultSet = stmt.executeQuery(query);
                while(resultSet.next()) {
                    System.out.println(resultSet.getString("lat"));
                    map.put("lat", resultSet.getString("lat"));
                    map.put("lng", resultSet.getString("lng"));
                }
            }catch (SQLException var5) {
                var5.printStackTrace();
                System.out.println("Error select from loghistory");
            }


        }
        return map;
    }
}
