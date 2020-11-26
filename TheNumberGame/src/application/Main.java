
package application;
	
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	
	ArrayList<GameData> data = new ArrayList<>();
	
	
	private int counter = 0;
	private int target = 0;
	
	// create root container 
	GridPane root = new GridPane();
	
	
	private void newGame() //clear game 
	{
		counter = 0;
		target = (int) (1 + Math.random()*100);
		
	
		
		//for each node in root
		
		for( Node n : root.getChildren())
		{
			if(n instanceof TextField) // if TextField then keep "Your guess"
			{
				
				((TextField)n).setText("Your guess");
			}
			
			else if (n instanceof Text && ((Text)n).getText().equals("Please enter your guess!"))
			{				
				((Text)n).setText("Please enter your guess!");
			}
			
			else if (n instanceof Text && ((Text)n).getText().contains("Too Low"))
			{				
				((Text)n).setText(" "); //clear comment
			}
			
			else if (n instanceof Text && ((Text)n).getText().contains("Too High"))
			{				
				((Text)n).setText(" "); //clear comment
			}
			
			else if (n instanceof Text && ((Text)n).getText().contains("Correct"))
			{				
				((Text)n).setText(" "); //clear comment
			}
			else if (n instanceof Text && ((Text)n).getText().contains("Good Luck"))
			{				
				((Text)n).setText(" "); //clear comments
			}
			
			else if (n instanceof Text && ((Text)n).getText().equals("Number Guessing Game"))
			{				
				((Text)n).setText("Number Guessing Game"); //keep header
			}
		}
		
		System.out.println("Target = " + target);
		
	}
	
	
	private Node getField(int flag, Color c) // flag which textfield and text to use, separate text by colour
	{
		
		
		for( Node n : root.getChildren())
		{
			if(n instanceof TextField && flag == 1)
				return n;
			else if (n instanceof Text && flag == 2 && ((Text)n).getFill() == c) 
				return n;
				
		}
		return null;
		
	}
	
	private void playGame()
	{ 
		Text comment = (Text)getField(2, Color.IVORY); //write comment
	
		int input = Integer.parseInt(((TextField)getField(1, null)).getText()); //get & translate textfield to int
	
		if(input == target) //if correct
		{			
			counter++;
			comment.setText("Correct! You had " + counter +" attempts.");
			
			data.add(new GameData(target, counter));
			
		}
		else if (input > target) //if too high
		{
			comment.setText("Your Guess Is Too High.");
			counter++;
		}
		else //if too low
		{
			comment.setText("Your Guess Is Too Low.");
			counter++;
		}
		
		if(counter >= 5 && input != target) //set minimum attempt to 5
		{
			comment.setText("You have reached minimum of 5 attempts. Solution was " + target + ". Good Luck Next Time.");
			data.add(new GameData(target, counter)); //add to GameData to save
		}
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			newGame();
						
			
			//set up GridPane
			root.setAlignment(Pos.CENTER);
			root.setMinSize(500, 300);
			root.setPadding(new Insets(25,25,25,25));
			root.setVgap(5);
			root.setHgap(5);
			
			//Create button
			Button Button1 = new Button("New Game");
			Button Button2 = new Button("Save Score");
			Button Button3 = new Button("Load Score");
			
			//Header
			DropShadow ds = new DropShadow();
			ds.setOffsetY(3.0f);
			ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
			 
			Text t = new Text();
			t.setEffect(ds);
			t.setCache(true);
			t.setX(10.0f);
			t.setY(270.0f);
			t.setFill(Color.DEEPPINK);
			t.setText("NUMBER GUESSING GAME");
			t.setFont(Font.font(null, FontWeight.BOLD, 32));
			
			//Instruction
			Text instruction = new Text("Please enter your guess!");
			TextField inputField = new TextField("Your guess");
			
			//Comment
			Text comment = new Text(" ");
			
			
			Button Button4 = new Button("Play");
			
			
			root.add(t,0,0); //header
			root.add(Button1,0,5); //clear screen
			root.add(Button2,0,7); //save score
			root.add(Button3,0,9); //load score
			root.add(instruction,0,11);
			root.add(inputField,0,13);
			root.add(Button4,0,15); //Play
			root.add(comment,0,17);
			
			
					
			root.setStyle("-fx-background-image:url(\"file:Resources/pinkwallpaper.jpg\");");
			
			//Clear screen
			Button1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> newGame());
			
			//play game
			Button4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> playGame());
			
			inputField.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> inputField.selectAll());
			
			//Save
			Button2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> saveData());
			
			//Load
			Button3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> loadData());
			
			comment.setFill(Color.IVORY); //set comment to Ivory, to call this field in play, save, load method
			
			Scene scene = new Scene(root,480,480);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void loadData()
	{
		Text comment = (Text)getField(2, Color.IVORY);
		try {
			
			Scanner sc= new Scanner(new File("gameData.text"));
			while (sc.hasNextInt()) 
			{
			   data.add(new GameData(sc.nextInt(), sc.nextInt()));
			   sc.nextLine();
			}
			sc.close();
			
			comment.setText("Game data loaded successfuly.");			
		} 
		catch (IOException e) {
			comment.setText("File Error.");
			}
	}
	
	private void saveData()
	{
		Text comment = (Text)getField(2, Color.IVORY);
		
		try
		{	
			java.io.File outfile = new java.io.File("gameData.text");			
			PrintStream out = new PrintStream(outfile);
			for(GameData p : data)
			{
				out.println(p.getRandomNumber()+ " " + p.getAttempts());
			}
			out.close();
			comment.setText("Game Data saved successfuly");
			
		} 
		catch (IOException e) {
			comment.setText("File Error.");
			}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
