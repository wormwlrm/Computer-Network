package socket.server;

public class Bellsprout extends Pokemon {

	public Bellsprout(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO 자동 생성된 생성자 스텁
	}

	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) { // 잃은 체력의 30% 회복
		// TODO 자동 생성된 메소드 스텁
		int Heal = (int) ((0.3 * (Ally.getMaxHealthPoint() - Ally.getCurHealthPoint())));
		Ally.setCurHealthPoint(Ally.getCurHealthPoint() + Heal);
		return ("모다피은(는) 잃은 체력의 30%인 " + Heal + "만큼 회복했다!");
	}

}
