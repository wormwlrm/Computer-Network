package socket.server;

public class Exeggcute extends Pokemon {

	public Exeggcute(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO 자동 생성된 생성자 스텁
	}
	
	int SolarBeamStack = 0;
	
	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO 자동 생성된 메소드 스텁
		if(SolarBeamStack == 0){
			SolarBeamStack++;
			return "아라리는 빛을 흡수했다!";
		}
		else{
			SolarBeamStack = 0;
			int RandomNum = ((int) (Math.random() * 100)) + 1;
			if(RandomNum <= Enemy.getSpeedPoint()){
				return "그러나 빗나가고 말았다!";
			}
			if(Enemy.getType() == 1){ // 풀 속성에게 공격
				Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 70);
				return "상대에게 70의 피해를 입혔다!";
			}
			else if(Enemy.getType() == 2){ // 불 속성에게 공격
				Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 35);
				return "효과는 조금 부족한 듯 하다. 상대에게 35의 피해를 입혔다.";
			}
			else{ // 물 속성에게 공격
				Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 105);
				return "효과는 뛰어났다! 상대에게 105의 피해를 입혔다!";
			}
		}
	}
	
}
