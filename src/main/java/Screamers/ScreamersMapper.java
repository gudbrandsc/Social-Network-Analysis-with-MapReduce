package Screamers;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ScreamersMapper extends Mapper<LongWritable, Text, Text, SubRedditWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // tokenize into words.

        JSONObject obj = new JSONObject(value.toString());
        String body = obj.getString("body");
        double count = 0;
        int totLetters = 0;
        int length = body.length();

        if(length > 0) {
            for (int i = 0; i < length; i++) {
                if(Character.isLetter(body.charAt(i))){
                    totLetters++;
                    if (Character.isUpperCase(body.charAt(i))) {
                        count++;
                    }
                }

            }

            if(totLetters > 0) {
                double score = ((count / (double) totLetters) * 100);


                SubRedditWritable writable = new SubRedditWritable(score, 1);

                try {
                    context.write(new Text(obj.getString("subreddit")), writable);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}