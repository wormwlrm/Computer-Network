package socket.server;

public class Growlithe extends Pokemon {

	public Growlithe(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO 자동 생성된 생성자 스텁
	}
	
	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO 자동 생성된 메소드 스텁
		Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 18);
		return "상대에게 18의 피해를 입혔다!";
	}
	
}
