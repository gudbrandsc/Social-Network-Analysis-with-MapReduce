package topThreeComments;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TopThreeCommentsReducer  extends Reducer<Text, UserCommentWritable, Text, UserCommentWritable> {
    UserCommentWritable largest = null;
    Text largestKey = new Text();
    @Override
    protected void reduce(Text key,  Iterable<UserCommentWritable> values, Context context)  throws IOException, InterruptedException {
        // calculate the total count
            UserCommentWritable userCommentWritable = new UserCommentWritable();



        for(UserCommentWritable val : values) {
            userCommentWritable.isLarge(val.getCommentBody(), val.getCommentVotes());
        }

        context.write(key, userCommentWritable);
    }

}

