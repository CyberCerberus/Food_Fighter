package main;

import javax.swing.JFrame;

import gameCore.SQL;


public class FoodFighter {

	public static void main(String[] args) {
		SQL.verify();		
		JFrame window = new JFrame("Food Fighter");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		window.setLocationRelativeTo(null);

	}

}
