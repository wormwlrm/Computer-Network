package socket.server;

public class Ponyta extends Pokemon {

	public Ponyta(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
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
		if(Enemy.getDefencePoint() <= 0){
			Enemy.setDefencePoint(0);
			return "����� ������ �� �̻� �������� �ʴ´�!";
		}		
		Enemy.setDefencePoint(Enemy.getDefencePoint() - 2);
		Ally.setSpeedPoint(Ally.getSpeedPoint() + 5);
		return "����� ������ ��������! ����Ÿ�� ȸ������ �ö���!";
	}

}
