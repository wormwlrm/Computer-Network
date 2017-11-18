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
			// Ŭ���̾�Ʈ�κ��� �����͸� ������ ���� OutputStream ����
			bw.write(data); // Ŭ���̾�Ʈ�� ������ ����
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
			try { // Ŭ���̾�Ʈ�κ��� �����͸� �ޱ� ���� InputStream ����
		/*		while(UserTurnInThread == false){
					ReturnData("�ٸ� ������� �Է��� ��ٸ��� ���Դϴ�.");
					UserTurnInThread = SimpleServer.UserTurn;
				}*/
				is = socket.getInputStream();
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				String data = br.readLine();
				System.out.println("Ŭ���̾�Ʈ�� ���� ���� ������:" + data);
				if(CommandQueue.add(data)){
					System.out.print("Ŀ�ǵ� ť�� " + data + "�� ���۵Ǿ����ϴ�.");
					System.out.println(CommandQueue.toString());
				} else{
					System.out.println("���۽��� : " + data);
					return;
				}
			//	ReturnData(data + " from Server"); // ���� �����͸� �״�� �ٽ� ������
				System.out.println("****** ���� �Ϸ� ****");
			/*	SimpleServer.UserTurn = !SimpleServer.UserTurn;
				UserTurnInThread = SimpleServer.UserTurn;*/
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
