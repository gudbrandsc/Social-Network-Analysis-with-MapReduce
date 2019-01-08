package NextPolitician;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NextPoliticianMapper extends Mapper<LongWritable, Text, Text, PoliticianUserWritable> {

    private HashMap<String, Integer> afinn_map = getHashmap();
    private ArrayList<String> subredditsList = getSubRedditList();


    private ArrayList<String> getSubRedditList(){
        ArrayList<String> list = new ArrayList<>();
        try {
            InputStream in = getClass().getResourceAsStream("/politics-subreddit.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line = null;

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private HashMap<String, Integer>  getHashmap(){
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


        String subreddit =  obj.getString("subreddit");


        //Check subreddit
        if(subredditsList.contains(subreddit)){
            String body = obj.getString("body").toLowerCase();
            int downs = 0;
            int ups = 0;

            if(obj.has("downs")){
                downs = obj.getInt("downs");
            }
            if(obj.has("ups")){
                ups = obj.getInt("ups");
            }


            boolean isTrump = conatinTrump(body);
            boolean isClinton = conatinClinton(body);
            boolean isStein = conatinStein(body);
            boolean isJohnson = conatinJohnson(body);


            if(isTrump && !isClinton && !isJohnson && !isStein){
                Double score = getSentimentScore(body).get();
                boolean type = false;

                if(score >= 0){
                    type = true;

                }

                PoliticianUserWritable trump = new PoliticianUserWritable(ups, downs, type);
                try {
                    context.write(new Text("Donald Trump"),trump );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(!isTrump && isClinton && !isJohnson && !isStein){
                Double score = getSentimentScore(body).get();
                boolean type = false;

                if(score >= 0){
                    type = true;

                }
                PoliticianUserWritable clinton = new PoliticianUserWritable(ups, downs, type);

                try {
                    context.write(new Text("Hillary Clinton"), clinton );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(!isTrump && !isClinton && isJohnson && !isStein){
                Double score = getSentimentScore(body).get();
                boolean type = false;

                if(score >= 0){
                    type = true;

                }
                PoliticianUserWritable johnson = new PoliticianUserWritable(ups, downs, type);

                try {
                    context.write(new Text("Gary Johnson"), johnson );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(!isTrump && !isClinton && !isJohnson && isStein){
                Double score = getSentimentScore(body).get();
                boolean type = false;

                if(score >= 0){
                    type = true;

                }
                PoliticianUserWritable stein = new PoliticianUserWritable(ups, downs, type);

                try {
                    context.write(new Text("Jill Stein"), stein );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private boolean conatinJohnson(String body){
        String gary = "gary";
        String johnson = "johnson";
        if((body.contains(gary) || body.contains(johnson))){
            return true;
        }
        return false;
    }

    private boolean conatinStein(String body){
        String jill = "jill";
        String stein = "stein";
        if((body.contains(jill) || body.contains(stein))){
            return true;
        }
        return false;
    }

    private boolean conatinTrump(String body){
        String donald = "donald";
        String trump = "trump";
        if((body.contains(donald) || body.contains(trump))){
            return true;
        }
        return false;
    }

    private boolean conatinClinton(String body){
        String hillary = "hillary";
        String clinton = "clinton";
        if((body.contains(hillary) || body.contains(clinton))){
            return true;
        }
        return false;
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
