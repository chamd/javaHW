import java.util.Scanner;

public class TicTacToe {

	static char[] maps = {'_', '_', '_', '_', '_', '_', '_', '_', '_'};
	static int userSelect, comSelect;
	static String first;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("선을 선택하세요(com, user) : ");
		first = sc.next();
		if(first.equals("com")) {
			maps[(int) (Math.random() * 9)] = 'x';
		} else if (!first.equals("user")) {
			System.out.println("없는 선택이므로 당신이 선입니다");
		}
		gameLoop(sc);
		
		sc.close();
	}
	
	static void gameLoop(Scanner sc) {
		while (true) {
			printMap();
			System.out.print("칸을 선택하세요(1~9) : ");
			userSelect = sc.nextInt() - 1;
			while (0 > userSelect || userSelect > 8) {
				System.out.println("없는 칸입니다");
				System.out.print("칸을 선택하세요(1~9) : ");
				userSelect = sc.nextInt() - 1;
			}
			while (maps[userSelect] != '_') {
				System.out.println("이미 선택된 칸입니다");
				System.out.print("칸을 선택하세요(1~9) : ");
				userSelect = sc.nextInt() - 1;
			}
			maps[userSelect] = 'o';
			
			if (isWin('o')) {
				printMap();
				System.out.println("당신이 승리했습니다!");
				break;
			}
			
			comSelect = getRandom();
			if (comSelect == 10) {
				printMap();
				System.out.println("무승부입니다!");
				break;
			}
			maps[comSelect] = 'x';
			
			if (isWin('x')) {
				printMap();
				System.out.println("컴퓨터가 승리했습니다!");
				break;
			}
		}
	}
	
	static void printMap() {
		System.out.println("[" + maps[0] + "][" + maps[1] + "][" + maps[2] + "]");
		System.out.println("[" + maps[3] + "][" + maps[4] + "][" + maps[5] + "]");
		System.out.println("[" + maps[6] + "][" + maps[7] + "][" + maps[8] + "]");
	}
	
	static int getRandom() {
		int result = userSelect;
		int endCount = 0;
		int blank = 0;
		for (int i = 0; i < 9; i ++) {
			if (maps[i] != '_') {
				endCount ++;
			} else {
				blank = i;
			}
		}
		if (endCount == 9 || endCount == 8) {
			if (first.equals("user")) {
				maps[blank] = 'x';
			}
			return 10;
		} else {
			while (maps[result] != '_') {
				result = (int) (Math.random() * 9);
			}
			return result;
		}
	}
	
	static boolean isWin(char player) {
		int curCount = 0;
		for (int i = 0; i <= 6; i += 3) {
			curCount = 0;
			for (int j = 0; j <= 2; j ++) {
				if (maps[i + j] == player) {
					curCount ++;
				}
			}
			if (curCount == 3) {
				return true;
			}
		}
		for (int i = 0; i <= 2; i ++) {
			curCount = 0;
			for (int j = 0; j <= 6; j += 3) {
				if (maps[i + j] == player) {
					curCount ++;
				}
			}
			if (curCount == 3) {
				return true;
			}
		}
		if (maps[0] == maps[4] && maps[4] == maps[8] && maps[8] == player) {
			return true;
		}
		
		if (maps[2] == maps[4] && maps[4] == maps[6] && maps[6] == player) {
			return true;
		}
		
		return false;
	}

}
