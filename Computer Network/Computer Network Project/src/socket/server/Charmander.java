package socket.server;

public class Charmander extends Pokemon {

	public Charmander(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO �ڵ� ������ ������ ����
	}
	
	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO �ڵ� ������ �޼ҵ� ����
		int RandomNum = ((int) (Math.random() * 100)) + 1;
		if(RandomNum <= Enemy.getSpeedPoint()){
			return "�׷��� �������� ���Ҵ�!";
		}
		if(Enemy.getType() == 1){ // Ǯ �Ӽ����� ����
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 33);
			return "ȿ���� �پ��! ��뿡�� 33�� ���ظ� ������!";
		}
		else if(Enemy.getType() == 2){ // �� �Ӽ����� ����
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 15);
			return "��뿡�� 15�� ���ظ� ������!";
		}
		else{ // �� �Ӽ����� ����
			Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 7);
			return "ȿ���� ���� ������ �� �ϴ�. ��뿡�� 7�� ���ظ� ������.";
		}
	}
	
}
