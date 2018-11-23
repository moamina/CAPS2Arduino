package cap.sim.generator;

import java.util.ArrayList;
import java.util.List;
import cap.sim.utility.NumberUtility;

public class NaturalEventGenerator {
	
	private double mean;
	private double standardDeviation;
	private int sampleSize;

	public NaturalEventGenerator(double mean,double standardDeviation,int size) {
		// TODO Auto-generated constructor stub
		this.mean=mean;
		this.sampleSize=size;
		this.standardDeviation=standardDeviation;
	}
	
	private List<Double>   generateNumber()
	{
		List<Double> table=new ArrayList<Double>();		
		double value=0;
		for(int i=0;i<this.sampleSize;i++)
		{			
			value=NumberUtility.getRandom(this.mean, this.standardDeviation);			
			table.add( value);
			
		}
		return table;
	}
	
	public String generateFormatedData()
	{
		List<Double> numbers=generateNumber();
		String data="time value\n";
		int time=0;
		for (int i = 0; i < numbers.size(); i++) {
			if(i>0)
				time=1;
			data+=time+" "+numbers.get(i)+"\n";
		}
		return data;
	}
	
	public static void main(String[] args) {
		String data=new NaturalEventGenerator(22, 4, 20).generateFormatedData();
		System.out.println(data.split("\n").length);
		System.out.println(data);
	}

}
