package socket.server;

public class Exeggcute extends Pokemon {

	public Exeggcute(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO �ڵ� ������ ������ ����
	}
	
	int SolarBeamStack = 0;
	
	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO �ڵ� ������ �޼ҵ� ����
		if(SolarBeamStack == 0){
			SolarBeamStack++;
			return "�ƶ󸮴� ���� ����ߴ�!";
		}
		else{
			SolarBeamStack = 0;
			int RandomNum = ((int) (Math.random() * 100)) + 1;
			if(RandomNum <= Enemy.getSpeedPoint()){
				return "�׷��� �������� ���Ҵ�!";
			}
			if(Enemy.getType() == 1){ // Ǯ �Ӽ����� ����
				Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 70);
				return "��뿡�� 70�� ���ظ� ������!";
			}
			else if(Enemy.getType() == 2){ // �� �Ӽ����� ����
				Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 35);
				return "ȿ���� ���� ������ �� �ϴ�. ��뿡�� 35�� ���ظ� ������.";
			}
			else{ // �� �Ӽ����� ����
				Enemy.setCurHealthPoint(Enemy.getCurHealthPoint() - 105);
				return "ȿ���� �پ��! ��뿡�� 105�� ���ظ� ������!";
			}
		}
	}
	
}
