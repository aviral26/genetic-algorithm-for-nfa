package geneticalgorithm;
import java.io.*;
import java.math.*;

public class GeneticAlgorithm {
    final static int pop_size = 100;
    public final static int chrom_length = 300;
    public final static int gene_length = 4;
    public final static int max_allowable_runs = 800;
    public final static float crossover_rate = 0.7f;
    public final static float mutation_rate = 0.01f;
    static int GenerationsRequiredToFindSolution = 0;
    static boolean Found = false;
    
    public static void main(String[] args) throws IOException {
        int target;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a target number");
        target = Integer.parseInt(br.readLine());
        Chromosome[] population = new Chromosome[pop_size];
        for(int i=0;i<pop_size;i++){
            population[i] = new Chromosome(getRandomBits(),0.0f);
        }
        
        while(!Found){
            float total_fitness = 0.0f;
            for(int i=0;i<pop_size;i++){
                population[i].AssignFitness(target);
                total_fitness += population[i].getFitness();
            }
            
            for(int i=0;i<pop_size;i++){
                if(population[i].getFitness() == 999.9f){
                    System.out.println("Solution found in " + GenerationsRequiredToFindSolution + " generations");
                    population[i].printChromosome();
                    Found = true;
                    break;
                }
            }
            
            Chromosome[] temp = new Chromosome[pop_size];
            int cpop=0;
            while(cpop < pop_size){
                
                String offspring1 = Roulette(total_fitness,population);
                String offspring2 = Roulette(total_fitness,population);
                //CROSSOVER
                String c_offspring1;
                String c_offspring2;
                float rand = (float)Math.random();
                if(rand < crossover_rate){
                    int crossover = (int)(rand*(float)chrom_length);
                    c_offspring1 = offspring1.substring(0,crossover).concat(offspring2.substring(crossover,chrom_length));
                    c_offspring2 = offspring2.substring(0,crossover).concat(offspring1.substring(crossover,chrom_length));
                
                    Mutate(c_offspring1);
                    Mutate(c_offspring2);
                    temp[cpop++] = new Chromosome(c_offspring1,0.0f);
                    temp[cpop++] = new Chromosome(c_offspring2,0.0f);
                }
                else{
                    Mutate(offspring1);
                    Mutate(offspring2);
                    temp[cpop++] = new Chromosome(offspring1,0.0f);
                    temp[cpop++] = new Chromosome(offspring2,0.0f);
                }                                
            }
            
            for(int i=0;i<pop_size;i++){
                population[i] = temp[i];
            }
            ++GenerationsRequiredToFindSolution;
            if(GenerationsRequiredToFindSolution > max_allowable_runs){
                System.out.println("No solution found in this run");
                Found = true;
            }
        }
        
    }
    
    public static String getRandomBits(){
        String bits = " ";
        
        for(int i=0;i<chrom_length;i++){
            if(Math.random() > 0.5f){
                bits += "1";
            }
            else{
                bits += 0;
            }
        }
        return bits;
    }
    
    public static void Mutate(String offspring){
        for (int i=0; i<offspring.length(); i++){
		if (Math.random() < mutation_rate){
			if (offspring.charAt(i) == '1'){
				offspring.replace('1','0');
                        }
                        else{
				offspring.replace('0','1');
                        }
		}
	}

    }
    public static String Roulette(float total_fitness, Chromosome[] population){
        float Slice = (float)(Math.random() * total_fitness);
        float fitness_so_far = 0.0f;
        for(int i=0;i<pop_size;i++){
            fitness_so_far += population[i].getFitness();
            if(fitness_so_far > Slice){
                return population[i].getBits();
            }
        }
        return " ";
    }
}
