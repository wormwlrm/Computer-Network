package socket.server;

public class Magikarp extends Pokemon {

	public Magikarp(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO �ڵ� ������ ������ ����
	}
	
	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO �ڵ� ������ �޼ҵ� ����
		return "�׷��� �ƹ� �ϵ� �Ͼ�� �ʾҴ�!";
	}
}
