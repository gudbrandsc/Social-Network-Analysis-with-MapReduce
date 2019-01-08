package SignificantDay;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import topThreeComments.UserCommentWritable;

import java.io.IOException;

public class SignificantDayReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key,  Iterable<Text> values, Context context)  throws IOException, InterruptedException {
        // calculate the total count
        for(Text val : values) {
            context.write(key, val);
        }



    }
}
