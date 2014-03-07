package sosu;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Sosu s = Sosu.get();
		World world = new World();
		s.loadWorld(world);
		s.init();
		s.start();
	}

}
