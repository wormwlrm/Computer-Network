package socket.server;

public class Bellsprout extends Pokemon {

	public Bellsprout(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO �ڵ� ������ ������ ����
	}

	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) { // ���� ü���� 30% ȸ��
		// TODO �ڵ� ������ �޼ҵ� ����
		int Heal = (int) ((0.3 * (Ally.getMaxHealthPoint() - Ally.getCurHealthPoint())));
		Ally.setCurHealthPoint(Ally.getCurHealthPoint() + Heal);
		return ("�������(��) ���� ü���� 30%�� " + Heal + "��ŭ ȸ���ߴ�!");
	}

}
