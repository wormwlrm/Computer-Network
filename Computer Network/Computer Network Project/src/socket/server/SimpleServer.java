package socket.server;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
 
public class SimpleServer {
	private static Socket[] socketList = new Socket[2];
	private static Vector<Pokemon> PokemonList = new Vector<Pokemon>(9);
	private static Vector<Pokemon> AllyPokemonList = new Vector<Pokemon>(3);
	private static Vector<Pokemon> EnemyPokemonList = new Vector<Pokemon>(3);
	private static int socketCount = 0;
	private static Queue<String> CommandQueue = new LinkedList<String>();
	OutputStream os = null;
	OutputStreamWriter osw = null;
	BufferedWriter bw = null;
	
    public static void main(String[] args) throws IOException{
        SimpleServer ss = new SimpleServer();
        ss.ServerRun();
    }
 
    @SuppressWarnings("resource")
	public void ServerRun() throws IOException{
        ServerSocket server = null;
        int port = 4200;
        Socket socket = null;
        
        server = new ServerSocket(port);
    	System.out.println("���� ������ ������ ��Ʈ����  ���� �� : " + port);
        try{
            while(true){ 
            	System.out.println("-------���� �����------");
                socket = null;
                socket = server.accept();  // Ŭ���̾�Ʈ�� �����ϸ� ����� �� �ִ� ���� ��ȯ
                System.out.println(socket.getInetAddress() + "�� ���� �����û�� ����");
                
            	switch (socketCount) {
				case 0: // Waiting for another client
					socketList[0] = socket;
					new ServerThread(socket, CommandQueue).start();
					System.out.println("1���� ����ڰ� ����Ǿ����ϴ�.");
					socketCount++;
					break;
				case 1: // Server can connect two client
					socketList[1] = socket;	
					new ServerThread(socket, CommandQueue).start();
					System.out.println("2���� ����ڰ� ����Ǿ����ϴ�.");
					socketCount++;
					SetBattle();					
					break;
				case 2: // Refuse connection
					System.out.println("3�� �̻��� ����ڰ� ����Ǿ����ϴ�.");
					OutputStream os = socketList[0].getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os);
					BufferedWriter bw = new BufferedWriter(osw);
					bw.write("�̹� ��Ʋ�� ���۵Ǿ����ϴ�. ������ �����մϴ�.");
					bw.newLine();
					bw.flush();
					socket.close();
					break;
				}

            }
        }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try{

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }    
    
    private void SetBattle() {
		try {// TODO �ڵ� ������ �޼ҵ� ����
			checkConnection();
			InitializePokemon();
			SelectPokemon();
			BattleStart();
		} catch (Exception e) {

		}
	}
    
	private void BattleStart() throws IOException, InterruptedException {
		// TODO �ڵ� ������ �޼ҵ� ����
		Pokemon[] CurBattlePokemon = new Pokemon[2];
		int WhosTurn = 0;
		sendServerMessageToClients(WhosTurn, "��Ʋ�� �� ���۵˴ϴ�.");
		Thread.sleep(200);
		sendServerMessageToClients(WhosTurn, "��Ʋ�� �� ���۵˴ϴ�..");
		Thread.sleep(200);
		sendServerMessageToClients(WhosTurn, "��Ʋ�� �� ���۵˴ϴ�...");
		Thread.sleep(200);
		sendServerMessageToClients(WhosTurn, "��Ʋ�� �� ���۵˴ϴ�....");
		Thread.sleep(200);
		while (AllyPokemonList.size() != 0 && EnemyPokemonList.size() != 0) { 
			Pokemon CurAlly = AllyPokemonList.get(0);
			CurBattlePokemon[0] = CurAlly;
			Pokemon CurEnemy = EnemyPokemonList.get(0);
			CurBattlePokemon[1] = CurEnemy;
			
			while (CurAlly.getCurHealthPoint() > 0 && CurEnemy.getCurHealthPoint() > 0) {				
				sendServerMessageToAllClients(CurAlly.getPokemonInfo());
				Thread.sleep(100);
				sendServerMessageToAllClients(CurEnemy.getPokemonInfo());
				Thread.sleep(100);
				sendServerMessageToClients(WhosTurn, CurBattlePokemon[WhosTurn].getName() + "��(��) �̹� �Ͽ� ������ �ұ�?");
				Thread.sleep(100);
				sendServerMessageToClients(WhosTurn, "1. �����ġ�� / 2. " + CurBattlePokemon[WhosTurn].getSkillName() + " / 3. ���� ����");
				Thread.sleep(100);
				
				switch (getUserChoice()) {
				case "1":
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].getName() + "�� ���� ��ġ��!");
					int Damage = (CurBattlePokemon[WhosTurn].getAttackPoint() - CurBattlePokemon[(WhosTurn+1)%2].getDefencePoint());
					CurBattlePokemon[(WhosTurn+1)%2].setCurHealthPoint(CurBattlePokemon[(WhosTurn+1)%2].getCurHealthPoint()-Damage);
					Thread.sleep(200);
					sendServerMessageToAllClients("��뿡�� " + Damage + "�� ���ظ� ������!");
					break;
				case "2":
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].getName() + "�� " + CurBattlePokemon[WhosTurn].getSkillName() + "!");
					Thread.sleep(200);
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].skill(CurBattlePokemon[WhosTurn], CurBattlePokemon[(WhosTurn+1)%2]));
					break;
				case "3":
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].getName() + "��(��) ���� ���Ÿ� �Ծ���.");
					Thread.sleep(200);
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].getName() + "�� ü���� 5 �����ߴ�!");
					CurBattlePokemon[WhosTurn].setCurHealthPoint(CurBattlePokemon[WhosTurn].getCurHealthPoint() + 5);
					break;
				}
				Thread.sleep(200);
				if(CurAlly.getCurHealthPoint() > CurAlly.getMaxHealthPoint())
					CurAlly.setCurHealthPoint(CurAlly.getMaxHealthPoint());
				if(CurEnemy.getCurHealthPoint() > CurEnemy.getMaxHealthPoint())
					CurEnemy.setCurHealthPoint(CurEnemy.getMaxHealthPoint());				
				
				WhosTurn = (WhosTurn + 1) % 2;
			}
			if(CurAlly.getCurHealthPoint() <= 0){
				AllyPokemonList.remove(0);
			}				
			else if( CurEnemy.getCurHealthPoint() <= 0){
				EnemyPokemonList.remove(0);
			}
		}
		
		if(AllyPokemonList.size() == 0){	
			sendServerMessageToAllClients("�÷��̾� 1�� �¸�!");
		}			
		else if(EnemyPokemonList.size() == 0){
			sendServerMessageToAllClients("�÷��̾� 0�� �¸�!");
		}
		return;
	}

	private void checkConnection() throws IOException{
		System.out.println(socketList[0].toString());
		System.out.println(socketList[1].toString());
		for (int i = 0; i < 2; i++) {
			os = socketList[i].getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw); // Ŭ���̾�Ʈ�� ������ ���� OutputStream
			String[] AllyAddress = socketList[i].toString().split(",");
			String[] EnemyAddress = socketList[1 - i].toString().split(",");
			bw.write("���(" + AllyAddress[1] + ")�� ��� �÷��̾�(" + EnemyAddress[1] + ")�� ����Ǿ����ϴ�.");
			bw.newLine();
			bw.flush();
		}
    }

	private void InitializePokemon() {
		// TODO �ڵ� ������ �޼ҵ� ����
		Pokemon Temp;
		Temp = new Bulbasaur(1, "�̻��ؾ�", 45, 45, 12, 4, 20, "����ä��");
		PokemonList.addElement(Temp);
		Temp = new Charmander(2, "���̸�", 39, 39, 14, 3, 20, "�Ҳɼ���");
		PokemonList.addElement(Temp);
		Temp = new Squirtle(3, "���α�", 52, 52, 11, 7, 10, "������");
		PokemonList.addElement(Temp);
		Temp = new Bellsprout(1, "�����", 41, 41, 10, 5, 20, "���ռ�");
		PokemonList.addElement(Temp);
		Temp = new Growlithe(2, "����", 45, 45, 11, 6, 40, "����");
		PokemonList.addElement(Temp);
		Temp = new Magikarp(3, "�׾�ŷ", 35, 35, 10, 4, 10, "Ƣ�������");
		PokemonList.addElement(Temp);
		Temp = new Exeggcute(1, "�ƶ�", 47, 47, 12, 5, 20, "�ֶ��");
		PokemonList.addElement(Temp);
		Temp = new Ponyta(2, "����Ÿ", 43, 43, 11, 4, 30, "��������");
		PokemonList.addElement(Temp);
		Temp = new Slowpoke(3, "�ߵ�", 49, 49, 10, 6, 10, "�����θ���");
		PokemonList.addElement(Temp);
	}
	
	private String getUserChoice() throws InterruptedException, IOException{
		while (true) {
			Thread.sleep(500);
			if (!CommandQueue.isEmpty()) {
				String Command = CommandQueue.peek();
				CommandQueue.remove();
				return Command;
			}		
		}
	}	
	private void sendServerMessageToAllClients(String Message) throws IOException{
		for (int i = 0; i < 2; i++) {
			OutputStream  os = socketList[i].getOutputStream();
			OutputStreamWriter  osw = new OutputStreamWriter(os);
			BufferedWriter  bw = new BufferedWriter(osw); 
			bw.write(Message);
			bw.newLine();
			bw.flush();
		}
		return;
	}

	private void sendServerMessageToAllyClients(String Message) throws IOException {
		OutputStream os = socketList[0].getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(Message);
		bw.newLine();
		bw.flush();
		return;
	}
	
	private void sendServerMessageToClients(int SocketNum, String Message) throws IOException {
		OutputStream os = socketList[SocketNum].getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(Message);
		bw.newLine();
		bw.flush();
		return;
	}

	private void sendServerMessageToEnemyClients(String Message) throws IOException {
		OutputStream os = socketList[1].getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(Message);
		bw.newLine();
		bw.flush();
		return;
	}
	
	private void SelectPokemon() throws IOException, InterruptedException{
		sendServerMessageToAllyClients("���� �Բ� �ο� ���ϸ��� �����մϴ�.");		
		for (int k = 0; k < 6; k++) { // ���ϸ� ����
			os = socketList[k % 2].getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
			Thread.sleep(500);
			String SelectablePokemonName = "";
			for(int j = 0; j < PokemonList.size(); j++){
				SelectablePokemonName = SelectablePokemonName + j + ":" + PokemonList.get(j).getName() + " ";
			}
			bw.write("������ ���ϸ� ��ȣ�� �Է����ּ���(" + SelectablePokemonName + ")");
			bw.newLine();
			bw.flush();
			int choice = Integer.parseInt(getUserChoice());
			sendServerMessageToAllClients(PokemonList.get(choice).getName() + "��(��) ���õǾ���!");
			if(k % 2 == 0){ // 0�� ������̸�
				AllyPokemonList.addElement(PokemonList.get(choice));
			}else if(k % 2 == 1){ // 1�� ������̸�
				EnemyPokemonList.addElement(PokemonList.get(choice));
			}				
			PokemonList.remove(choice); // �� �� �ִ� ����Ʈ���� ����
		}			
		sendServerMessageToAllClients("�÷��̾� 0�� ���ϸ� : " + AllyPokemonList.get(0).getName() + " / "
				+ AllyPokemonList.get(1).getName() + " / " + AllyPokemonList.get(2).getName());
		sendServerMessageToAllClients("�÷��̾� 1�� ���ϸ� : " + EnemyPokemonList.get(0).getName() + " / "
				+ EnemyPokemonList.get(1).getName() + " / " + EnemyPokemonList.get(2).getName());
		sendServerMessageToEnemyClients("��� ������ �Ϸ�Ǿ����ϴ�.");
		Thread.sleep(100);
		sendServerMessageToAllClients("-------------------------------------------");
	}
		
}

	/*	InputStream is = socketList[0].getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);*/

	/*public void receiveData(String data, Socket socket){
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        
        try{
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            // Ŭ���̾�Ʈ�κ��� �����͸� ������ ���� OutputStream ����
            bw.write(data);            // Ŭ���̾�Ʈ�� ������ ����
            bw.flush();
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            try{
                bw.close();
                osw.close();
                os.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }   */


