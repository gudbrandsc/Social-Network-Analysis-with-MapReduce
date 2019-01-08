package BackgroundStory;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import uniqueRecords.UserWritable;

import java.io.IOException;

public class BackgroundStoryReducer extends Reducer<Text, UserStoryWritable, Text, UserStoryWritable> {

    @Override
    protected void reduce(Text key,  Iterable<UserStoryWritable> values, Context context)  throws IOException, InterruptedException {

        int num = 0;
        double sentimentScore = 0.0;
        StringBuilder builder = new StringBuilder();
        for(UserStoryWritable val : values) {
            num++;
           if(val.isHaveFrom()){
               builder.append("\n\nBody --> " + val.getFromText() +"\n");
            }
            sentimentScore += val.getSentimentScore().get();

        }

        double score = sentimentScore/num;
        Text fromText = new Text(builder.toString());

        UserStoryWritable writable = new UserStoryWritable();
        writable.setSubReddit(key);
        writable.setFromText(fromText);
        writable.setNumberOfComments(num);
        writable.setSentimentScore(score);

        context.write(key, writable);





    }
}
