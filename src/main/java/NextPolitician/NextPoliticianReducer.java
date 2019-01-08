package NextPolitician;

import TermFrequency.ResultDisplayer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class NextPoliticianReducer extends Reducer<Text, PoliticianUserWritable, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<PoliticianUserWritable> values, Context context)  throws IOException, InterruptedException {
        PoliticianUserWritable user = new PoliticianUserWritable();

        for(PoliticianUserWritable val : values){
            user.setValues(val);        }

        context.write(key, new Text(user.toString()));


    }
}
