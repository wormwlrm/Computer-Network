package client;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
 
public class SimpleClient{
//	private static Logger logger = Logger.getAnonymousLogger();
	static Socket socket = null;
    OutputStream os = null;
    OutputStreamWriter osw = null;
    BufferedWriter bw = null;
    
    static InputStream is = null;
    static InputStreamReader isr = null;
    static BufferedReader br = null;
	
	public static void main(String[] args) throws IOException, InterruptedException{
        SimpleClient cm = new SimpleClient();
        socket = new Socket("127.0.0.1", 4200);
        Scanner scanner = new Scanner(System.in);
        is = socket.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);        // �����κ��� Data�� ����
        cm.ReceiveMessage(); // ������ ������ �Ǹ� ����Ǿ����� �޽����� ����
        
		for (int i = 0; i < 3; i++) { // ���ϸ� ��
			cm.ReceiveMessage();
			cm.ReceiveMessage();
			System.out.print("�޽��� �Է� : ");
			String input = scanner.nextLine();
			cm.SendMessage(input); // ���� ���� ����
			cm.ReceiveMessage();
		}
        
		cm.ReceiveMessage(); // ����� ��
		cm.ReceiveMessage(); // ���� ��
		Thread.sleep(100);
		cm.ReceiveMessage(); // ��ũ ����
		cm.ReceiveMessage(); // ���м�
		
        while (true) { // ��Ʋ
			try {
				cm.ReceiveMessage();	// ��ü �������ͽ�
				Thread.sleep(100);
				cm.ReceiveMessage();  
				Thread.sleep(100);
				cm.ReceiveMessage();	// ����� �ൿ
				Thread.sleep(100);
				cm.ReceiveMessage(); 	// �ൿ�� ȿ��
				Thread.sleep(100);
				cm.ReceiveMessage();	// ��ü �������ͽ�
				Thread.sleep(100);
				cm.ReceiveMessage();
				Thread.sleep(100);
				cm.ReceiveMessage();	// �� ���ϸ�
				Thread.sleep(100);
				cm.ReceiveMessage();	// ������
				Thread.sleep(100);
				System.out.print("�޽��� �Է� : ");
				String input = scanner.nextLine();
				cm.SendMessage(input); // ���� ���� ����
				Thread.sleep(100);
				cm.ReceiveMessage();   // ���� ���� ���
				Thread.sleep(100);
				cm.ReceiveMessage();   	
				Thread.sleep(100);
			}catch (Exception e) {
				scanner.close();
				return;
			}
        }
    }
	
    public void SendMessage(String data){
        try{            
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);            //������ ������ ���� OutputStream
        
            bw.write(data);
            bw.newLine();
            bw.flush();            
            
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
 /*              bw.close();
                osw.close();
                os.close();
                br.close();
                isr.close();
                is.close();*/
            }catch(Exception e){
                e.printStackTrace();
            }
        }    
        
    }
    
    public void ReceiveMessage() throws IOException{
        is = socket.getInputStream();
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);        // �����κ��� Data�� ����
        
        String receiveData = br.readLine();        // �����κ��� ������ ���� ����
        System.out.println("Server : " + receiveData);
    }    
}
