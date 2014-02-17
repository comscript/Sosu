package test;

import sosutu.Sosuent;
import sosutu.Sosutu;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

public class KeyTest extends Sosuent{

	private static String[][] bindings = {{"RETURN", "enterPressed"},
										  {"SPACE", "spacePressed"},
										  {"E", "ePressed"}};

	public void enterPressed(){
		System.out.println("Enter was pressed!");
	}

	public void spacePressed(){
		System.out.println("Space was pressed!");
	}

	public void ePressed(){
		System.out.println("E was pressed!");
	}

	public static void main(String [] argv){
		
		for (String[] binding:bindings){
			Sosutu.get().addBinding(binding[0], binding[1]);
		}
		KeyTest test = new KeyTest();
		while(true){

			Sosutu.get().checkKeys();
			Display.update();
			try{
				Thread.sleep(100);
			}catch (Exception e){}
		}
	}
}