import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;


public class WordCount 
{

  public static void main(String[] args) throws Exception 
  {
    Job job = new Job();
    
    job.setJobName("Word Count");
    
    job.setJarByClass(WordCount.class);
    
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    job.setMapperClass(WordCountMapper.class);        
    job.setReducerClass(WordCountReducer.class);

    FileInputFormat.setInputPaths(job, args[0]);
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    FileSystem fs = FileSystem.get(job.getConfiguration());
    fs.delete(new Path(args[1]));
				
    job.waitForCompletion(true);
  }
}
