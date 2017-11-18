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
    	System.out.println("메인 서버가 다음의 포트에서  실행 중 : " + port);
        try{
            while(true){ 
            	System.out.println("-------접속 대기중------");
                socket = null;
                socket = server.accept();  // 클라이언트가 접속하면 통신할 수 있는 소켓 반환
                System.out.println(socket.getInetAddress() + "로 부터 연결요청이 들어옴");
                
            	switch (socketCount) {
				case 0: // Waiting for another client
					socketList[0] = socket;
					new ServerThread(socket, CommandQueue).start();
					System.out.println("1명의 사용자가 연결되었습니다.");
					socketCount++;
					break;
				case 1: // Server can connect two client
					socketList[1] = socket;	
					new ServerThread(socket, CommandQueue).start();
					System.out.println("2명의 사용자가 연결되었습니다.");
					socketCount++;
					SetBattle();					
					break;
				case 2: // Refuse connection
					System.out.println("3명 이상의 사용자가 연결되었습니다.");
					OutputStream os = socketList[0].getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os);
					BufferedWriter bw = new BufferedWriter(osw);
					bw.write("이미 배틀이 시작되었습니다. 연결을 종료합니다.");
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
		try {// TODO 자동 생성된 메소드 스텁
			checkConnection();
			InitializePokemon();
			SelectPokemon();
			BattleStart();
		} catch (Exception e) {

		}
	}
    
	private void BattleStart() throws IOException, InterruptedException {
		// TODO 자동 생성된 메소드 스텁
		Pokemon[] CurBattlePokemon = new Pokemon[2];
		int WhosTurn = 0;
		sendServerMessageToClients(WhosTurn, "배틀이 곧 시작됩니다.");
		Thread.sleep(200);
		sendServerMessageToClients(WhosTurn, "배틀이 곧 시작됩니다..");
		Thread.sleep(200);
		sendServerMessageToClients(WhosTurn, "배틀이 곧 시작됩니다...");
		Thread.sleep(200);
		sendServerMessageToClients(WhosTurn, "배틀이 곧 시작됩니다....");
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
				sendServerMessageToClients(WhosTurn, CurBattlePokemon[WhosTurn].getName() + "은(는) 이번 턴에 무엇을 할까?");
				Thread.sleep(100);
				sendServerMessageToClients(WhosTurn, "1. 몸통박치기 / 2. " + CurBattlePokemon[WhosTurn].getSkillName() + " / 3. 나무 열매");
				Thread.sleep(100);
				
				switch (getUserChoice()) {
				case "1":
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].getName() + "의 몸통 박치기!");
					int Damage = (CurBattlePokemon[WhosTurn].getAttackPoint() - CurBattlePokemon[(WhosTurn+1)%2].getDefencePoint());
					CurBattlePokemon[(WhosTurn+1)%2].setCurHealthPoint(CurBattlePokemon[(WhosTurn+1)%2].getCurHealthPoint()-Damage);
					Thread.sleep(200);
					sendServerMessageToAllClients("상대에게 " + Damage + "의 피해를 입혔다!");
					break;
				case "2":
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].getName() + "의 " + CurBattlePokemon[WhosTurn].getSkillName() + "!");
					Thread.sleep(200);
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].skill(CurBattlePokemon[WhosTurn], CurBattlePokemon[(WhosTurn+1)%2]));
					break;
				case "3":
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].getName() + "은(는) 나무 열매를 먹었다.");
					Thread.sleep(200);
					sendServerMessageToAllClients(CurBattlePokemon[WhosTurn].getName() + "의 체력이 5 증가했다!");
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
			sendServerMessageToAllClients("플레이어 1의 승리!");
		}			
		else if(EnemyPokemonList.size() == 0){
			sendServerMessageToAllClients("플레이어 0의 승리!");
		}
		return;
	}

	private void checkConnection() throws IOException{
		System.out.println(socketList[0].toString());
		System.out.println(socketList[1].toString());
		for (int i = 0; i < 2; i++) {
			os = socketList[i].getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw); // 클라이언트로 전송을 위한 OutputStream
			String[] AllyAddress = socketList[i].toString().split(",");
			String[] EnemyAddress = socketList[1 - i].toString().split(",");
			bw.write("당신(" + AllyAddress[1] + ")은 상대 플레이어(" + EnemyAddress[1] + ")와 연결되었습니다.");
			bw.newLine();
			bw.flush();
		}
    }

	private void InitializePokemon() {
		// TODO 자동 생성된 메소드 스텁
		Pokemon Temp;
		Temp = new Bulbasaur(1, "이상해씨", 45, 45, 12, 4, 20, "덩굴채찍");
		PokemonList.addElement(Temp);
		Temp = new Charmander(2, "파이리", 39, 39, 14, 3, 20, "불꽃세례");
		PokemonList.addElement(Temp);
		Temp = new Squirtle(3, "꼬부기", 52, 52, 11, 7, 10, "물대포");
		PokemonList.addElement(Temp);
		Temp = new Bellsprout(1, "모다피", 41, 41, 10, 5, 20, "광합성");
		PokemonList.addElement(Temp);
		Temp = new Growlithe(2, "가디", 45, 45, 11, 6, 40, "물기");
		PokemonList.addElement(Temp);
		Temp = new Magikarp(3, "잉어킹", 35, 35, 10, 4, 10, "튀어오르기");
		PokemonList.addElement(Temp);
		Temp = new Exeggcute(1, "아라리", 47, 47, 12, 5, 20, "솔라빔");
		PokemonList.addElement(Temp);
		Temp = new Ponyta(2, "포니타", 43, 43, 11, 4, 30, "꼬리흔들기");
		PokemonList.addElement(Temp);
		Temp = new Slowpoke(3, "야돈", 49, 49, 10, 6, 10, "난동부리기");
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
		sendServerMessageToAllyClients("먼저 함께 싸울 포켓몬을 선택합니다.");		
		for (int k = 0; k < 6; k++) { // 포켓몬 선택
			os = socketList[k % 2].getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
			Thread.sleep(500);
			String SelectablePokemonName = "";
			for(int j = 0; j < PokemonList.size(); j++){
				SelectablePokemonName = SelectablePokemonName + j + ":" + PokemonList.get(j).getName() + " ";
			}
			bw.write("선택할 포켓몬 번호을 입력해주세요(" + SelectablePokemonName + ")");
			bw.newLine();
			bw.flush();
			int choice = Integer.parseInt(getUserChoice());
			sendServerMessageToAllClients(PokemonList.get(choice).getName() + "이(가) 선택되었다!");
			if(k % 2 == 0){ // 0번 사용자이면
				AllyPokemonList.addElement(PokemonList.get(choice));
			}else if(k % 2 == 1){ // 1번 사용자이면
				EnemyPokemonList.addElement(PokemonList.get(choice));
			}				
			PokemonList.remove(choice); // 고를 수 있는 리스트에서 삭제
		}			
		sendServerMessageToAllClients("플레이어 0의 포켓몬 : " + AllyPokemonList.get(0).getName() + " / "
				+ AllyPokemonList.get(1).getName() + " / " + AllyPokemonList.get(2).getName());
		sendServerMessageToAllClients("플레이어 1의 포켓몬 : " + EnemyPokemonList.get(0).getName() + " / "
				+ EnemyPokemonList.get(1).getName() + " / " + EnemyPokemonList.get(2).getName());
		sendServerMessageToEnemyClients("모든 선택이 완료되었습니다.");
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
            // 클라이언트로부터 데이터를 보내기 위해 OutputStream 선언
            bw.write(data);            // 클라이언트로 데이터 전송
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


