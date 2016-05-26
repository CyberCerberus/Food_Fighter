import java.util.Scanner;

import gameCore.Play;
import gameCore.SQL;


public class FoodFighter {

	public static void main(String[] args) {
		SQL.verify();
		Scanner kb = new Scanner(System.in);
		int choice = 0;
		System.out.println("Welcome to...\nSUPER FOOD FIGHTER 4!!!");
		while(choice != 3) {
			System.out.println("Choose an option\n1. Start\n2. Load\n3. Exit");
			choice = kb.nextInt();
			kb.nextLine();
			if(choice == 1) {
				Play.playFromStart(kb);
			}
			else if(choice == 2) {
				Play.load(kb);
			}
		}
		System.out.println("Later");
		kb.close();
	}

}
