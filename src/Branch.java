

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Branch extends Thread {
    static Bd bd;
    int connection;
    int mes;
    Socket s;
    InputStream is;
    OutputStream os;
    Parser parser;
    LogTxt log;
    final String res = "J";

    public Branch(int i, Socket s, Bd bd, LogTxt log) {
        bd = bd;
        this.parser = new Parser(bd);
        this.s = s;
        this.log = log;

        try {
            s.setSoTimeout(900000);
        } catch (SocketException var9) {
            System.out.println("Error s timeout: " + var9);
            return;
        }

        System.out.println("new connection" + i);
        this.connection = i;
        this.s = s;

        try {
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        } catch (IOException var8) {
            try {
                s.close();
            } catch (IOException var7) {
                var7.printStackTrace();
                System.out.println("Socket close");
                return;
            }

            var8.printStackTrace();
        }

        this.setDaemon(true);
        this.setPriority(5);
        this.start();
    }

    public void run() {
        while(true) {
            try {
                while(true) {
                    byte[] e = new byte[65536];
                    int e1 = this.is.read(e);
                    if(e1 == -1) {
                        this.s.close();
                        System.out.println("Sosket close");
                        return;
                    }

                    String data = new String(e, 0, e1);
                    ++this.mes;
                    System.out.println("");
                    System.out.println("connection:" + this.connection + " mes: " + this.mes + " Data: " + data);
                   // this.log.save(data);
                    if(this.mes != 1) {
                        if(this.mes > 100) {
                            this.mes = 2;
                        }
                        this.parser.next(data);
                    } else {
                        this.parser.first(data);
                    }

                    byte[] ba = res.getBytes();
                    os.write(ba);
                    os.flush();

                }
            } catch (Exception var5) {
                System.out.println("Error1: " + var5);

                try {
                    this.s.close();
                    System.out.println("s close ");
                    return;
                } catch (IOException var4) {
                    var4.printStackTrace();
                }
            }
        }
    }
}
