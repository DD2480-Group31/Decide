package decide;

public class Decide {
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

	// There exists at least one set of three consecutive data points that cannot all be contained within or on a circle of radius RADIUS1.
	// 0 ≤ RADIUS1
	// => exist point(i-2),point(i-1),point(i) not in one circle(r=Radius1) === true
	public boolean LIC1 (int NumPoints , double[] X , double[] Y , double Radius1){
		//Distance(Point1, Point2)>2R => Next
		//Distance(Point1, Point2)<2R 
		//angle(Point1, Point3, Point2) < 2 * angle1=> False
		////sin(2 * angle1) = Distance(Point1, Point2) / 2 * R
		/*
		 * |
		 * |            P3
		 * |          /   \
		 * |         /     \
 		 * |        /       \
		 * |       /         \
		 * |______P1__________P2________
		 */
		//cos<BAC = (AB * BC) /(|AB|*|BC|)
		double x1 , y1 , x2 , y2 , x3 , y3 , Distance , Distance2 , Distance3 , Angle , AngleMax;
		for(int i = 2 ; i < NumPoints ; i++) {
			x1 = X[i-2];
			y1 = Y[i-2];
			x2 = X[i-1];
			y2 = Y[i-1];
			x3 = X[i];
			y3 = Y[i];
			Distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
			if (Distance < 2 * Radius1){
				AngleMax = 2 * Math.asin(Distance / (2 * Radius1));
				Distance2 = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
				Distance3 = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
				Angle = Math.acos( ( (x1-x3)*(x2-x3) + (y1-y3)*(y2-y3) ) / (Distance2 * Distance3));
				if (Angle <= AngleMax){
					return true;
				}
				return false;
			}
			return false;
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
		return false;
	}

	//There exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[j],Y[j]), such that X[j] - X[i] < 0. (where i = j-1)
	public boolean LIC5 (int NumPoints , double[] X , double[] Y ){
		return false;
	}

	//There exists at least one set of N PTS consecutive data points such that at least one of the points lies a distance greater than DIST from the line joining the first and last of these N PTS points. If the first and last points of these N PTS are identical, then the calculated distance to compare with DIST will be the distance from the coincident point to all other points of the N PTS consecutive points. The condition is not met when NUMPOINTS < 3.
	//3 ≤ N PTS ≤ NUMPOINTS , 0 ≤ DIST
	public boolean LIC6 (int NumPoints , double[] X , double[] Y ){
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

	//There exists at least one set of three data points separated by exactly E PTS and F PTS con- secutive intervening points, respectively, that are the vertices of a triangle with area greater than AREA1. The condition is not met when NUMPOINTS < 5.
	//1≤E PTS,1≤F PTS, E PTS+F PTS ≤ NUMPOINTS−3
	public boolean LIC10 (int NumPoints , double[] X , double[] Y ){
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

	//There exists at least one set of three data points, separated by exactly A PTS and B PTS consecutive intervening points, respectively, that cannot be contained within or on a circle of radius RADIUS1. In addition, there exists at least one set of three data points (which can be the same or different from the three data points just mentioned) separated by exactly A PTS and B PTS consecutive intervening points, respectively, that can be contained in or on a circle of radius RADIUS2. Both parts must be true for the LIC to be true. The condition is not met when NUMPOINTS < 5.
	//0 ≤ RADIUS2
	public boolean LIC13 (int NumPoints , double[] X , double[] Y ){
		return false;
	}

	//There exists at least one set of three data points, separated by exactly E PTS and F PTS con- secutive intervening points, respectively, that are the vertices of a triangle with area greater than AREA1. In addition, there exist three data points (which can be the same or different from the three data points just mentioned) separated by exactly E PTS and F PTS consec- utive intervening points, respectively, that are the vertices of a triangle with area less than AREA2. Both parts must be true for the LIC to be true. The condition is not met when NUMPOINTS < 5.
	//0 ≤ AREA2
	public boolean LIC14( int NumPoints , double[] X , double[] Y ){
		return false;
	}
	

}
