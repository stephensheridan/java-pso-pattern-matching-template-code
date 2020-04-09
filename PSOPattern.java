public class PSOPattern{
	// Set the TARGET
	private static String TARGET = "Dublin";
	// Set the maximum number of generations for main loop
	private static int MAX_GENERATIONS = 100;
	// Set the number of particles in our swarm
	private static int SWARM_SIZE = 10;
	// Set the default fitness function
	private static String FITNESS_FUNC = "LEV";
	// Set the GBest influence for swapping
	private static double GBEST_INFLUENCE = 0.5;

	// Create a particle array
    private static Particle[] swarm;
	
	public static void displaySwarm(){
		for(int i = 0; i < swarm.length; i++){
			swarm[i].display();
		}
	}
	public static void sortParticles() {  
	    // Bubble sort
		for(int i = 0; i < swarm.length; i++){ 
			// Inner loop will shrink by 1 each iteration
			for(int j = 1; j < (swarm.length-i); j++){  
				// Compare swarm[j-1] with swarm[j]
				// and swap if necessary
				if(swarm[j-1].getFitness() > swarm[j].getFitness()){  
                	// Swap swarm[j-1] with swarm[j]
                    Particle temp = swarm[j-1];  
                    swarm[j-1] = swarm[j];  
                    swarm[j] = temp;  
                }  
            }  
         }  
  	} 
	private static void arguments(String[] args){
		System.out.println("Num arguments " + args.length);
		switch(args.length){
			case 0:
				// Take the default arguments
				System.out.println("Usage: java PSOPattern [\"STRING TARGET\"] {INT MAX_GENERATIONS} {INT SWARM_SIZE} {[LEV/ORD]} {GBEST_INFLUENCE}");
				System.exit(0);
			case 1:
				// TARGET String
				TARGET = args[0];
				if (TARGET.equals("")){System.out.println("You must supply a TARGET string.");System.exit(0);}
				break;
			case 2:
				TARGET = args[0];
				if (TARGET.equals("")){System.out.println("You must supply a TARGET string.");System.exit(0);}
				MAX_GENERATIONS = Integer.parseInt(args[1]);
				if (MAX_GENERATIONS < 1){System.out.println("Error: Max generations size must be > 0");System.exit(0);}
				break;
			case 3:
				TARGET = args[0];
				if (TARGET.equals("")){System.out.println("You must supply a TARGET string.");System.exit(0);}
				MAX_GENERATIONS = Integer.parseInt(args[1]);
				SWARM_SIZE = Integer.parseInt(args[2]);
				if (MAX_GENERATIONS < 1){System.out.println("Error: Max generations size must be > 0");System.exit(0);}
				if (SWARM_SIZE < 10){System.out.println("Error: Swarm size must be > 10");System.exit(0);}
				break;
			case 4:
				TARGET = args[0];
				if (TARGET.equals("")){System.out.println("You must supply a TARGET string.");System.exit(0);}
				MAX_GENERATIONS = Integer.parseInt(args[1]);
				SWARM_SIZE = Integer.parseInt(args[2]);
				FITNESS_FUNC = args[3];
				if (MAX_GENERATIONS < 1){System.out.println("Error: Max generations size must be > 0");System.exit(0);}
				if (SWARM_SIZE < 10){System.out.println("Error: Swarm size must be > 10");System.exit(0);}
				if (!(FITNESS_FUNC.equals("LEV") || FITNESS_FUNC.equals("ORD"))){System.out.println("Error: Fitness function must be LEV or ORD");System.exit(0);}
				break;
			case 5:
				TARGET = args[0];
				if (TARGET.equals("")){System.out.println("You must supply a TARGET string.");System.exit(0);}
				MAX_GENERATIONS = Integer.parseInt(args[1]);
				SWARM_SIZE = Integer.parseInt(args[2]);
				FITNESS_FUNC = args[3];
				if (MAX_GENERATIONS < 1){System.out.println("Error: Max generations size must be > 0");System.exit(0);}
				if (SWARM_SIZE < 10){System.out.println("Error: Swarm size must be > 10");System.exit(0);}
				if (!(FITNESS_FUNC.equals("LEV") || FITNESS_FUNC.equals("ORD"))){System.out.println("Error: Fitness function must be LEV or ORD");System.exit(0);}
				GBEST_INFLUENCE = Double.parseDouble(args[4]);
				if (GBEST_INFLUENCE < 0 || GBEST_INFLUENCE > 1){System.out.println("Error: GBEST_INFLUENCE must be between 0-1");System.exit(0);}
				break;			
		}
		System.out.printf("Arguments Target: %s MAX_GENERATIONS: %d SWARM_SIZE: %d FITNESS_FUNC: %s GBEST_INFLUENCE: %f", TARGET, MAX_GENERATIONS, SWARM_SIZE, FITNESS_FUNC, GBEST_INFLUENCE);
	}
	public static void main(String[] args)
	{
		int generation = 0; 			// Count these number of generations for loop
		boolean converged = false; 		// Not converged to begin with
		
		// Set the command line arguments
		// NOTE: Remove to run from development environment with default values, 
		//       this will be useful when testing.
		//       This call to arguments must be in place when submitting.
		arguments(args);
				
		// Create our Particle Swarm and /initialise each particle
		swarm = new Particle[SWARM_SIZE];
		for (int i = 0; i < swarm.length; i++){
			swarm[i] = new Particle(TARGET, FITNESS_FUNC, GBEST_INFLUENCE);
		}
		// Rank the particles based on fitness (best is first)
		sortParticles();
		// Display the TARGET string and the intial swarm and fitness values
		System.out.printf("Target length %d\n", TARGET.length());
		System.out.println("Generation " + generation);
		// Display the swarm
		displaySwarm();
		// Lets Swarm...
		// Global best will always be swarm[0]
		while (generation < MAX_GENERATIONS && !converged){
			// Calculate the fitness for each particle and check for convergence
			for(int i = 0; i < swarm.length; i++){
				swarm[i].calculateFitness();
				converged = converged || (swarm[i].getFitness() == 0);
			}
			// Rank the particles based on fitness (best is first)
			sortParticles();
			if (!converged){
				// Update each particle based on Eberhart's equation
				for(int i = 1; i < swarm.length; i++){
					// i = 1 Don't change the global best
					// swarm[0] -> Global best
					// swarm[i - 1] -> Best neighbour
					swarm[i].update(swarm[0], swarm[i-1]);
				}
				// Increment the generation
				generation++;
			}
		}
		// Display the final swarm and fitness values
		System.out.println("Generation " + generation);
		System.out.println(TARGET);
		// Display the swarm
		displaySwarm();
	}
}