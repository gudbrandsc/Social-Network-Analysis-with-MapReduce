package ActivityHour;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ActivityHourReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text key,  Iterable<IntWritable> values, Context context)  throws IOException, InterruptedException {
        // calculate the total count
        int count = 0;

        for(IntWritable val : values) {
            count += val.get();
        }
        context.write(key, new IntWritable(count));



    }
}
