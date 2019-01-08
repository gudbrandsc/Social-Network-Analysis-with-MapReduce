package TermFrequency;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TermFrequencyReducer extends Reducer<Text, IntWritable, Text, ResultDisplayer> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)  throws IOException, InterruptedException {
        int count = 0;
        // calculate the total count
        for(IntWritable val : values){
            count += val.get();
        }

        ResultDisplayer resultDisplayer = new ResultDisplayer(key.toString(), count);



        context.write(key, resultDisplayer);
    }
}
