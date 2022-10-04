import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    static Socket[] clients = new Socket[3];
	static int count;
	public static void main(String...args){
		try{
			ServerSocket ss = new ServerSocket(6666);
			while(true){
				Socket s = ss.accept();
				clients[count] = s;
				Thread client = new ClientHandler(s, count, clients);
				count++;
				client.start();
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}
class ClientHandler extends Thread{
	private Socket s;
	private int id;
	private Socket[] clients;
	public ClientHandler(Socket s, int id, Socket[] clients){
		this.s = s;
		this.id = id;
		this.clients = clients;
	}

	@Override
	public void run(){
		try{
			DataInputStream dio = new DataInputStream(s.getInputStream());

			while(true){
				String msg = dio.readUTF();
				System.out.println("Receiving a msg from Client #" + id);
				DataOutputStream dos;
				// go throught the data strcuture and send the msg to all clients
				//the only element you ignore is the client who sent the msg
				//you can use the id to identify the client who sent the msg
				//and ignore him which is int id = 0;
				for(int i = 0; i < clients.length; i++){	
					//  ignore the client who sent the msg
					if(i == id)
					// dont send the msg to the client who sent the msg
						
					continue;
					//boradcasting: remove the if statement and send the msg to all clients including the sender
					// send the msg to all clients
					//add more features to the chat app
					Socket dstClient = clients[i];
					
					dos = new DataOutputStream(dstClient.getOutputStream());
					dos.writeUTF(msg);
					dos.flush();
					System.out.println("Forwarding the msg to Client #" + i);
				}
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}
