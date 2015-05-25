//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
    String data;
    String imei;
    Bd bd;
/*

            (027043161231BR00150518A5024.9394N03031.1620E000.1 063804 215.8400000000L00000000)
            (027043161231BR00150518A5024.9403N03031.1620E001.0 063835 25.73000000000L00000000)
            (027043161231BR00150518A5024.9433N03031.1594E000.4 063905 17.69000000000L00000000)
            (027043161231BR00150518A5024.9438N03031.1601E000.4 063935 250.9600000000L00000000)
            (027043161231BR00150518A5024.9425N03031.1574E000.3 064005 216.7900000000L00000000)
            (027043161231BR 00 15 05 18 A 5024.9451 N 03031.1573 E001.0 064035 34.21000000000L00000000)
*/

    public Parser(Bd bd) {
        this.bd = bd;
    }

    public void first(String data) {
        this.data = data;
        List list = this.setList(data);
        String sourcedata = (String)list.get(0);
        String[] arr =  explode(sourcedata);
//(027043161231BR00150518A5024.9451N03031.1573E001.006403534.21000000000L00000000)
        this.imei = getParam(arr, 0, 12);
        HashMap map = new HashMap();
        map.put("time", getParam(arr,49,55 ));
        map.put("lat", getParam(arr,23,32));
        map.put("lng", getParam(arr,33,43));
        map.put("speed", getParam(arr,44,49));
        map.put("date", getParam(arr,16,22));
        map.put("azimuth", getParam(arr,55,60));
        map.put("sourcedata", sourcedata);
        convert(map);
    }

    public void next(String data) {
        first(data);
       /* List list = this.setList(data);



        while(!list.isEmpty()) {
            //System.out.println("list data: "+(String)list.remove(0));
            this.params((String)list.remove(0));
        }*/

    }

    private void params(String row) {
        String[] param = row.split(",");
        HashMap map = new HashMap();
        map.put("time", param[0]);
        map.put("veraciously", param[1]);
        map.put("lat", param[2]);
        map.put("lng", param[4]);
        map.put("speed", param[6]);
        map.put("azimuth", param[7]);
        map.put("date", param[8]);
        map.put("sputnik", param[9]);
        map.put("zaryad", param[12]);
        map.put("sourcedata", row);
        this.convert(map);
    }

    private List<String> setList(String data) {
        ArrayList allMatches = new ArrayList();
        Matcher m = Pattern.compile("[(][^}]+[)]").matcher(data);
        //System.out.println("list data: "+data));
        while(m.find()) {
            allMatches.add(m.group().replaceAll("[(]|[)]", ""));
        }

        return allMatches;
    }

    private String demic(String s) {
        if(s.isEmpty()) {
            System.out.println("Empty latLng");
            return null;
        } else {
            double d = Double.parseDouble(s);
            d /= 100.0D;
            int res = (int)d;
            double res2 = d - (double)res;
            res2 = res2 * 100.0D / 60.0D;
            d = (double)res + res2;
            double newDouble = (new BigDecimal(d)).setScale(6, RoundingMode.UP).doubleValue();
            s = Double.toString(newDouble);
            return s;
        }
    }
    //{012207005768384,211728,A,5023.266,N,03029.601,E,0.2,129,220315,5,00,F9,67,1,,,,,,,D5,,,80.981,M,3,,}
//{211634,V,,,,,0.7,138,220315,3,00,F9,67,1,,,,,,,D5,,,,M,3,,}
    private String datetime(String date, String time) {
        if(date != null && date.length() != 0) {
            if(time != null && time.length() != 0) {
                String[] aDate = date.split("");
                if(aDate[0].isEmpty()){
                    date = aDate[4+1] + aDate[5+1] + aDate[2+1] + aDate[3+1] + aDate[0+1] + aDate[1+1];
                }else{
                    date = aDate[4] + aDate[5] + aDate[2] + aDate[3] + aDate[0] + aDate[1];
                }

                if(time.indexOf(".") != -1) {
                    time = time.split("[.]")[0];
                }

                return date + time;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private String zaryadDec(String z) {
        int decimal = Integer.parseInt(z, 16);
        String s = "" + decimal;
        double d = Double.valueOf(s.trim()).doubleValue();
        d /= 10.0D;
        s = Double.toString(d);
        return s;
    }

    private String azimuth(String s) {
        return s != null && s.length() != 0?s:"0";
    }

    private int sputnik(String s) {
        int i = Integer.parseInt(s);
        return i;
    }

    private String speedKmh(String s) {
        double d = Double.valueOf(s.trim()).doubleValue();
        d *= 1.825D;
        s = Double.toString(d);
        return s;
    }

    private void convert(HashMap<String, String> map) {
       // String dateTime = this.datetime((String)map.get("date"), (String)map.get("time"));
        HashMap<String, String> map1 = new HashMap();
        String dateTime = map.get("date") +map.get("time");
        map1.put("dateTime", dateTime);
        String lat = demic(map.get("lat"));
        map1.put("lat", lat);
        String lng = demic(map.get("lng"));
        map1.put("lng", lng);
     //   String _zaryad = this.zaryadDec((String)map.get("zaryad"));
        //map.put("zaryad", _zaryad);
        String _speed = this.speedKmh(map.get("speed"));
        map1.put("speed", _speed);
        String _azimuth = this.azimuth((String)map.get("azimuth"));
        map1.put("azimuth", _azimuth);
        map1.put("sourcedata", map.get("sourcedata"));
        map1.put("sputnik", "n/a");
        map1.put("zaryad", "n/a");

        saveToDb(map1);
        for (HashMap.Entry<String, String> entry : map1.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key+": "+value);
        }

        //System.out.println("imei: " + this.imei + " lat: " + (String)map.get("lat") + " lng:  " + (String)map.get("lng") + " dateTime: " + (String)map.get("dateTime") + " zaryad: " + (String)map.get("zaryad") + " azimuth: " + (String)map.get("azimuth") + " speed:" + (String)map.get("speed") + " sputnik: " + (String)map.get("sputnik"));


        /*if(map.get("dateTime") != null && 3 < this.sputnik((String)map.get("sputnik")) && map.get("lat") != null && map.get("lng") != null && !(map.get("lat")).equals("null") && !((String)map.get("lng")).equals("null")) {
            this.saveToDb(map);
        } else {
            map.put("dateTime", (new TimeStamp()).getDateTime());
            Map _map = this.bd.getData(this.imei);
            System.out.println("No visible sattelits");
            map.put("lat", (String)_map.get("lat"));
            map.put("lng", (String)_map.get("lng"));
            map.put("speed", "0");
            map.put("sputnik", "0");
            this.saveToDb(map);
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String thing = entry.getValue();
            System.out.println(key+": "+thing);
        }*/

    }

    private void saveToDb(Map<String, String> map) {
        String _line = "INSERT INTO `log`(imei, lat, lng, datetime, speed, sputnik, azimuth,sourcedata, zaryad) VALUES(\'" + this.imei + "\'" + ",\'" + map.get("lat") + "\'" + ",\'" + map.get("lng") + "\'" + ",\'" + map.get("dateTime") + "\'" + ",\'" + map.get("speed") + "\'" + ",\'" + map.get("sputnik") + "\'" + ",\'" + (String)map.get("azimuth") + "\'" + ",\'" + (String)map.get("sourcedata") + "\'" + ",\'" + (String)map.get("zaryad") + "\'" + ")";

        try {
            this.bd.save(_line);
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }
    private String[] explode(String s) {

        char[] c = s.toCharArray();
        String[] sArray = new String[c.length];
        for (int i = 0; i < c.length; i++) {
            sArray[i] = String.valueOf(c[i]);
        }
        return sArray;
    }

    private String getParam(String [] arrStr, int start, int end){
        StringBuilder buildStr = new StringBuilder();

        for(int i = start; i<end; i++){
            buildStr.append(arrStr[i]);
        }
        return buildStr.toString();

    }
}
