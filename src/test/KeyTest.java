package test;

import sosutu.SosuKey;
import sosutu.Sosuent;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
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
		try {
			Display.create();
		} catch (LWJGLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SosuKey.init(bindings);

		KeyTest test = new KeyTest();

		while(true){

			SosuKey.getInstance().checkKeys();
			Display.update();
			try{
				Thread.sleep(100);
			}catch (Exception e){}
		}
	}
}