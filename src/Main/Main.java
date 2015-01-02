package Main;

import java.util.ArrayList;

import Classifier.Domain;
import Classifier.Trainingset;
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
	}

}
