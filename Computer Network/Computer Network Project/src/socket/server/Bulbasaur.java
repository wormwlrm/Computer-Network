package socket.server;

public class Bulbasaur extends Pokemon {
	public Bulbasaur(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
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
		if (Enemy.getType() == 1) {
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 20);
			return "상대에게 20의 피해를 입혔다!";
		} else if (Enemy.getType() == 2) { // 불 속성이면
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 10);
			return "효과는 조금 부족한 듯 하다. 상대에게 10의 피해를 입혔다. ";
		} else { // 물 속성이면
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 30);
			return "효과는 뛰어났다! 상대에게 30의 피해를 입혔다!";
		}
	}

}
