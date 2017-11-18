package socket.server;

public class Squirtle extends Pokemon {

	public Squirtle(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO 자동 생성된 생성자 스텁
	}
	
	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO 자동 생성된 메소드 스텁
		int RandomNum = ((int) (Math.random() * 100)) + 1;
		if (RandomNum <= Enemy.getSpeedPoint()) {
			return "그러나 빗나가고 말았다!";
		}		
		if(Enemy.getType() == 1){ // 풀 속성에게 공격
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 15);
			return "효과는 조금 부족한 듯 하다. 상대에게 15의 피해를 입혔다.";
		}
		else if(Enemy.getType() == 2){ // 불 속성에게 공격
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 25);
			return "효과는 뛰어났다! 상대에게 25의 피해를 입혔다!";
		}
		else{ // 물 속성에게 공격
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 20);
			return "상대에게 20의 피해를 입혔다!";
		}
	}
	
}
