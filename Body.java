
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
	public double calcDistance(Body b) {
		double xDif = Math.pow(this.xxPos - b.xxPos, 2);
		double yDif = Math.pow(this.yyPos - b.yyPos, 2);
		return Math.sqrt(xDif + yDif);
	}

	public double calcForceExertedBy(Body b) {
		if (this.equals(b)) {
			return 0;
		}
		double G = 6.67E-11;
		return (G * this.mass * b.mass)/Math.pow(calcDistance(b), 2);
	}

	public double calcForceExertedByX(Body b) {
		double xForce = calcForceExertedBy(b) * (this.xxPos - b.xxPos) * -1;
		return xForce / calcDistance(b);
	}

	public double calcForceExertedByY(Body b) {
		double yForce = calcForceExertedBy(b) * (this.yyPos - b.yyPos) * -1;
		return yForce / calcDistance(b);
	}

	public double calcNetForceExertedByX(Body[] bodies) {
		double netForce = 0;
		for(Body b : bodies) {
			if (!this.equals(b)) netForce += calcForceExertedByX(b);
		}
		return netForce;
	}

	public double calcNetForceExertedByY(Body[] bodies) {
		double netForce = 0;
		for(Body b : bodies) {
			if (!this.equals(b)) {
				netForce += calcForceExertedByY(b);
			}
		}
		return netForce;
	}

	public void update(double dt, double fX, double fY) {
		double accX = fX / mass;
		double accY = fY / mass;
		xxVel = xxVel + dt * accX;
		yyVel = yyVel + dt * accY;
		xxPos = xxPos + dt * xxVel;
		yyPos = yyPos + dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}


}