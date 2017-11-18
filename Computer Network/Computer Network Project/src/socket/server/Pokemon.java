package socket.server;
abstract class Pokemon {
	
	protected int Type;
	protected String Name;
	protected int MaxHealthPoint;
	protected int CurHealthPoint;
	protected int AttackPoint;
	protected int DefencePoint;
	protected int SpeedPoint;
	protected String SkillName; 
	
	public Pokemon(int T, String N, int MHP, int CHP, int AP, int DP, int SP, String SN) {
		Type = T;
		Name = N;
		MaxHealthPoint = MHP;
		CurHealthPoint = CHP;
		AttackPoint = AP;
		DefencePoint = DP;
		SpeedPoint = SP;
		SkillName = SN;
	}
	
	public String getPokemonInfo(){
		return (getName() + " : (" + getCurHealthPoint() + " / " + getMaxHealthPoint() + ")");
	}

	public int getType() {
		return Type;
	}

	public String getName() {
		return Name;
	}

	public int getMaxHealthPoint() {
		return MaxHealthPoint;
	}

	public int getCurHealthPoint() {
		return CurHealthPoint;
	}

	public int getAttackPoint() {
		return AttackPoint;
	}

	public int getDefencePoint() {
		return DefencePoint;
	}

	public int getSpeedPoint() {
		return SpeedPoint;
	}

	public String getSkillName(){
		return SkillName;
	}
	
	public void setType(int T) {
		Type = T;
	}

	public void setName(String N) {
		Name = N;
	}

	public void setMaxHealthPoint(int M) {
		MaxHealthPoint = M;
	}

	public void setCurHealthPoint(int C) {
		CurHealthPoint = C;
	}

	public void setAttackPoint(int A) {
		AttackPoint = A;
	}

	public void setDefencePoint(int D) {
		DefencePoint = D;
	}

	public void setSpeedPoint(int S) {
		SpeedPoint = S;
	}

	public void setSkillName(String SN){
		SkillName = SN;
	}
	
	public abstract String skill(Pokemon Ally, Pokemon Enemy);
}


