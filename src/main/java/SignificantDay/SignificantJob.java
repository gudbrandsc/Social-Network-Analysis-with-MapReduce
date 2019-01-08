package SignificantDay;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import topThreeComments.TopThreeCommentsJob;
import topThreeComments.TopThreeCommentsMapper;
import topThreeComments.TopThreeCommentsReducer;
import topThreeComments.UserCommentWritable;

public class SignificantJob {
    public static void main(String[] args) {
        try {
            Configuration conf = new Configuration();

            /* Job Name. You'll see this in the YARN webapp */
            Job job = Job.getInstance(conf, "Finding user comments counts");

            /* Current class */
            job.setJarByClass(SignificantJob.class);

            /* Mapper class */
            job.setMapperClass(SignificantDayMapper.class);
            job.setNumReduceTasks(1);

            /* Combiner class. Combiners are run between the Map and Reduce
             * phases to reduce the amount of output that must be transmitted.
             * In some situations, we can actually use the Reducer as a Combiner
             * but ONLY if its inputs and ouputs match up correctly. The
             * combiner is disabled here, but the following can be uncommented
             * for this particular job:
            //job.setCombinerClass(WordCountReducer.class);

            /* Reducer class */
            job.setReducerClass(SignificantDayReducer.class);

            /* Outputs from the Mapper. */
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            /* Outputs from the Reducer */
            job.setOutputKeyClass(Text.class);

            job.setOutputValueClass(Text.class);

            /* Job input path in HDFS */
            FileInputFormat.addInputPath(job, new Path(args[0]));

            /* Job output path in HDFS. NOTE: if the output path already exists
             * and you try to create it, the job will fail. You may want to
             * automate the creation of new output directories here */
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            /* Wait (block) for the job to complete... */
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}