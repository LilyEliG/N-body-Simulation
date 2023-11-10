import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class NBody {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		double big_t = Double.parseDouble(args[0]);
		double delta_t = Double.parseDouble(args[1]);

		String resourceFolder = "resources/";
		String fileName = resourceFolder + args[2];
		FileInputStream fileInputStream = new FileInputStream(fileName);
		System.setIn(fileInputStream);

		// Use StdIn to read from the file
		int numBodies = StdIn.readInt();
		double universeRadius = StdIn.readDouble();

		// Create holders for the information for each body
		double[] xPos = new double[numBodies];
		double[] yPos = new double[numBodies];
		double[] xVel = new double[numBodies];
		double[] yVel = new double[numBodies];
		double[] mass = new double[numBodies];
		String[] pic = new String[numBodies];
		
		// Initialize the information needed for each body
		for(int i = 0; i < numBodies; ++i)
		{
			xPos[i] = StdIn.readDouble();
			yPos[i] = StdIn.readDouble();
			xVel[i] = StdIn.readDouble();
			yVel[i] = StdIn.readDouble();
			mass[i] = StdIn.readDouble();
			pic[i] = StdIn.readString();
		}
		
		// Create the universe
		StdDraw.setXscale(-universeRadius, universeRadius);
		StdDraw.setYscale(-universeRadius, universeRadius);
				
		
		double time = 0;
		while (time < big_t) // start while loop
		{
			StdAudio.play(resourceFolder + "2001.wav");
			StdDraw.picture(0, 0, resourceFolder + "starfield.jpg");
			
			// Calculate the total force acting on each body
			for (int i = 0; i < numBodies; ++i)
			{
				double Fx = 0;
				double Fy = 0;
				
				// Calculating the constituent force acting on each body
				for (int j = 0; j < numBodies; ++j)
				{
					if (i != j) // If an object is not itself
					{
						double deltaX = xPos[j] - xPos[i];
						double deltaY = yPos[j] - yPos[i];
						double R = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
						double F = 6.67E-11 * mass[i] * mass[j] / (R * R);
						Fx += (F * deltaX / R);
						Fy += (F * deltaY / R);
					}
				}
				
				double ax = Fx / mass[i];
				double ay = Fy / mass[i];
				
				xVel[i] += ax * delta_t;
				yVel[i] += ay * delta_t;
				
				xPos[i] += xVel[i] * delta_t;
				yPos[i] += yVel[i] * delta_t;
				
				StdDraw.picture(xPos[i], yPos[i], resourceFolder + pic[i]);
			}
			
			StdDraw.show(5);	
	
			time += delta_t;
		} // end while loop
		
		// print the end state of the universe
		for(int i = 0; i < numBodies; ++i)
		{
			System.out.print("\n" + xPos[i] + " " + yPos[i] + xVel[i] + yVel[i]);
			System.out.print(" " + mass[i] + " " + pic[i]);
		}
	}
}