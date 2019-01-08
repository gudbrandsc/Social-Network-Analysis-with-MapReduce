package uniqueRecords;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserCommentMapper extends Mapper<LongWritable, Text, Text, UserWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
        // tokenize into words.
        JSONObject obj = null;
        UserWritable userWritable = new UserWritable();
        obj = new JSONObject(value.toString());

        if(!(obj.getString("author").equals("[deleted]"))) {
            try {
                context.write(new Text(obj.getString("author")), userWritable);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
