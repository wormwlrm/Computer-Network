package socket.server;

public class Squirtle extends Pokemon {

	public Squirtle(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
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
		if(Enemy.getType() == 1){ // Ǯ �Ӽ����� ����
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 15);
			return "ȿ���� ���� ������ �� �ϴ�. ��뿡�� 15�� ���ظ� ������.";
		}
		else if(Enemy.getType() == 2){ // �� �Ӽ����� ����
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 25);
			return "ȿ���� �پ��! ��뿡�� 25�� ���ظ� ������!";
		}
		else{ // �� �Ӽ����� ����
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 20);
			return "��뿡�� 20�� ���ظ� ������!";
		}
	}
	
}
