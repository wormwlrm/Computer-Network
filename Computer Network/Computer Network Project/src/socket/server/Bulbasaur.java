package socket.server;

public class Bulbasaur extends Pokemon {
	public Bulbasaur(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
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
		if (Enemy.getType() == 1) {
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 20);
			return "��뿡�� 20�� ���ظ� ������!";
		} else if (Enemy.getType() == 2) { // �� �Ӽ��̸�
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 10);
			return "ȿ���� ���� ������ �� �ϴ�. ��뿡�� 10�� ���ظ� ������. ";
		} else { // �� �Ӽ��̸�
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 30);
			return "ȿ���� �پ��! ��뿡�� 30�� ���ظ� ������!";
		}
	}

}
