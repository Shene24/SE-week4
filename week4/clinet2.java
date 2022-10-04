import java.io.*;
import java.net.*;
import java.util.Scanner;
//cleint2 class
public class clinet2 {
    public static void main(String[] args) {
        final BufferedReader in;
        final PrintWriter dout;
        final Scanner scan = new Scanner(System.in);
        try {
            Socket so = new Socket("localhost", 6666); 
                dout = new PrintWriter(so.getOutputStream());
                 in = new BufferedReader(new InputStreamReader(so.getInputStream()));
                 
                 Thread sender = new Thread(new Runnable() {
                    String msg;
                    @Override
                    public void run(){
                        while(true){
                            msg = scan.nextLine();
                            dout.println(msg);
                            dout.flush();
                        }
                    }

                 });
                 sender.start();
                 Thread receiver = new Thread(new Runnable() {
                    String msg;
                    @Override
                    public void run(){
                        try {
                            msg =in.readLine();
                            while(msg!=null){
                                System.out.println("Server: "+ msg);
                                msg =in.readLine();
                            }
                            System.out.println("Server out of service");
                            dout.close();
                            so.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            // TODO: handle exception
                        }
                    }
                 });
                 receiver.start();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
