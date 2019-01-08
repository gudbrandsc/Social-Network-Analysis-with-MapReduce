package SentimentAnalysis;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

public class SentimentAnalysisMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private HashMap<String, Integer> afinn_map = getHashmap();


    HashMap<String, Integer>  getHashmap(){
        HashMap<String, Integer> map = new HashMap<>();
        try {

            InputStream in = getClass().getResourceAsStream("/AFINN-111.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String word = line.split("\t")[0];
                Integer score = Integer.parseInt(line.split("\t")[1]);
                map.put(word, score);
            }
        } catch (IOException e) {
            System.out.println("couldnt read line");
        }
        return map;
    }

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // tokenize into words.
        JSONObject obj = new JSONObject(value.toString());
        String comment = obj.getString("body");

        Integer sum_score = 0;
        Integer n_words_in_comment = 0;

        // go through words, adding score
        for (String word : comment.split(" ")) {
            n_words_in_comment++;
            if (afinn_map.containsKey(word.toLowerCase())) {
                sum_score += afinn_map.get(word.toLowerCase());
            }
        }


        try {
            context.write(new Text(obj.getString("subreddit")), new DoubleWritable(sum_score/(double) n_words_in_comment ) );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
