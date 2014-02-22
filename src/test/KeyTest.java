package test;

import sosutu.Sosuent;
import sosutu.Sosutu;
import sosutu.World;

public class KeyTest extends Sosuent{

	private static String[][] bindings = {{"RETURN", "enterPressed"},
										  {"SPACE", "spacePressed"},
										  {"E", "ePressed"},
										  {"BUTTON0", "leftMouse"},
										 };
	public String name;
	public KeyTest(){
	}
	
	public void leftMouse(boolean pressed){
		if(pressed)
			System.out.println("Left Mouse Button was pressed");
		else
			System.out.println("Left Mouse Button was released");
	}
	public void enterPressed(boolean pressed){
		if(pressed)
			System.out.println("Enter was pressed");
		else
			System.out.println("Enter was released");
	}

	public void spacePressed(boolean pressed){
		if(pressed)
			System.out.println("Space was pressed");
		else
			System.out.println("Space was released");
	}

	public void ePressed(boolean pressed){
		if(pressed)
			System.out.println("E was pressed");
		else
			System.out.println("E was released");
	}

	public static void main(String [] argv){
		Sosutu sosutu = Sosutu.get();
		sosutu.init();
		World testworld = new World();
		sosutu.setWorld(testworld);
		for (String[] binding:bindings){
			testworld.addBinding(binding[0], binding[1]);
		}
		KeyTest test = new KeyTest();
		sosutu.loop();
		
	}
}