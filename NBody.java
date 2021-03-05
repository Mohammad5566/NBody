public class NBody {

	/*Reads in the radius from a given file.*/
	public static double readRadius(String file) {
		In in = new In(file);
		in.readDouble();
		return in.readDouble();
	}

	/*Reads in a list of bodies from given file.*/
	public static Body[] readBodies(String file) {
		In in = new In(file);
		int numOfBodies = in.readInt();
		Body[] bodies = new Body[numOfBodies];
		in.readDouble();
		for (int i = 0; i < numOfBodies; i += 1) {
			double xPos = in.readDouble();
			double yPos = in.readDouble();
			double xVel = in.readDouble();
			double yVel = in.readDouble();
			double mass = in.readDouble();
			String img = in.readString();
			bodies[i] = new Body(xPos, yPos, xVel, yVel, mass, img);
		}
		return bodies;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double universeRadius = readRadius(filename);
		Body[] bodies = readBodies(filename);

		StdDraw.enableDoubleBuffering();

		StdDraw.setScale(-universeRadius, universeRadius);
		StdDraw.picture(-200, -200, "images/starfield.jpg");

		for (Body b : bodies) {
			b.draw();
		}

		/*
		loop through simulation and calculate the x and y
		force exerted on each body by every other body
		Use theses forces to update bodies' location, and draw
		bodies onto universe.
		 */
		for (int time = 0; time <= T; time += dt) {
			double[] xForce = new double[bodies.length];
			calculateNetForceX(xForce, bodies);
			double[] yForce = new double[bodies.length];
			calculateNetForceY(yForce, bodies);

			//update each body with new position, velocity, and acceleration
			for (int i = 0; i < xForce.length; i += 1) {
				bodies[i].update(dt, xForce[i], yForce[i]);
			}

			StdDraw.picture(-200, -200, "images/starfield.jpg");
			for (Body b : bodies) b.draw();
			StdDraw.show();
			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", universeRadius);
		for (int i = 0; i < bodies.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
					bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
					bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
		}

	}

	/*Calculates the net X force exerted on each body by all other bodies*/
	public static void calculateNetForceX(double[] force, Body[] bodies) {
		for (int i = 0; i < bodies.length; i += 1) {
			double netForce = bodies[i].calcNetForceExertedByX(bodies);
			force[i] = netForce;
		}

	}

	/*Calculates the net Y force exerted on each body by all other bodies*/
	public static void calculateNetForceY(double[] force, Body[] bodies) {
		for (int i = 0; i < bodies.length; i += 1) {
			double netForce = bodies[i].calcNetForceExertedByY(bodies);
			force[i] = netForce;
		}
	}

}