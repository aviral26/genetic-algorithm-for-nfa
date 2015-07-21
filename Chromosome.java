package geneticalgorithm;

public class Chromosome {
    String bits;
    float fitness;
    public final static int chrom_length = 300;
    public final static int gene_length = 4;
    Chromosome(){
        bits = " ";
        fitness = 0.0f;
    }
    Chromosome(String b, float f){
        bits = b;
        fitness = f;
    }
    public float getFitness(){
        return fitness;
    }
    public String getBits(){
        return bits;
    }
    public void printChromosome(){
        int temp = chrom_length/gene_length;
        int[] buffer = new int[temp];
        int num_elements = ParseBits(buffer);
        for (int i=0; i<num_elements; i++){
		PrintGeneSymbol(buffer[i]);
        }
    }
    public void PrintGeneSymbol(int val){
        if (val < 10 ){
		System.out.print(val + " ");
        }
        else{
            switch(val){
		case 10:System.out.print("+ ");
			break;

		case 11:System.out.print("- ");
			break;

		case 12:System.out.print("* ");
			break;

		case 13:System.out.print("/ ");
			break;

		}
	}

    }
    public void AssignFitness(int target){
        int temp = chrom_length/gene_length;
        int[] buffer = new int[temp];
        float result = 0.0f;
        int num_elements = ParseBits(buffer);
        
        for(int i=0;i<num_elements-1;i+=2){
            
            switch(buffer[i]){
                case 10:result += buffer[i+1];
			break;

		case 11:result -= buffer[i+1];
			break;
		
                case 12:result *= buffer[i+1];
			break;

		case 13:result /= buffer[i+1];
			break;
		}
            
         }
        if(result == (float)target){
            this.fitness = 999.9f;
        }
        else{
            this.fitness = 1/(float)Math.abs((target - result));
        }
     }
       
     public int ParseBits(int[] buffer){
        
         int cbuff = 0;
         boolean operator = true;
         int this_gene;
         //int flag = 0;
         for(int i=0;i+gene_length<bits.length();i+=gene_length){
                //System.out.println(gene_length);
                //System.out.println(bits.substring(i, i+gene_length));
                //if(i+gene_length>=chrom_length){
                  //  flag = 1;
                //}
                
                this_gene = BinToDec(bits.substring(i,i+gene_length));
                if(operator){
                    if(this_gene<10 || this_gene>13){
                        continue;
                    }
                    else{
                        operator = false;
			buffer[cbuff++] = this_gene;
			continue;
                    }
                }
                else{
                    if(this_gene>9){
                        continue;
                    }
                    else{
                        operator = true;
			buffer[cbuff++] = this_gene;
			continue;
                    }
                }                
         }
         for (int i=0; i<cbuff; i++){ //checking for divide by zero
		if ((buffer[i] == 13) && (buffer[i+1] == 0)){
			buffer[i] = 10;
                }
        }
        return cbuff; 
    }
     
    public int BinToDec(String gene){
        int val	= 0;
	int value_to_add = 1;

	for (int i = gene_length-1; i >= 0; i--){
            if (gene.charAt(i) == '1'){
			val += value_to_add;
            }
            value_to_add *= 2;
	}
	return val;
    }

}
