package Screamers;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ScreamersReducer  extends Reducer<Text, SubRedditWritable, Text, SubRedditWritable> {

    @Override
    protected void reduce(Text key,  Iterable<SubRedditWritable> values, Context context)  throws IOException, InterruptedException {
        // calculate the total count
        double score = 0.0;
        int num = 0;

        for(SubRedditWritable val : values) {
            score += val.getScore().get();
            num += val.getNumberOfComments().get();
        }

        double totScore = score/num;

        SubRedditWritable writable = new SubRedditWritable(totScore, num);
        context.write(key, writable);
    }

}


