//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class LogTxt {
    PrintWriter out;
    TimeStamp time = new TimeStamp();

    public LogTxt() throws IOException {
    }

    public void save(String txt) throws IOException {
        this.out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
        String t = this.time.getDateTimeNormal();
        this.out.println(t + "  " + txt + "\r\n" + "\r\n ");
        this.out.close();
    }
}
