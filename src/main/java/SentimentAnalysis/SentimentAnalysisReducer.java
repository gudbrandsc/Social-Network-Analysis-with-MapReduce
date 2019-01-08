package SentimentAnalysis;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SentimentAnalysisReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Context context)  throws IOException, InterruptedException {
        int count = 0;
        double score = 0;


        for(DoubleWritable val : values) {
            count++;
            score += val.get();
        }

        double result = score/count;

        context.write(key, new DoubleWritable(result));

    }
}
