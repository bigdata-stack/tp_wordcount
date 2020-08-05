import java.util.TreeMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.Map.Entry;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends
		Mapper<LongWritable, Text, Text, IntWritable> 
{
	//private final static IntWritable one = new IntWritable();
	public void map(LongWritable cle, Text valeur, Context sortie)
			throws IOException 
	{
		Map<String, Integer> hasmap_counter = new TreeMap<String, Integer>();
		try 
		{			
			String line = valeur.toString();			
			StringTokenizer itr = new StringTokenizer(line, " .,?!:;");			
			while (itr.hasMoreElements()) 
			{
				/**
				 * on recupère le 1ere lettre de chaque mots 
				 * en suite on le converti en minuscle
				 * et on effectue le Map
				 */
				String mot = itr.nextToken().trim().toLowerCase().substring(0, 1);
				//compter  de lettre 
	            Integer currentCount = hasmap_counter.get(mot);
	            if (currentCount == null) {
	                currentCount = 0;
	            }
	            ++currentCount;
	            hasmap_counter.put(mot, currentCount);
	            
			}			
			for (Entry<String, Integer> entry : hasmap_counter.entrySet()) {		        
				sortie.write(new Text(entry.getKey()), new IntWritable(entry.getValue().intValue()));
		    }			
		} 
		catch (Exception ee) 
		{
			throw new RuntimeException(ee.getMessage());
		}
	}
}
