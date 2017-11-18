package socket.server;

public class Ponyta extends Pokemon {

	public Ponyta(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		super(T, N, MHP, CHP, AP, DP, SP, SN);
		// TODO 자동 생성된 생성자 스텁
	}

	@Override
	public String skill(Pokemon Ally, Pokemon Enemy) {
		// TODO 자동 생성된 메소드 스텁
		int RandomNum = ((int) (Math.random() * 100)) + 1;
		if(RandomNum <= Enemy.getSpeedPoint()){
			return "그러나 빗나가고 말았다!";
		}
		if(Enemy.getDefencePoint() <= 0){
			Enemy.setDefencePoint(0);
			return "상대의 방어력은 더 이상 떨어지지 않는다!";
		}		
		Enemy.setDefencePoint(Enemy.getDefencePoint() - 2);
		Ally.setSpeedPoint(Ally.getSpeedPoint() + 5);
		return "상대의 방어력이 떨어졌다! 포니타의 회피율이 올랐다!";
	}

}
