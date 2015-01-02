package Classifier;

/**
 * Domain of a feature
 * 
 * @author Tilman & Tim
 * @param <T>
 *
 */
public class Domain<T> {

	protected String name;
	protected T[] values;
	
	public Domain(String name, T[] values){
		this.name = name;
		this.values = values;
	}
	
	public T getValue(int index){
		return values[index];
	}
	
	public String getName(){
		return name;
	}
	
	public int size(){
		return values.length;
	}
	
	public String toString(){
		String res = name + "(";
		for(T s : values){
			res += s + ",";
		}
		return res + ")";
	}
	
	public int indexOf(T value){
		for(int i = 0; i < values.length; i++){
			if(values[i] == value)
				return i;
		}
		return -1;
	}
	
}
