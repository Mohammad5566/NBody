/**
 * @author Mohammad Mahmud
 * This class represents a body in space.
 * A body consists of Cartesian coordinates, velocities,
 * mass, and a string path to the image of the body.
 */

public class Body {
	public  double xxPos;
	public  double yyPos;
	public  double xxVel;
	public  double yyVel;
	public  double mass;
	public  String imgFileName;

	public Body(double xP, double yP, double xV, double yV, 
								double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	
	}
	/*Calculates the radius between this body and given body*/
	public double calcDistance(Body b) {
		double xDif = Math.pow(this.xxPos - b.xxPos, 2);
		double yDif = Math.pow(this.yyPos - b.yyPos, 2);
		return Math.sqrt(xDif + yDif);
	}

	/*Calculates force exerted on this body by given body*/
	public double calcForceExertedBy(Body b) {
		if (this.equals(b)) {
			return 0;
		}
		double G = 6.67E-11;
		return (G * this.mass * b.mass)/Math.pow(calcDistance(b), 2);
	}

	/*Calculates force of X direction exerted on this body by given body*/
	public double calcForceExertedByX(Body b) {
		double xForce = calcForceExertedBy(b) * (this.xxPos - b.xxPos) * -1;
		return xForce / calcDistance(b);
	}

	/*Calculates force of Y direction exerted on this body by given body*/
	public double calcForceExertedByY(Body b) {
		double yForce = calcForceExertedBy(b) * (this.yyPos - b.yyPos) * -1;
		return yForce / calcDistance(b);
	}

	/*Calculates net force of X direction exerted by given bodies on this body*/
	public double calcNetForceExertedByX(Body[] bodies) {
		double netForce = 0;
		for(Body b : bodies) {
			if (!this.equals(b)) netForce += calcForceExertedByX(b);
		}
		return netForce;
	}

	/*Calculates net force of Y direction exerted by given bodies on this body*/
	public double calcNetForceExertedByY(Body[] bodies) {
		double netForce = 0;
		for(Body b : bodies) {
			if (!this.equals(b)) {
				netForce += calcForceExertedByY(b);
			}
		}
		return netForce;
	}

	/*Calculates new position of this body based on given time and forces*/
	public void update(double dt, double fX, double fY) {
		double accX = fX / mass;
		double accY = fY / mass;
		xxVel = xxVel + dt * accX;
		yyVel = yyVel + dt * accY;
		xxPos = xxPos + dt * xxVel;
		yyPos = yyPos + dt * yyVel;
	}

	/*Draws this body to canvas using position and body image*/
	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}


}