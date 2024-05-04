import java.util.Scanner;

public class TicTacToe {

	// 전역변수 maps(3x3 맵), userSelect(유저 선택 칸), comSelect(컴퓨터 선택 칸), first(선)
	static char[] maps = {'_', '_', '_', '_', '_', '_', '_', '_', '_'};
	static int userSelect, comSelect;
	static String first;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("선을 선택하세요(com, user) : ");
		first = sc.next();
		
		// equals() 메소드로 string 검사
		if(first.equals("com")) {
			// 컴퓨터가 선이면 먼저 랜덤 칸에 x 표시
			maps[(int) (Math.random() * 9)] = 'x';
		} else if (!first.equals("user")) {
			// com, user가 둘다 아니면 유저가 선
			System.out.println("없는 선택이므로 당신이 선입니다");
		}
		// gameLoop 함수 실행 (sc는 스캐너를 인수로 전달)
		gameLoop(sc);
		
		sc.close();
	}
	
	// gameLoop 함수 (sc를 매개변수로 받음)
	static void gameLoop(Scanner sc) {
		// break가 나오기 전까지 무한 반복
		while (true) {
			// printMap 함수를 실행해서 실시간 현황을 출력
			printMap();
			System.out.print("칸을 선택하세요(1~9) : ");
			// 유저 선택은 1~9이지만 maps는 0~8이기 때문에 1을 뺌
			userSelect = sc.nextInt() - 1;
			// 유저 선택이 0보다 적거나 8보다 크면 다시 선택하게 함
			while (0 > userSelect || userSelect > 8) {
				System.out.println("없는 칸입니다");
				System.out.print("칸을 선택하세요(1~9) : ");
				userSelect = sc.nextInt() - 1;
			}
			// 유저 선택이 이미 o나 x가 있다면 다시 선택하게 함
			while (maps[userSelect] != '_') {
				System.out.println("이미 선택된 칸입니다");
				System.out.print("칸을 선택하세요(1~9) : ");
				userSelect = sc.nextInt() - 1;
			}
			// maps의 유저 선택 번째에 o 표시
			maps[userSelect] = 'o';
			
			// isWin 함수에 o를 인수로 전달해서 o가 이겼는지 판별
			if (isWin('o')) {
				printMap();
				System.out.println("당신이 승리했습니다!");
				break;
			}
			
			// 컴퓨터 선택을 getRandom 함수(남은 빈칸 중 랜덤)로 설정
			comSelect = getRandom();
			// 만약 10이라면 무승부 처리
			if (comSelect == 10) {
				printMap();
				System.out.println("무승부입니다!");
				break;
			}
			// 받은 랜덤 컴퓨터 선택을 맵에 x로 표시
			maps[comSelect] = 'x';
			
			// isWin 함수에 x를 인수로 전달해서 x가 이겼는지 판별
			if (isWin('x')) {
				printMap();
				System.out.println("컴퓨터가 승리했습니다!");
				break;
			}
		}
	}
	
	static void printMap() {
		// maps의 n번째 칸을 하나하나 출력
		System.out.println("[" + maps[0] + "][" + maps[1] + "][" + maps[2] + "]");
		System.out.println("[" + maps[3] + "][" + maps[4] + "][" + maps[5] + "]");
		System.out.println("[" + maps[6] + "][" + maps[7] + "][" + maps[8] + "]");
	}
	
	static int getRandom() {
		// 랜덤 시작 전 컴퓨터 선택을 유저 선택으로 저장(중복을 일부러 만들어서 무조건 랜덤을 실행할 수 있도록)
		int result = userSelect;
		// 맵이 다 찼는지 판별하기 위해 endCount 생성
		int endCount = 0;
		// 빈칸을 저장하기 위한 변수 blank 생성
		int blank = 0;
		// 총 9번 반복
		for (int i = 0; i < 9; i ++) {
			// 맵의 i번째가 빈칸이 아니라면 endCount 증가
			if (maps[i] != '_') {
				endCount ++;
			// 아니라면 빈칸을 i로 저장
			} else {
				blank = i;
			}
		}
		// 컴퓨터가 선이라면 endCount는 항상 짝수, 마지막 칸이 남았을 때가 8, 마지막 선택자는 컴퓨터
		// 유저가 선이라면 endCount는 항상 홀수, 마지막 칸이 남았을 때가 9, 마지막 선택자는 유저
		if (endCount == 9 || endCount == 8) {
			// 만약 컴퓨터가 선이라면 남은 빈칸을 x로 표시
			if (first.equals("com")) {
				maps[blank] = 'x';
			}
			// 이 함수가 실행되려면 아직까지 아무도 이기지 않았기 때문에 10 전달(무승부)
			return 10;
		} else {
			// maps의 랜덤 번째가 빈칸일 때까지 랜덤 생성
			while (maps[result] != '_') {
				result = (int) (Math.random() * 9);
			}
			// 생성 값을 전달
			return result;
		}
	}
	
	// isWin 함수 (o나 x를 매개변수로 받음)
	static boolean isWin(char player) {
		// 한줄이 같은지 판별하기 위해 연속 칸 개수를 변수로 생성
		int curCount = 0;
		// 총 3번 반복 (0, 3, 6)
		for (int i = 0; i <= 6; i += 3) {
			// 반복할 때마다 연속 칸 개수 초기화
			curCount = 0;
			// 총 3번 반복 (0, 1, 2)
			for (int j = 0; j <= 2; j ++) {
				// maps의 i + j번째(0, 1, 2 | 3, 4, 5 | 6, 7, 8)가 player(매개변수: o, x)인지
				if (maps[i + j] == player) {
					// 연속 칸 개수 증가
					curCount ++;
				}
			}
			// 연속 칸 개수가 3이라면 true(이김) 전달
			if (curCount == 3) {
				return true;
			}
		}
		// 총 3번 반복 (0, 1, 2)
		for (int i = 0; i <= 2; i ++) {
			// 반복할 때마다 연속 칸 개수 초기화
			curCount = 0;
			// 총 3번 반복 (0, 3, 6)
			for (int j = 0; j <= 6; j += 3) {
				// maps의 i + j번째(0, 3, 6 | 1, 4, 7 | 2, 5, 8)가 player(매개변수: o, x)인지
				if (maps[i + j] == player) {
					// 연속 칸 개수 증가
					curCount ++;
				}
			}
			// 연속 칸 개수 3이라면 true(이김) 전달
			if (curCount == 3) {
				return true;
			}
		}
		// 대각선이 player(매개변수: o, x)이면 true(이김) 전달
		if (maps[0] == maps[4] && maps[4] == maps[8] && maps[8] == player) {
			return true;
		}
		// 대각선이 player(매개변수: o, x)이면 true(이김) 전달
		if (maps[2] == maps[4] && maps[4] == maps[6] && maps[6] == player) {
			return true;
		}
		// 다 아니면 false(안이김) 전달 (위에서 먼저 return을 하면 그 이후 코드는 실행이 안되기 때문에)
		return false;
	}

}
