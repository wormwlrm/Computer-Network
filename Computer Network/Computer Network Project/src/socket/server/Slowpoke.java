package socket.server;

public class Slowpoke extends Pokemon {

	public Slowpoke(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO �ڵ� ������ ������ ����
	}

	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO �ڵ� ������ �޼ҵ� ����
		int RandomNum = ((int) (Math.random() * 100)) + 1;
		if (RandomNum <= Enemy.getSpeedPoint()) {
			return "�׷��� �������� ���Ҵ�!";
		}
		int HowManyHit = ((int) (Math.random() * 5)) + 1; // 1~5�� �������� ������
		Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - HowManyHit * 7);
		return ("�� " + HowManyHit + "�� ���ȴ�! ��뿡�� " + 7 * HowManyHit + "�� ���ظ� ������.");

	}

}
