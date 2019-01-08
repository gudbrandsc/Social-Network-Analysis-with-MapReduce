package HappyScore;

import BackgroundStory.UserStoryWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class HappyScoreReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    @Override
    protected void reduce(Text key,  Iterable<DoubleWritable> values, Context context)  throws IOException, InterruptedException {

        int num = 0;
        double sentimentScore = 0.0;

        for(DoubleWritable val : values) {
            sentimentScore += val.get();
            num ++;
        }



        context.write(key, new DoubleWritable(sentimentScore /(double) num));





    }
}
