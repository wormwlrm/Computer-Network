package socket.server;

public class Growlithe extends Pokemon {

	public Growlithe(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO �ڵ� ������ ������ ����
	}
	
	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO �ڵ� ������ �޼ҵ� ����
		Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 18);
		return "��뿡�� 18�� ���ظ� ������!";
	}
	
}
