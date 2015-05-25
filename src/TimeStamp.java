//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeStamp {
    public TimeStamp() {
    }

    public String getDateTime() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yy.MM.dd.HH.mm.ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String s = formatter.format(now);
        s = s.replace(".", "");
        return s;
    }

    public String getDateTimeNormal() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String s = formatter.format(now);
        return s;
    }
}
