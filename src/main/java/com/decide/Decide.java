package com.decide;
import java.lang.Math;

class Decide{

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


    // CONSTANT
    final static double PI = 3.1415926535;

    // TYPE DECLARATIONS
    enum CONNECTORS{
        NOTUSED,
        ORR,
        ANDD, 
    }

    // Coordinates matrix [x, y] for the 100x100 plane
    double[][] COORDINATES = new double[100][100];


    //Logical Connector Matrix
    CONNECTORS[][] CMATRIX = new CONNECTORS[15][15];

    //Preliminary Unlocking matrix
    boolean[][] PUM = new boolean[15][15];

    //Final Unlocking Vector
    boolean[] FUV = new boolean[15];



    // Number of data points 
    int NUMPOINTS;
    static int NUMPOINTS2;


    public void DECIDE(){
        System.out.println("Init");
        //This is the function we are going to implement
        //Calling every LICx from here?
    }


    // HERE starts the LIC implementations -------------

    // 15 Launch Interceptor Conditions
	// Input: 100 planar data points

	// LIC 0 : There exists at least one set of two consecutive data points that are a distance greater than the length, LENGTH1, apart
	// 0 ≤ LENGTH1
	// => distance(point(i-1),point(i)) > Length1 === true
	public boolean LIC0 (int NumPoints , double[] X , double[] Y , double Length1 ){
		double x1 , y1 , x2 , y2 , Distance ;
		for(int i = 1 ; i < NumPoints ;  i++) {
			x1 = X[i-1];
			y1 = Y[i-1];
			x2 = X[i];
			y2 = Y[i];
			Distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
			if (Distance > Length1){
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
	public boolean LIC4 (int NumPoints , double[] X , double[] Y ){
		//UNDO
		return false;
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
	public boolean LIC6(int numPoints, int n_pts, double[] x, double[] y, double dist) {
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
	public boolean LIC7 (int NumPoints , double[] X , double[] Y ){
		return false;
	}

	//There exists at least one set of three data points separated by exactly A PTS and B PTS consecutive intervening points, respectively, that cannot be contained within or on a circle of radius RADIUS1. The condition is not met when NUMPOINTS < 5.
	//1≤A_PTS,1≤B_PTS, A_PTS+B_PTS ≤ (NUMPOINTS−3)
	public boolean LIC8 (int NumPoints , double[] X , double[] Y ){
		return false;
	}

	//There exists at least one set of three data points separated by exactly C PTS and D PTS consecutive intervening points, respectively, that form an angle such that:
	//angle < (PI − EPSILON) or angle > (PI + EPSILON)
	//The second point of the set of three points is always the vertex of the angle. If either the first point or the last point (or both) coincide with the vertex, the angle is undefined and the LIC is not satisfied by those three points. When NUMPOINTS < 5, the condition is not met.
	//1≤C PTS,1≤D PTS, C_PTS+D_PTS ≤ NUMPOINTS−3
	public boolean LIC9 (int NumPoints , double[] X , double[] Y ){
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
	public boolean LIC12 (int NumPoints , double[] X , double[] Y ){
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
			int i2 = i1 + a_pts + 1;
			int i3 = i2 + b_pts + 1;
			double[][] points = {{x[i1], y[i1]}, {x[i2], y[i2]}, {x[i3], y[i3]}};
			found_false = found_false || !containedInCircle(points, radius1);
			found_true = found_true || containedInCircle(points, radius2);
		}
		return found_true && found_false;
	}

	/**
	 * Check wether the 3 given points can be contained in, or on, a circle of radius `radius`. 
	 * 
	 * @param points A matrix containing 3 rows (points) and 2 columns (x & y values)
	 * @param radius The radius of the circle
	 */
	public boolean containedInCircle(double[][] points, double radius) {
		double[] p1, p2, p3;
		p1 = points[0];
		p2 = points[1];
		p3 = points[2];
		double d12 = pointDist(p1, p2);
		if (d12 > radius * 2) {
			return false;
		}
		// Calculate the point between p1 and p2
		double[] m12 = {p1[0] - (p1[0] - p2[0]) / 2, p1[1] - (p1[1] - p2[1]) / 2};

		// Calculate the distance from the center of the circles to the intersections.
		double h = Math.sqrt(Math.pow(radius, 2) - Math.pow(d12 / 2, 2));

		// Calculate deltas
		double dx = h * (p1[1] - p2[1]) / d12;
		double dy = -h * (p1[0] - p2[0]) / d12;

		for (int dif = -1; dif <= 1; dif++) {
			double[] intersection = {m12[0] + dif * dx, m12[1] + dif * dy};
			if (pointDist(intersection, p3) <= radius) {
				return true;
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