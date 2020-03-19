import java.util.Arrays;
public class Particle{
	// This is the allowable character set for a Particle
	private static char[] charSet = {'a','b','c','d','e','f','g','h','i','j','k','l',
		                             'm','n','o','p','q','r','s','t','u','v','w','x','y','z',
									 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
									 'P','Q','R','S','T','U','V','W','X','Y','Z',' '};
	// This is the particles data
	private String data;
	// This is the Target string
	private String TARGET;
	// Fitness function to use Levenshtein or Ordinal distance
	private String FITNESS_FUNC;
	// GBest influence - % of gbest character swaps
	private double GBEST_INFLUENCE;
	private int pBest;
	private int fitness;
	private float velocity;

	// Particle constructor
	Particle(String TARGET, String FITNESS_FUNC, double GBEST_INFLUENCE){
		this.TARGET = TARGET;
		this.FITNESS_FUNC = FITNESS_FUNC;
		this.GBEST_INFLUENCE = GBEST_INFLUENCE;
		initialise();
	}
	// Initialise the particle
	public void initialise(){
		data = getRandomString(TARGET.length());
		velocity = TARGET.length();
		pBest = fitness(TARGET,data);
	}
	// Used by Levenshtein
	public static int costOfSubstitution(char a, char b) {
		return a == b ? 0 : 1;
	}
	// Used by Levenshtein
	public static int min(int... numbers) {
	        return Arrays.stream(numbers)
	          .min().orElse(Integer.MAX_VALUE);
	}
	// Calculate the Levenshtein distance between two strings
	public static int levenshtein(String x, String y) {
	    int[][] dp = new int[x.length() + 1][y.length() + 1];
	    for (int i = 0; i <= x.length(); i++) {
	        for (int j = 0; j <= y.length(); j++) {
	            if (i == 0)
	                dp[i][j] = j;
	            else if (j == 0)
	                dp[i][j] = i;
	            else {
	                dp[i][j] = min(dp[i - 1][j - 1] 
	                 + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)), 
	                  dp[i - 1][j] + 1, 
	                  dp[i][j - 1] + 1);
	            }
	        }
	    }
		return dp[x.length()][y.length()];
	}
	// Calculates the ordinal distance between two strings
	public int ordDistance(String sample, String target){
		// T O D O
		// This method should calculate the ordinal distance between two strings
		// The ordinal distance is the ordinal difference (absolute) between each
		// charater in the sample string and it's associated target character.
		// For example, take the following strings
		// Bob -> Dub
		// The ordinal distance would be = abs('B' - 'D') + abs('o'-'u') + abs('b'-'b')
		// return the ordinal distance between sample and target
	}
	public int getFitness(){
		return this.fitness;
	}
	// Selects that fitness function to use
	private int fitness(String sample, String target){
		// Select which fitness function to use
		if (FITNESS_FUNC.equals("LEV"))
			return fitness = levenshtein(data, TARGET);
		else if (FITNESS_FUNC.equals("ORD"))
			return fitness = ordDistance(data, TARGET);
		else
			return fitness = levenshtein(data, TARGET);
	}
	// Called from the main loop to set the new fitness for each particle
	public void calculateFitness()
	{
		fitness = fitness(data, TARGET);
		// Check if current fitness is better then pBest and store
		if (fitness < pBest){
			pBest = fitness;
		}
	}
	///////////////////////////////////////////////////////////////
	// gBest - Global best particle passed from main loop
	// neighbourBest - Best neighbour passed from main loop
	public void update(Particle gBest, Particle neighbourBest){
		// Get the current answer for this particle
		int current = fitness(data, TARGET);
		// Get the global best
		int bestResult = gBest.pBest;
		
		// Calculate a change in velocity based on Eberhart's equation
		velocity = Math.abs((float)(velocity + 2 * Math.random() * (pBest - current) + 2 * Math.random() * (bestResult - current)));
		
		// Strings in Java are non mutable
		// So use the StringBuilder to make the changes
		StringBuilder pStr = new StringBuilder(data); 				    // Current particle str data
		StringBuilder gBestStr = new StringBuilder(gBest.data);		    // Global best str data
		StringBuilder nBestStr = new StringBuilder(neighbourBest.data);	// Local best str data (best neighbour)
				
		// T O D O - C H A N G E  pStr
		// U P D A T E   P A R T I C L E   D A T A   H E R E
		// We need to modify this particles string data based on the velocity
		// But how??
		// One way to change the string would be to swap some of its characters 
		// with the gBest and some with it's best neighbour 
		// Use the following variables for this part!!
		// velocity - control the amount of swaps - loop
		// GBEST_INFLUENCE - This variable controls the balance of swaps between gBest and neighbourBest.
		//                   If 0.5 (50%) then equal probability that roughly same amount of
		//                   swaps between gBest and neighbour best.
		//                   If 0.1 (10%) then more probabilty that swaps will mostly be from
		//                   gBest. GBEST_INFLUENCE is defaulted to 0.5.
		
		


		// T O D O - C H A N G E  pStr
		// A P P L Y   S O M E   R A N D O M N E S S   T O   T H E   P A R T I C L E   D A T A
		// Make a random flip of 1 character in the string
		// this is necessary because random string might not
		// contain all the necesssary characters to build
		// the target string. The flip should be
		// made by randomly generating a position and
		// randomly selecting a character from the 
		// charSet 'a'-'z', 'A'-'Z' and space ' '.

		// Set the final particle data after updating pStr above
		data = pStr.toString();
	}
	// Display this particles variables and fitness
	public void display(){
		System.out.printf("%s\tFitness %d\n",data, fitness);
	}
	// Used to initialise the Particle string
	private static String getRandomString(int l){
		// T O D O
		// Return a random String of length l
		// built from the allowable character set
	}
}