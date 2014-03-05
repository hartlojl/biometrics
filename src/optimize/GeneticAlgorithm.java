package optimize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Random;

import settings.AllSettings;
import settings.coordinatorsettings.AllTestGeneratorSettings;
import settings.coordinatorsettings.TestGeneratorFVCTestsSettings;
import settings.fingerprintmethodsettings.AllFingerprintMethodSettings;
import settings.fingerprintmethodsettings.NgonSettings;
import settings.modalitysettings.AllModalitySettings;
import settings.modalitysettings.FingerprintSettings;
import settings.settingsvariables.SettingsDropDownItem;
import system.allcommonclasses.commonstructures.Results;

public class GeneticAlgorithm {
	//FIXME Matt
	private FitnessFunction f;
	
	public GeneticAlgorithm(FitnessFunction fitness){
		AllFingerprintMethodSettings.getInstance().manuallySetComboBox(NgonSettings.getInstance());;
		AllModalitySettings.getInstance().manuallySetComboBox(FingerprintSettings.getInstance());
		AllTestGeneratorSettings.getInstance().manuallySetComboBox(new TestGeneratorFVCTestsSettings());
		FingerprintSettings.getInstance().testingDataset( ).manuallySetComboBox(new SettingsDropDownItem("FVC20022Small.ser"));
		FingerprintSettings.getInstance().trainingDataset().manuallySetComboBox(new SettingsDropDownItem("FVC20022Small.ser"));
		this.f = fitness;
		
	}

	public static void main(String[] args){
		GeneticAlgorithm ga = new GeneticAlgorithm(new FitnessFunction(){
			@Override
			public Double evaluateFitness(ArrayList<Chromosome> chromosomes) {
				AllSettings settings = AllSettings.getInstance();
				for(Chromosome c : chromosomes){
					c.execute();
				}
				Results results =  settings.runSystemAndGetResults();
				System.out.println(results.getEer());

				return results.getEer()*100.0; 			//minimizing EER
			}	
		});		

		ArrayList<Candidate> candidates = ga.intializePopulation(5);
		ArrayList<Candidate> init = new ArrayList<Candidate>(candidates);
		System.out.println("Initial Population:");
		for(Candidate c : init){
			System.out.println("Candidate: "+c);
		}
		for(int i = 0; i < 2; i++){
			candidates = ga.evolve(candidates);
			for(Candidate c : candidates){
				System.out.println(i+"th Gen Candidate: "+c);
			}
		}
		System.out.println("\n\n\n\n\n\n\n\n\n");
		System.out.println("Initial Population:");
		for(Candidate c : init){
			System.out.println("Candidate: "+c);
		}
		
		System.out.println("Current Generation:");

		for(Candidate c : candidates){
			System.out.println("Candidate: "+c);
		}
		System.exit(0);
	}
	
	

	private ArrayList<Candidate> evolve(ArrayList<Candidate> candidates) {
		//Evaluation -- expensive fitness function evaluations
		for(Candidate candidate : candidates)
			candidate.evaluate(f);
		
		//Selection
		//normalizing
		Double norm = 0.0;
		for(Candidate candidate : candidates){
			norm += candidate.getFitness();
		}
		for(int i = 0; i < candidates.size(); i++){
			candidates.get(i).setNormalizedFitness(candidates.get(i).getFitness()/norm);
		}
		
		Collections.sort(candidates, Collections.reverseOrder());
		
		for(int i = 0; i < candidates.size(); i++){
			Double anf = 0.0;
			for(int j = 0; j <= i; j++){
				anf += candidates.get(j).getNormalizedFitness();
			}
			candidates.get(i).setAccumlatedNormalizedFitness(anf);
		}
		
		//constructing breeding population based on accumulated frequency
		int offspringProportion = 2; //make this more clear
		//int numberToSelect = candidates.size()/offspringProportion;
		int numberToSelect = candidates.size()*3/4;
		
		ArrayList<Candidate> breedingPopulation = new ArrayList<Candidate>();
		Random r = new Random();
		while(breedingPopulation.size() < numberToSelect){
			Double R = r.nextDouble();
			for(Candidate c : candidates){
				if(c.getAccumlatedNormalizedFitness() > R){
					breedingPopulation.add(c);
					break;
				}
			}
		}
		System.out.println("\n\n\n");

		
		//Crossover/Mutation
		ArrayList<Candidate> nextGenPopulation = new ArrayList<Candidate>();
		nextGenPopulation.addAll(breedingPopulation);
		Random R = new Random();
 		while(nextGenPopulation.size() != candidates.size()){
			Candidate parentA = breedingPopulation.get(R.nextInt(breedingPopulation.size()));
			Candidate parentB = breedingPopulation.get(R.nextInt(breedingPopulation.size()));
			Candidate child = makeOffspring(parentA, parentB);
			nextGenPopulation.add(child);
		}
	
		return nextGenPopulation;		
	}
	
	private Candidate makeOffspring(Candidate A, Candidate B) {
		Random R = new Random();
		Double crossoverConstant = .5, scalingConstant = .87; //adjust these for varying crossover and magnitude of mutations
		//one point crossover
		//switch to two point crossover if there are enough variables
		int initialParent = R.nextInt(2);
		boolean crossed = false;
		ArrayList<Chromosome> childChromosomes = new ArrayList<Chromosome>();
		for(int i = 0; i < A.getChromosomes().size(); i++){
			if(initialParent == 0){
				childChromosomes.add(A.getChromosomes().get(i));
			} else{
				childChromosomes.add(B.getChromosomes().get(i));
			}
			if(R.nextDouble() > crossoverConstant && !crossed){
				initialParent = (initialParent+1) % 2;
				crossed = true;
			}
		}
		
		return new Candidate(childChromosomes);
	}

	private ArrayList<Candidate> intializePopulation(int populationSize){
		ArrayList<Candidate> candidateSolutions = new ArrayList<Candidate>();
		Random r = new Random();
		
		for(int i = 0; i < populationSize; i++){
			ArrayList<Chromosome> chromosomes = getChromosomeList();
			for(int c = 0; c < chromosomes.size(); c++){
				Chromosome chromosome = chromosomes.get(c);
				ArrayList<Long> bounds = chromosome.getBounds();
				Long shiftInterval = bounds.get(1) - bounds.get(0);
				chromosome.setValue(r.nextInt(shiftInterval.intValue())+bounds.get(0));
			}
			candidateSolutions.add(new Candidate(chromosomes));
		}
		return candidateSolutions;
	}
	
	
	public static ArrayList<Chromosome> getChromosomeList(){
		ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
		NgonSettings fpm = NgonSettings.getInstance();
		try {
			Long[] nbounds = {3L,11L};
			Long[] kClosestBounds = {3L,11L};
			Long[] xBinBounds = {2L, 11L};
			Long[] yBinBounds = {2L, 11L};
			Long[] thetaBinBounds = {2L, 11L};
			
			chromosomes.add(new Chromosome(fpm.n(), 0L,"N",
							fpm.n().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(nbounds))));
			chromosomes.add(new Chromosome(fpm.kClosestMinutia(), 0L,"k-closest Minutia", 
							fpm.kClosestMinutia().getClass().getMethod("setValue", Long.class), 
							new ArrayList<Long>(Arrays.asList(kClosestBounds))));
			chromosomes.add(new Chromosome(fpm.xBins(), 0L, "xBins",
							fpm.xBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(xBinBounds))));
			chromosomes.add(new Chromosome(fpm.yBins(), 0L, "yBins",
							fpm.yBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(yBinBounds))));
			chromosomes.add(new Chromosome(fpm.thetaBins(), 0L, "thetaBins",
							fpm.thetaBins().getClass().getMethod("setValue", Long.class),
							new ArrayList<Long>(Arrays.asList(thetaBinBounds))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chromosomes;
	}
		

}
