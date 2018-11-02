package uniqueSubreddits;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SubRedditMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Mapper.Context context)
        throws IOException, InterruptedException {
        // tokenize into words.
        JSONObject obj = null;

        try {
            obj = new JSONObject(value.toString());
            context.write(new Text(obj.getString("subreddit")), new IntWritable(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // emit word, count pairs.
    }
}
