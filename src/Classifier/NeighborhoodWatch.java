package Classifier;

import java.util.ArrayList;

public class NeighborhoodWatch implements IClassifier {

	// the set of instances the classification is based on
	private ArrayList<Instance<Integer>> decisionSet;
	// the number of instances considered for classification
	private int k;
	//contains class names
	private ArrayList<String> classes;
	
	public NeighborhoodWatch(){
		decisionSet = new ArrayList<Instance<Integer>>();
		k = 1;
	}
	
	public NeighborhoodWatch(int _k){
		decisionSet = new ArrayList<Instance<Integer>>();
		k = _k;
	}
	
	public void learn (Trainingset<Integer> training){
		this.classes = training.getClasses();
		for(int m = 0; m < training.size(); m++)
			decisionSet.add(training.getInstance(m));
	}
	
	/*
	 * so far, we dont weight the distances of different features
	 */
	private int distance(Instance<Integer> i, Instance<Integer> j){
		
		int distance = 0;
		for (int m = 0; m < i.getDimension(); m++) {
			distance += Math.abs(i.getFeature(m) - j.getFeature(m));
		}
		
		return distance;
	}
	
	private Instance<Integer> nearestNeighbor(Instance<Integer> i, ArrayList<Instance<Integer>> neighbors){
		Instance<Integer> nearestNeighbor = neighbors.get(0);
		for (int m = 0; m < neighbors.size(); m++) {
			if (distance(i,neighbors.get(m)) < distance(i,nearestNeighbor))
				nearestNeighbor = neighbors.get(m);
		}
		return nearestNeighbor;
	}
	
	@Override
	public String classify(Instance<Integer> i) {
		
		//just to be sure, that our list is not too small
		if (k > decisionSet.size()) {
			System.err.println("Es kann kein " + k + "nearestNeighbors Classification auf einem " + 
								decisionSet.size() + " elementigen TrainingsSet durchgeführt werden!");
			return "";
		}
		
		ArrayList<Instance<Integer>> nearestNeighbors = new ArrayList<Instance<Integer>>();
		ArrayList<Instance<Integer>> neighbors = new ArrayList<Instance<Integer>>(decisionSet);
		
		for (int m = 0; m < k; m++){
			Instance<Integer> nearestNeighbor = nearestNeighbor(i, neighbors);
			nearestNeighbors.add(nearestNeighbor);
			neighbors.remove(nearestNeighbor);
		}
		
		int[] nearestClasses = new int[classes.size()];
		for (int m = 0; m < nearestNeighbors.size(); m++)
			nearestClasses[classes.indexOf(nearestNeighbors.get(m).getClassification())]++;
		
		int max = -1;
		String nearestClass = "";
		for (int m = 0; m < nearestClasses.length; m++){
			if (nearestClasses[m] > max){
				max = nearestClasses[m];
				nearestClass = classes.get(m);
			}
		}
		return nearestClass;
	}

}
