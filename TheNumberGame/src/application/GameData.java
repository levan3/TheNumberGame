package application;

public class GameData {
	int randomNumber;
	int attempts;
	
	
	public int getRandomNumber() {
		return randomNumber;
	}
	public void setRandomNumber(int randomNumber) {
		this.randomNumber = randomNumber;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public GameData(int randomNumber, int attempts) {
		super();
		this.randomNumber = randomNumber;
		this.attempts = attempts;
	}

}