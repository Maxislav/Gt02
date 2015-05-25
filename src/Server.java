//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;


public class Server {
    int port;
    int i = 0;
    LogTxt log = new LogTxt();

    public Server(int port, Bd bd) throws IOException {
        try {
            ServerSocket e = new ServerSocket(port);
            InetAddress me = InetAddress.getLocalHost();
            String dottedQuad = me.getHostAddress();
            System.out.println("server is started IP:" + dottedQuad + " port: " + port);

            while(true) {
                ++this.i;
                if(1000 < this.i) {
                    this.i = 1;
                }

                new Branch(this.i, e.accept(), bd, this.log);
            }
        } catch (Exception var6) {
            System.out.println("Error create server " + var6);
        }
    }
}
