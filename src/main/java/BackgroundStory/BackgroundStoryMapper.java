package BackgroundStory;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;

public class BackgroundStoryMapper extends Mapper<LongWritable, Text, Text, UserStoryWritable> {
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

        JSONObject obj = new JSONObject(value.toString());

        if(obj.getString("author").equals("fotopaper")){
            UserStoryWritable userStoryWritable = new UserStoryWritable();


            Text body = new Text(obj.getString("body"));
            //i'm from, i live in,
            String bodyString = body.toString().toLowerCase();

            if((bodyString.contains("i am from")) || bodyString.contains("i live in") || bodyString.contains("i'm from")){
                userStoryWritable.setHaveFrom();
                userStoryWritable.setFromText(body);
            }

            userStoryWritable.setNumberOfComments(1);
            DoubleWritable val = getSentimentScore(body.toString());
            userStoryWritable.setSentimentScore(val);
            Text sub = new Text(obj.getString("subreddit"));

            userStoryWritable.setSubReddit(sub);

            //TODO take body and do sentimental analysis



            try {
                context.write(sub, userStoryWritable);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private DoubleWritable getSentimentScore(String comment){
        Integer sum_score = 0;
        Integer n_words_in_comment = 0;

        // go through words, adding score
        for (String word : comment.split(" ")) {
            n_words_in_comment++;
            if (afinn_map.containsKey(word.toLowerCase())) {
                sum_score += afinn_map.get(word.toLowerCase());
            }
        }
        return new DoubleWritable(sum_score/(double) n_words_in_comment);
    }
}