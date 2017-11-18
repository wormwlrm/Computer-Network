package socket.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ServerThread extends Thread {
	protected Socket socket;
	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;
	protected Queue<String> CommandQueue = new LinkedList<String>();

	public ServerThread(Socket clientSocket, Queue<String> CommandQueue) {
		this.socket = clientSocket;
		this.CommandQueue = CommandQueue;	
	}
	
	public void ReturnData(String data) {
		OutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		try {
			os = socket.getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
			// 클라이언트로부터 데이터를 보내기 위해 OutputStream 선언
			bw.write(data); // 클라이언트로 데이터 전송
			bw.newLine();
			bw.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
	/*			bw.close();
				osw.close();
				os.close();*/
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void run() {
		while (true) {
			try { // 클라이언트로부터 데이터를 받기 위한 InputStream 선언
		/*		while(UserTurnInThread == false){
					ReturnData("다른 사용자의 입력을 기다리는 중입니다.");
					UserTurnInThread = SimpleServer.UserTurn;
				}*/
				is = socket.getInputStream();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				String data = br.readLine();
				System.out.println("클라이언트로 부터 받은 데이터:" + data);
				if(CommandQueue.add(data)){
					System.out.print("커맨드 큐에 " + data + "가 전송되었습니다.");
					System.out.println(CommandQueue.toString());
				} else{
					System.out.println("전송실패 : " + data);
					return;
				}
			//	ReturnData(data + " from Server"); // 받은 데이터를 그대로 다시 보내기
				System.out.println("****** 전송 완료 ****");
			/*	SimpleServer.UserTurn = !SimpleServer.UserTurn;
				UserTurnInThread = SimpleServer.UserTurn;*/
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
