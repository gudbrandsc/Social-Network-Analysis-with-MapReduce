package TermFrequency;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TermFrequencyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {

        JSONObject obj = new JSONObject(value.toString());


        String subreddit =  obj.getString("subreddit");

        if(subreddit.equals("programming")) {

            String body = obj.getString("body").toLowerCase();


            Pattern p = Pattern.compile("[a-zA-Z]+");

            Matcher m1 = p.matcher(body);

            while (m1.find()) {
                String word = m1.group();
                if(word.length() > 1) {
                    try {
                        context.write(new Text(m1.group()), new IntWritable(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
