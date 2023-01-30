package com.decide;
import java.lang.Math;

class Decide {

	// TYPE DECLARATIONS
    enum CONNECTORS {
        NOTUSED,
        ORR,
        ANDD
    }

    // CONSTANT
    final static double PI = 3.1415926535;

    //Input parameters to the DECIDE() function
    public double LENGTH1;      // Length in LICs 0, 7, 12
    public double RADIUS1;      // Radius in LICs 1, 8, 13
    public double EPSILON;      // Deviation from P1 in LICs 2, 9
    public double AREA1;        // Area in LICs 3, 10, 14
    public int Q_PTS;           // No. of consecutive points in LIC 4
    public int QUADS;           // No. of quadrants in LIC 4
    public double DIST;         // Distance in LIC 6
    public int N_PTS;           // No. of consecutive pts. in LIC 6
    public int K_PTS;           // No. of int. pts. in LICs 7, 12
    public int A_PTS;           // No. of int. pts. in LICs 8, 13
    public int B_PTS;           // No. of int. pts. in LICs 8, 13
    public int C_PTS;           // No. of int. pts. in LIC 9
    public int D_PTS;           // No. of int. pts. in LIC 9
    public int E_PTS;           // No. of int. pts. in LICs 10, 14
    public int F_PTS;           // No. of int. pts. in LICs 10, 14
    public int G_PTS;           // No. of int. pts. in LIC 11
    public double LENGTH2;      // Maximum length in LIC 12
    public double RADIUS2;      // Maximum radius in LIC 13
    public double AREA2;        // Maximum area in LIC 14

    public int NUMPOINTS;
    public double[] X = new double[100];
    public double[] Y = new double[100];
    public CONNECTORS[][] LCM = new CONNECTORS[15][15];
    public boolean[] PUV = new boolean[15];


    //Constructor for the decide class
    public Decide(double length1, double radius1, double epsilon, double area1, 
                int q_pts, int quads, double dist, int n_pts, int k_pts, int a_pts,
                int b_pts, int c_pts, int d_pts, int e_pts, int f_pts, int g_pts,
                double length2, double radius2, double area2){

        LENGTH1 = length1;      // Length in LICs 0, 7, 12
        RADIUS1 = radius1;      // Radius in LICs 1, 8, 13
        EPSILON = epsilon;      // Deviation from P1 in LICs 2, 9
        AREA1 = area1;          // Area in LICs 3, 10, 14
        Q_PTS = q_pts;          // No. of consecutive points in LIC 4
        QUADS = quads;          // No. of quadrants in LIC 4
        DIST = dist;            // Distance in LIC 6
        N_PTS = n_pts;          // No. of consecutive pts. in LIC 6
        K_PTS = k_pts;          // No. of int. pts. in LICs 7, 12
        A_PTS = a_pts;          // No. of int. pts. in LICs 8, 13
        B_PTS = b_pts;          // No. of int. pts. in LICs 8, 13
        C_PTS = c_pts;          // No. of int. pts. in LIC 9
        D_PTS = d_pts;          // No. of int. pts. in LIC 9
        E_PTS = e_pts;          // No. of int. pts. in LICs 10, 14
        F_PTS = f_pts;          // No. of int. pts. in LICs 10, 14
        G_PTS = g_pts;          // No. of int. pts. in LIC 11
        LENGTH2 = length2;      // Maximum length in LIC 12
        RADIUS2 = radius2;      // Maximum radius in LIC 13
        AREA2 = area2;          // Maximum area in LIC 14

    }

    /**
	 * Generates a boolean signal which determines whether an interceptor should be launched based
	 * on the current state of this object.
	 */
    public void DECIDE() {
        boolean[] cmv = new boolean[15];
        cmv[0]  =  LIC0(NUMPOINTS, X, Y, LENGTH1);
        cmv[1]  =  LIC1(NUMPOINTS, X, Y, RADIUS1);
        cmv[2]  =  LIC2(NUMPOINTS, X, Y, EPSILON);
        cmv[3]  =  LIC3(NUMPOINTS, X, Y, AREA1);
        cmv[4]  =  LIC4(NUMPOINTS, X, Y, Q_PTS, QUADS);
        cmv[5]  =  LIC5(NUMPOINTS, X, Y);
        cmv[6]  =  LIC6(NUMPOINTS, X, Y, N_PTS, DIST);
        cmv[7]  =  LIC7(NUMPOINTS, X, Y, K_PTS, LENGTH1);
        cmv[8]  =  LIC8(NUMPOINTS, X, Y, A_PTS, B_PTS, RADIUS1);
        cmv[9]  =  LIC9(NUMPOINTS, X, Y, C_PTS, D_PTS, EPSILON);
        cmv[10] = LIC10(NUMPOINTS, X, Y, E_PTS, F_PTS, AREA1);
        cmv[11] = LIC11(NUMPOINTS, X, Y, G_PTS);
        cmv[12] = LIC12(NUMPOINTS, X, Y, K_PTS, LENGTH1, LENGTH2);
        cmv[13] = LIC13(NUMPOINTS, X, Y, A_PTS, B_PTS, RADIUS1, RADIUS2);
        cmv[14] = LIC14(NUMPOINTS, X, Y, E_PTS, F_PTS, AREA1, AREA2);
        boolean[][] pum = new boolean[15][15];
        for (int i = 0; i < 15; ++i) {
        	for (int j = 0; j < 15; ++j) {
        		if      (LCM[i][j] == CONNECTORS.ANDD)    pum[i][j] = cmv[i] && cmv[j];
        		else if (LCM[i][j] == CONNECTORS.ORR)     pum[i][j] = cmv[i] || cmv[j];
        		else if (LCM[i][j] == CONNECTORS.NOTUSED) pum[i][j] = true;
        	}
        }
        boolean[] fuv = new boolean[15];
        for (int i = 0; i < 15; ++i) {
        	fuv[i] = !PUV[i];
        	if (!fuv[i]) {
        		fuv[i] = true;
        		for (int j = 0; j < 15 && fuv[i]; ++j) {
        			fuv[i] &= pum[i][j];
        		}
        	}
        }
        boolean launch = true;
        for (int i = 0; i < 15; ++i) {
        	launch &= fuv[i];
        }
        if (launch) System.out.println("YES");
        else        System.out.println("NO");
    }


    // HERE starts the LIC implementations -------------

    // 15 Launch Interceptor Conditions
	// Input: 100 planar data points

	/**
	 * There exists at least one set of two consecutive data points that are a distance greater than the length, LENGTH1, apart.
	 * 0 ≤ LENGTH1
	 */
	public boolean LIC0(int numPoints, double[] x, double[] y, double length1) {
		double x1, y1, x2, y2, d;
		if (length1 < 0) {
			return false;
		}
		for (int i = 1; i < numPoints; i++) {
			x1 = x[i-1]; y1 = y[i-1];
			x2 = x[i]; y2 = y[i];
			d = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
			if (d > length1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * There exists at least one set of three consecutive data points that 
	 * cannot all be contained within or on a circle of radius RADIUS1.
	 * 
	 * 0 ≤ RADIUS1
	 */
	public boolean LIC1(int numPoints, double[] x, double[] y, double radius1) {
		if (radius1 < 0) {
			return false;
		}
		for (int i = 0; i <= numPoints - 3; i++) {
			int j = i + 1, k = i + 2;
			double[][] points = {{x[i], y[i]}, {x[j], y[j]}, {x[k], y[k]}};
			if (!containedInCircle(points, radius1)) {
				return true;
			}
		}
		return false;
	}

	//There exists at least one set of three consecutive data points which form an angle such that:
	//angle < (PI − EPSILON) or angle > (PI + EPSILON)
	//The second of the three consecutive points is always the vertex of the angle. If either the first point or the last point (or both) coincides with the vertex, the angle is undefined and the LIC is not satisfied by those three points.
	//0 ≤ EPSILON < PI
	public boolean LIC2 (int NumPoints , double[] X , double[] Y , double Epsilon){
		double x1 , y1 , x2 , y2 , x3 , y3 , Distance1 , Distance2 , Angle;
		for(int i = 2 ; i < NumPoints ; i++) {
			x1 = X[i-2];
			y1 = Y[i-2];
			x2 = X[i-1];
			y2 = Y[i-1];
			x3 = X[i];
			y3 = Y[i];
			if ( ! (x1 == x2 & y1 == y2) & ! (x1 == x3 & y1 == y3) & ! (x3 == x2 & y3 == y2) ){
				Distance1 = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
				Distance2 = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
				Angle = Math.acos( ( (x1-x2)*(x1-x3) + (y1-y2)*(y1-y3) ) / (Distance1 * Distance2));
				if ( Angle < (Math.PI - Epsilon) | Angle > (Math.PI + Epsilon)){
					return true;
				}
			}
		}
		return false;
	}

	//There exists at least one set of three consecutive data points that are the vertices of a triangle with area greater than AREA1.
	//0 ≤ AREA1
	public boolean LIC3 (int NumPoints , double[] X , double[] Y , double Area1){
		//S = 1/2 * (x1y2+x2y3+x3y1-x1y3-x2y1-x3y2)
		double x1 , y1 , x2 , y2 , x3 , y3 , Area;
		for(int i = 2 ; i < NumPoints ; i++) {
			x1 = X[i-2];
			y1 = Y[i-2];
			x2 = X[i-1];
			y2 = Y[i-1];
			x3 = X[i];
			y3 = Y[i];
			Area = 0.5 * (x1*y2 + x2*y3 + x3*y1 - x1*y3 - x2*y1 - x3*y2);
			if(Area > Area1){
				return true;
			}
		}
		return false;
	}

	//There exists at least one set of Q PTS consecutive data points that lie in more than QUADS quadrants. Where there is ambiguity as to which quadrant contains a given point, priority of decision will be by quadrant number, i.e., I, II, III, IV. For example, the data point (0,0) is in quadrant I, the point (-l,0) is in quadrant II, the point (0,-l) is in quadrant III, the point (0,1) is in quadrant I and the point (1,0) is in quadrant I.
	//2 ≤ Q PTS ≤ NUMPOINTS, 1 ≤ QUADS ≤ 3
	public boolean LIC4 (int numPoints, double[] X, double[] Y, int q_pts, int quads){
		//conditions on input variables
		if(q_pts < 2 || q_pts > numPoints || quads < 1 || quads > 3 || quads >= q_pts) return false;

		//Contains number of points in each quadrant for each q_pts-interval
		int[] quadrants = {0, 0, 0, 0};

		//check the first q_pts
		for(int i = 0; i < q_pts; i++){
			quadrants[getQuadrant(X[i], Y[i]) - 1]++; //increment quadrants array on quadrant index
			if(checkNumberOfQuads(quadrants, quads)) return true;
		}
		//update number of points in quadrant and check
		for(int i = q_pts; i < numPoints; i++){
			quadrants[getQuadrant(X[i], Y[i]) - 1]++; //increment quadrants array on quadrant index
			quadrants[getQuadrant(X[i-q_pts], Y[i-q_pts]) - 1]--; //decrement quadrants array on quadrant index
			if(checkNumberOfQuads(quadrants, quads)) return true; 
		}
		return false;
	}

	//checks whether there are points in more than quads quadrants
	private boolean checkNumberOfQuads(int quadrants[], int quads){
		int numQuadrants = 0;

		for(int i = 0; i < quadrants.length; i++){
			if(quadrants[i] > 0) numQuadrants++;
			if(numQuadrants > quads) return true;
		}

		return false;
	}

	//function to return quadrant number
	private int getQuadrant(double x, double y){
		if(x >= 0 && y >= 0) return 1;
		if(x < 0 && y >= 0) return 2;
		if(x <= 0 && y < 0) return 3;
		if(x > 0 && y < 0) return 4;

		return -1;
	}
 
	//There exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), such that X[j] - X[i] < 0. (where i = j-1)
	public boolean LIC5 (int NumPoints , double[] X , double[] Y ){
		double x1 , x2;
		for(int i = 1 ; i < NumPoints ; i ++) {
			x1 = X[i-1];
			x2 = X[i];
			if (x1 > x2){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * There exists at least one set of N_PTS consecutive data points such that at least one of the 
	 * points lies a distance greater than DIST from the line joining the first and last of these N_PTS points. 
	 * 
	 * If the first and last points of these N_PTS are identical, then the calculated distance to compare 
	 * with DIST will be the distance from the coincident point to all other points of the N_PTS consecutive points. 
	 * 
	 * The condition is not met when NUMPOINTS < 3. 
	 * 3 ≤ N_PTS ≤ NUMPOINTS , 0 ≤ DIST
	 * 
	 * @param n_pts Number of consecutive points to evaluate.
	 * @param x x-coordinates of data points.
	 * @param y y-coordinates of data points.
	 * @param dist distance to line. 
	 */
	public boolean LIC6(int numPoints, double[] x, double[] y, int n_pts, double dist) {
		// Return false if called with invalid arguments.
		if (n_pts < 3 || n_pts > numPoints || dist < 0) {
			return false;
		}
		for (int i = 0; i + n_pts <= numPoints; i++) {
			int last = i + n_pts - 1;
			double x1, x2, y1, y2, d;
			x1 = x[i]; 
			x2 = x[last];
			y1 = y[i];
			y2 = y[last];
			double denom = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
			for (int j = i + 1; j < last; j++) {
				if (x1 == x2 && y1 == y2) {
					d = Math.abs(x1 - x[j]) + Math.abs(y1 - y[j]);
				} else {
					// https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line#Line_defined_by_two_points
					d = Math.abs((x2 - x1) * (y1 - y[j]) - (x1 - x[j]) * (y2 - y1)) / denom;
				}
				// If we find a distance greater than `dist`, return true
				if (d > dist) {
					return true;
				}
			}
		}
		return false;
	}

	//There exists at least one set of two data points separated by exactly K PTS consecutive in- tervening points that are a distance greater than the length, LENGTH1, apart. The condition is not met when NUMPOINTS < 3.
	//1 ≤ K_PTS ≤ (NUMPOINTS − 2)
	public boolean LIC7(int numPoints, double[] x, double[] y, int k_pts, double length1) {
		if (numPoints < 3) return false;
		if (k_pts < 1 || k_pts > numPoints - 2) return false;
		if (length1 < 0) return false;
		for (int i = 0; i < numPoints - k_pts - 1; ++i) {
			double dx = x[i + k_pts + 1] - x[i];
			double dy = y[i + k_pts + 1] - y[i];
			if ((dx * dx) + (dy * dy) > (length1 * length1)) {
				return true;
			}
		}
		return false;
	}

	//There exists at least one set of three data points separated by exactly A PTS and B PTS consecutive intervening points, respectively, that cannot be contained within or on a circle of radius RADIUS1. The condition is not met when NUMPOINTS < 5.
	//1≤A_PTS,1≤B_PTS, A_PTS+B_PTS ≤ (NUMPOINTS−3)
	public boolean LIC8 (int numPoints , double[] x , double[] y, int a_pts, int b_pts, double Radius1){
		//The condition is not met when Numpoints < 5, A_pts or B_pts less than 1
		if(numPoints < 5 || a_pts < 1 || b_pts < 1 || a_pts + b_pts > numPoints-3){
			return false; 
		}

		int startPoint;
		int midPoint1;
		int midPoint2;
		int endPoint1;
		int endPoint2;


		for (int i = 0; i < numPoints - a_pts - b_pts - 2; i++) {
			//3 consecutive points: (start) * * * (2nd) * * * * (end), a_pts = 3, b_pts = 4
			
			//Outside the number of points
			startPoint = i;
			//First step a_pts
			midPoint1 = i + a_pts + 1;
			endPoint1 = i + a_pts + b_pts + 2;

			//First step b_pts
			midPoint2 = i + b_pts + 1;
			endPoint2 = i + b_pts + a_pts + 2;

			double [][] threePts1 = {{x[startPoint], y[startPoint]}, //Start point
								{x[midPoint1], y[midPoint1]}, 		//Mid point
								{x[endPoint1], y[endPoint1]}}; 		//End point
			double [][] threePts2 = {{x[startPoint], y[startPoint]}, //Start point
									{x[midPoint2], y[midPoint2]}, 		//Mid point
									{x[endPoint2], y[endPoint2]}}; 		//End point
			//Contained in RADIUS1
			if( (!containedInCircle(threePts1, Radius1)) || (!containedInCircle(threePts2, Radius1))){
				return true;
			}

		}
		//No points found
		return false;
	}

	//There exists at least one set of three data points separated by exactly C PTS and D PTS consecutive intervening points, respectively, that form an angle such that:
	//angle < (PI − EPSILON) or angle > (PI + EPSILON)
	//The second point of the set of three points is always the vertex of the angle. If either the first point or the last point (or both) coincide with the vertex, the angle is undefined and the LIC is not satisfied by those three points. When NUMPOINTS < 5, the condition is not met.
	//1≤C PTS,1≤D PTS, C_PTS+D_PTS ≤ NUMPOINTS−3
	public boolean LIC9 (int numPoints , double[] x , double[] y, int c_pts, int d_pts, double epsilon){

		//The condition is not met when Numpoints < 5, C_pts or D_pts less than 1, or c_pts+d_pts > numpoints-3
		if(numPoints < 5 || c_pts < 1 || d_pts < 1 || c_pts + d_pts > numPoints - 3){
			return false; 
		}

		int startPoint;
		int midPoint1;
		int endPoint1;
		int midPoint2;
		int endPoint2;
		double vx;
		double vy;
		double ux;
		double uy;


		for(int i = 0; i < numPoints - c_pts - d_pts - 2; i++){
			//3 consecutive points: (start) * * * (2nd) * * * * (end), a_pts = 3, b_pts = 4
			
			startPoint = i; 									//Current point
			midPoint1 = i + c_pts + 1;							//Current point goes c_pts forward and the next point (+1) is the next.
			endPoint1 = i + c_pts + 1 + d_pts + 1;   			//Current Points goes past the mid point, forward d_pts and one more.

			startPoint = i; 									//Current point
			midPoint2 = i + d_pts + 1;							//Current point goes d_pts forward and the next point (+1) is the next.
			endPoint2 = i + d_pts + 1 + c_pts + 1;   			//Current Points goes past the mid point, forward c_pts and one more.
			
			//If the startPoint or the endPoint coincide with the vertex(midPoint)
			//then it is not satisfied by the three points. Only check perform
			//calculations if they do not coincide.
			if( !( (x[startPoint] == x[midPoint1] && y[startPoint] == y[midPoint1]) ||
			(x[endPoint1] == x[midPoint1] && y[endPoint1] == y[midPoint1]) )){
				//Vector from middle vertex to startpoint
				vx = x[startPoint] - x[midPoint1];
				vy = y[startPoint] - y[midPoint1];

				//Vector from middle vertex to endpoint
				ux = x[endPoint1] - x[midPoint1];
				uy = y[endPoint1] - y[midPoint1];

				double numer = (vx*ux + vy*uy);
				double denom = (Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) * (Math.sqrt(Math.pow(ux, 2) + Math.pow(uy, 2))) );
				if (denom > 0){
					double angle = Math.acos(numer/denom);

					if(angle < (Math.PI - epsilon) || angle > (Math.PI + epsilon)){
						return true;
					}
				}
			}

			//If the startPoint or the endPoint coincide with the vertex(midPoint)
			//then it is not satisfied by the three points. Only check perform
			//calculations if they do not coincide.
			if( !( (x[startPoint] == x[midPoint2] && y[startPoint] == y[midPoint2]) ||
			(x[endPoint2] == x[midPoint2] && y[endPoint2] == y[midPoint2]) )){


				//Vector from middle vertex to startpoint
				vx = x[startPoint] - x[midPoint2];
				vy = y[startPoint] - y[midPoint2];

				//Vector from middle vertex to endpoint
				ux = x[endPoint2] - x[midPoint2];
				uy = y[endPoint2] - y[midPoint2];

				double numer = (vx*ux + vy*uy);
				double denom = (Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) * (Math.sqrt(Math.pow(ux, 2) + Math.pow(uy, 2))) );
				if (denom > 0){
					double angle = Math.acos(numer/denom);

					if(angle < (Math.PI - epsilon) || angle > (Math.PI + epsilon)){
						return true;
					}
				}
				
			}
		}
		//No points found
		return false;
	}

	/**
	 * There exists at least one set of three data points separated by exactly E_PTS and F_PTS consecutive intervening points, 
	 * respectively, that are the vertices of a triangle with area greater than AREA1. 
	 * 
	 * The condition is not met when NUMPOINTS < 5.
	 * 
	 * 1 ≤ E_PTS, 1 ≤ F PTS, E_PTS + F_PTS ≤ NUMPOINTS − 3
	 */
	public boolean LIC10(int numPoints, double[] x, double[] y, int e_pts, int f_pts, double area1) {
		//Return false if these conditions are met.
		if (numPoints < 5 || e_pts < 1 || f_pts < 1 || e_pts + f_pts > numPoints - 3) {
			return false;
		}
		for (int i = 0; i + e_pts + f_pts <= numPoints - 3; i++) {
			double x1, x2, x3, y1, y2, y3;
			x1 = x[i]; y1 = y[i];
			int j = i + e_pts + 1;
			int k = j + f_pts + 1;
			x2 = x[j]; y2 = y[j];
			x3 = x[k]; y3 = y[k];
			// Calculate area of the triangle
			double a = Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2;
			if (a > area1) {
				return true;
			}
		}
		return false;
	}

	//There exists at least one set of two data points, (X[i],Y[i]) and (X[j],Y[j]), separated by exactly G PTS consecutive intervening points, such that X[j] - X[i] < 0. (where i < j ) The condition is not met when NUMPOINTS < 3.
	//1 ≤ G PTS ≤ NUMPOINTS−2
	public boolean LIC11 (int NumPoints , double[] X , double[] Y ){
		return false;
	}

	//There exists at least one set of two data points, separated by exactly K PTS consecutive intervening points, which are a distance greater than the length, LENGTH1, apart. In addi- tion, there exists at least one set of two data points (which can be the same or different from the two data points just mentioned), separated by exactly K PTS consecutive intervening points, that are a distance less than the length, LENGTH2, apart. Both parts must be true for the LIC to be true. The condition is not met when NUMPOINTS < 3.
	//0 ≤ LENGTH2
	public boolean LIC12 (int NumPoints , double[] X , double[] Y, int k_pts, double length1, double length2){
		//The condition is not met when Numpoints < 3, 
		if(NumPoints < 3 || k_pts < 0 || length1 < 0 || length2 < 0){
			return false; 
		}
		int startPoint1;
		int endPoint1;
		int startPoint2;
		int endPoint2;
		double dist;
		for(int i = 0; i < NumPoints; i++){
			
			//Outside the number of points
			if((i + k_pts + 1) <= NumPoints - 1){ 
				startPoint1 = i; 									//Current point for 1
				endPoint1 = i + k_pts + 1;   						//Skips k_pts
				dist = Math.sqrt(Math.pow(X[startPoint1] - X[endPoint1], 2) + Math.pow(Y[startPoint1] - Y[endPoint1], 2));
				if(dist > length1){

					//Check again for another pair with length2
					for(int j = 0; j < NumPoints; j++){
						if((j + k_pts + 1) <= NumPoints - 1){
							startPoint2 = j; 									//Current point for 1
							endPoint2 = j + k_pts + 1;   						//Skips k_pts
							dist = Math.sqrt(Math.pow(X[startPoint2] - X[endPoint2], 2) + Math.pow(Y[startPoint2] - Y[endPoint2], 2));

							if(dist > length2){
								return true;
							}
						}
					}

				}
				

				
			}
		}
		//No points found
		return false;
	}

	/**
	 * There exists at least one set of three data points, separated by exactly A_PTS and B_PTS consecutive intervening points, respectively, 
	 * that cannot be contained within or on a circle of radius RADIUS1. 
	 * 
	 * In addition, there exists at least one set of three data points (which can be the same or different from the three data points just mentioned) 
	 * separated by exactly A_PTS and B_PTS consecutive intervening points, respectively, 
	 * that can be contained in or on a circle of radius RADIUS2. 
	 * 
	 * Both parts must be true for the LIC to be true. The condition is not met when NUMPOINTS < 5.
	 * 
	 * 0 ≤ RADIUS2
	 */
	public boolean LIC13(int numPoints, double[] x, double[] y, int a_pts, int b_pts, double radius1, double radius2) {
		// Return false if the preconditions are not met.
		if (numPoints < 5 || radius2 < 0 || radius1 < 0) {
			return false;
		}
		boolean found_true = false, found_false = false;
		// Iterate through the data points, but stop early if we meet both criteria.
		for (int i1 = 0; i1 + a_pts + b_pts <= numPoints - 3 && !(found_true && found_false); i1++) {
			// Check both ways of ordering the gaps!
			int[][] gapOrders = {{a_pts, b_pts}, {b_pts, a_pts}}; 
			for (int[] gaps : gapOrders) { 
				int i2 = i1 + gaps[0] + 1;
				int i3 = i2 + gaps[1] + 1;
				double[][] points = {{x[i1], y[i1]}, {x[i2], y[i2]}, {x[i3], y[i3]}};
				found_false = found_false || !containedInCircle(points, radius1);
				found_true = found_true || containedInCircle(points, radius2);
			}
		}
		return found_true && found_false;
	}

	/**
	 * Check wether the 3 given points can be contained in, or on, a circle of radius `radius`. 
	 * 
	 * It calculates all possible (3) circles with radius `radius` that intersects two of the given points.
	 * For each circle, it checks whether the third point is inside the circle, at which point the condition is met.
	 * 
	 * TODO: It might not be necessary to check all circles and instead only look at the one intersecting the two points farthest apart.
	 * 
	 * @param points A matrix containing 3 rows (points) and 2 columns (x & y values)
	 * @param radius The radius of the circle
	 */
	public boolean containedInCircle(double[][] points, double radius) {
		// Check all three possible circles.
		double[][][] orders = {
			{points[0], points[1], points[2]}, 
			{points[1], points[2], points[0]}, 
			{points[2], points[0], points[1]}
		};
		for (double[][] p : orders) {
			double[] p1 = p[0], p2 = p[1], p3 = p[2];
			double d = Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2)); // Calculate distance between p1 and p2
			double[] m = {(p1[0] + p2[0]) / 2, (p1[1] + p2[1]) / 2}; // Calculate the point between p1 and p2
			double h = Math.sqrt(Math.pow(radius, 2) - Math.pow(d / 2, 2)); // Determine how far out from m we want to put the circle
			double dx = h * (p1[1] - p2[1]) / d;	// Calculate dx and dy based on h
			double dy = -h * (p1[0] - p2[0]) / d;
			for (int dif = -1; dif <= 1; dif += 2) {	// Check both sides of p1 and p2
				double[] center = {m[0] + dif * dx, m[1] + dif * dy};
				if (pointDist(center, p3) <= radius) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Calculate the distance between two points represented as arrays of length 2.
	 * 
	 * @param a 
	 * @param b 
	 * @return The euclidian distance between the two points.
	 */
	private double pointDist(double[] a, double[] b) {
		return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
	}

	//There exists at least one set of three data points, separated by exactly E PTS and F PTS con- secutive intervening points, respectively, that are the vertices of a triangle with area greater than AREA1. In addition, there exist three data points (which can be the same or different from the three data points just mentioned) separated by exactly E PTS and F PTS consec- utive intervening points, respectively, that are the vertices of a triangle with area less than AREA2. Both parts must be true for the LIC to be true. The condition is not met when NUMPOINTS < 5.
	//0 ≤ AREA2
	public boolean LIC14(int numPoints, double[] x, double[] y, int e_pts, int f_pts, double area1, double area2) {
		if (numPoints < 5) return false;
		if (e_pts < 1 || f_pts < 1 || e_pts + f_pts > numPoints - 3) return false;
		if (area1 < 0 || area2 < 0) return false;
		boolean c0 = false;
		boolean c1 = false;
		for (int i = 0; i < numPoints - e_pts - f_pts - 2; ++i) {
			double x0 = x[i];
			double y0 = y[i];
			double x1 = x[i + e_pts + 1];
			double y1 = y[i + e_pts + 1];
			double x2 = x[i + f_pts + 1];
			double y2 = y[i + f_pts + 1];
			double x3 = x[i + e_pts + f_pts + 2];
			double y3 = y[i + e_pts + f_pts + 2];
			double a0 = 0.5 * Math.abs(x0 * (y1 - y3) + x1 * (y3 - y0) + x3 * (y0 - y1));
			double a1 = 0.5 * Math.abs(x0 * (y2 - y3) + x2 * (y3 - y0) + x3 * (y0 - y2));
			c0 |= (a0 > area1 | a1 > area1);
			c1 |= (a0 < area2 | a1 < area2);
			if (c0 && c1) return true;
		}
		return false;
	}


    //Double comparison function as defined in the assignment header-file
    public int DOUBLECOMPARE(double A, double B){
        if(Math.abs(A-B) < 0.000001){
            return 0; //They are equal
        }
        if(A<B) return 2; //B is greater

        return 1; //A is greater
    }

    public static void main(String[] args){
        Decide dc = new Decide(1.0, 1.0, 1.0, 1.0, 1, 
                            1, 1.0, 1, 1, 1, 1,1,1,1,
                            1,1,1.0,1.0,1.0);

        dc.DECIDE();
    }


}