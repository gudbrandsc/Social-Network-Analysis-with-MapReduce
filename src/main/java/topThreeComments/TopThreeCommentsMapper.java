package topThreeComments;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TopThreeCommentsMapper extends Mapper<LongWritable, Text, Text, UserCommentWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // tokenize into words.

        JSONObject obj = new JSONObject(value.toString());
        String author1 = "Algaroth";
        String currAuthor = obj.getString("author");

        if(currAuthor.equals(author1)) {
            int upVotes = obj.getInt("ups");
            String body = obj.getString("body");

            UserCommentWritable userCommentWritable = new UserCommentWritable(body, upVotes);

            try {
                context.write(new Text(currAuthor), userCommentWritable);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
