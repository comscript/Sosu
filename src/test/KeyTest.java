package test;

import sosutu.Sosuent;
import sosutu.Sosutu;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class KeyTest extends Sosuent{

	private static String[][] bindings = {{"RETURN", "enterPressed", "down"},
										  {"SPACE", "spacePressed", "down"},
										  {"E", "ePressed", "up"}};
	public String name;
	public KeyTest(String name){
		this.name = name;
	}
	public void enterPressed(){
		System.out.println("Enter was pressed for "+name);
	}

	public void spacePressed(){
		System.out.println("Space was pressed for "+name);
	}

	public void ePressed(){
		System.out.println("E was pressed for "+name);
	}

	public static void main(String [] argv){
		
		for (String[] binding:bindings){
			Sosutu.get().addBinding(binding[0], binding[1], binding[2]);
		}
		KeyTest test = new KeyTest("First");
		KeyTest test2 = new KeyTest("Second");
		while(true){

			Sosutu.get().checkKeys();
			Display.update();
			try{
				Thread.sleep(10);
			}catch (Exception e){}
		}
	}
}