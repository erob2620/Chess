package Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Exceptions.CheckException;
import Exceptions.CheckMateException;
import Exceptions.InvalidMoveException;
import Exceptions.PieceBlockedException;
import Exceptions.WrongTurnException;


public class Controller {
	public ChessBoard board;
	private List<String> commands;
	private static boolean isVerbose = false;
	private static String file = "";
	public static void main(String[] args) {
		Controller c = new Controller();
		for(String s : args) {
			if(s.equalsIgnoreCase("v")) {
				isVerbose = true;
			}
			if(s.endsWith("txt")) {
				file = s;
			}
		}
		if(file.isEmpty()){
			c.Run();
			
		} else {
			c.Run(args);
		}
		
	}
	
	public void Run() {
		board = new ChessBoard();
		Scanner scan = new Scanner(System.in);
		commands = new ArrayList<String>();
		FileIO fio = new FileIO(board);
		while(true) {
			String input = scan.nextLine();
			if(input.isEmpty()) {
				break;
			}
			if(isVerbose) {
				try {
					System.out.println(fio.parse(input) + "\n");
				}catch (CheckMateException e) {
					System.out.println(e.getMessage() + "\n");
					board.printBoard();
					break;
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
				board.printBoard();
			} else {
				try {
					fio.parse(input);
				}catch (CheckMateException e) {
					System.out.println(e.getMessage());
					break;
				}catch (Exception e) {
					commands.add(e.getMessage());
				}
			}
		}
		if(!isVerbose) {
			for(String s : commands) {
				System.out.println(s);
			}
			board.printBoard();
		}
		
	}
	public void Run(String[] args) {
		File chessInput = new File(file);
		commands = new ArrayList<String>();
		board = new ChessBoard();
		FileIO fio = new FileIO(board);
		try(Scanner read = new Scanner(chessInput)) {
			while(read.hasNext()) {
				if(isVerbose) {
					try {
						System.out.println(fio.parse(read.nextLine()) + "\n");
					} catch (CheckMateException e){
						System.out.println(e.getMessage() + "\n");
						board.printBoard();
						break;
					}catch (CheckException | WrongTurnException | InvalidMoveException | PieceBlockedException e) {
						System.out.println(e.getMessage() + "\n");
					} 
					board.printBoard();
				} else {
					try {
						fio.parse(read.nextLine());
					}catch (CheckMateException e){
						System.out.println(e.getMessage());
						break;
					}catch (Exception e) {
						commands.add(e.getMessage());
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(!isVerbose) {
				for(String s : commands) {
					System.out.println(s);
				}
				board.printBoard();
			}
		}
	}

}
