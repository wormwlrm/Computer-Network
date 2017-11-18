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
        br = new BufferedReader(isr);        // 서버로부터 Data를 받음
        cm.ReceiveMessage(); // 서버와 연결이 되면 연결되었음을 메시지로 받음
        
		for (int i = 0; i < 3; i++) { // 포켓몬 픽
			cm.ReceiveMessage();
			cm.ReceiveMessage();
			System.out.print("메시지 입력 : ");
			String input = scanner.nextLine();
			cm.SendMessage(input); // 나의 선택 전송
			cm.ReceiveMessage();
		}
        
		cm.ReceiveMessage(); // 상대의 픽
		cm.ReceiveMessage(); // 나의 픽
		Thread.sleep(100);
		cm.ReceiveMessage(); // 싱크 조절
		cm.ReceiveMessage(); // 구분선
		
        while (true) { // 배틀
			try {
				cm.ReceiveMessage();	// 전체 스테이터스
				Thread.sleep(100);
				cm.ReceiveMessage();  
				Thread.sleep(100);
				cm.ReceiveMessage();	// 상대의 행동
				Thread.sleep(100);
				cm.ReceiveMessage(); 	// 행동의 효과
				Thread.sleep(100);
				cm.ReceiveMessage();	// 전체 스테이터스
				Thread.sleep(100);
				cm.ReceiveMessage();
				Thread.sleep(100);
				cm.ReceiveMessage();	// 내 포켓몬
				Thread.sleep(100);
				cm.ReceiveMessage();	// 선택지
				Thread.sleep(100);
				System.out.print("메시지 입력 : ");
				String input = scanner.nextLine();
				cm.SendMessage(input); // 나의 선택 전송
				Thread.sleep(100);
				cm.ReceiveMessage();   // 나의 선택 출력
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
            bw = new BufferedWriter(osw);            //서버로 전송을 위한 OutputStream
        
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
        br = new BufferedReader(isr);        // 서버로부터 Data를 받음
        
        String receiveData = br.readLine();        // 서버로부터 데이터 한줄 읽음
        System.out.println("Server : " + receiveData);
    }    
}
