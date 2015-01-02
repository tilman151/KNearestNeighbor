package Main;

import java.util.ArrayList;

import Classifier.Domain;
import Classifier.Instance;
import Classifier.NeighborhoodWatch;
import Classifier.Trainingset;
import Classifier.Validator;
import DataTransfer.InstanceConverter;
import DataTransfer.InstanceReader;
import DataTransfer.MetaDataReader;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InstanceReader input = new InstanceReader("./car.data");
		Trainingset<String> t = input.readInstances();
		
		MetaDataReader meta = new MetaDataReader("./car.c45-names");
		ArrayList<Domain<String>> d = meta.readDomains();
		ArrayList<String> c = meta.readClasses();
		
		t.setClasses(c);
		t.setDomains(d);
		
		InstanceConverter converter = new InstanceConverter();
		Trainingset<Integer> newT = converter.convertStringToInteger(t);
		
		for(int i = 0; i < t.getDomains().size(); i++){
			System.out.println(t.getDomain(i));
		}
		
		for(int i = 0; i < newT.getDomains().size(); i++){
			System.out.println(newT.getDomain(i));
		}
		
		System.out.println(t.getInstance(500));
		System.out.println(newT.getInstance(500));
		
		NeighborhoodWatch classifier = new NeighborhoodWatch();
		
		Trainingset<Integer> testSet = newT.splitUpTestSet(33);
		
		classifier.learn(newT);
		
		ArrayList<Integer> features = new ArrayList<>();
		features.add(0); //vhigh
		features.add(0); //vhigh
		features.add(0); //2
		features.add(1); //4
		features.add(0); //small
		features.add(1); //med
		Instance<Integer> i = new Instance<Integer>(features,"unacc");
		
		System.out.println(classifier.classify(i));
		
		Validator v = new Validator(classifier);
		System.out.println(v.validateOnTestSet(testSet));
	}

}
