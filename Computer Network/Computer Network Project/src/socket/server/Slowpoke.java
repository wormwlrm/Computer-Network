package socket.server;

public class Slowpoke extends Pokemon {

	public Slowpoke(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
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
		int HowManyHit = ((int) (Math.random() * 5)) + 1; // 1~5대 랜덤으로 때린다
		Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - HowManyHit * 7);
		return ("총 " + HowManyHit + "대 때렸다! 상대에게 " + 7 * HowManyHit + "의 피해를 입혔다.");

	}

}
