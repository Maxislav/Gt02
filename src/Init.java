

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

//import main.Bd;
//import main.Server;

public class Init {
    public Init() {
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String root = null;
        String url = null;
        String table = null;
        String pass = null;
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("config.txt"));
            JSONObject bd = (JSONObject)obj;
            root = (String)bd.get("root");
            url = (String)bd.get("url");
            table = (String)bd.get("table");
            pass = (String)bd.get("pass");
        } catch (ParseException var8) {
            var8.printStackTrace();
        }
            System.out.println(url);

        System.out.println("Version 28.05.15. Pareser Gt02");
        Bd bd1 = new Bd(root, pass, url, table);
        bd1.connect();
        new Server(3128, bd1);
    }
}
