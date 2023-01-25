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