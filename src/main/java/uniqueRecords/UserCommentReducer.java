package uniqueRecords;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class UserCommentReducer extends Reducer<Text, UserWritable, Text, UserWritable> {

    private UserWritable largest = null;
    private Text largestKey = new Text();
    private UserWritable secondLargest = null;
    private Text secondLargestKey = new Text();
    private UserWritable theirdLargest = null;
    private Text theirdLargestKey = new Text();

    @Override
    protected void reduce(Text key,  Iterable<UserWritable> values, Context context)  throws IOException, InterruptedException {
        // calculate the total count
        UserWritable uw = new UserWritable(0);

        for(UserWritable val : values) {
            uw.incrementCount(val);
        }
        context.write(key, uw);

        //  checkIfLarges(key, uw);
    }

    private boolean checkIfLarges(Text key, UserWritable userWritable){
        if(this.largest == null){
            this.largest = userWritable;
            this.largestKey.set(key);
            return true;
        }else {
            if( this.largest.getCount().get() < userWritable.getCount().get()){
                this.largest = userWritable;
                this.largestKey.set(key);
                return true;
            }else {
                return false;
            }
        }
    }





/*
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(this.largestKey, this.largest);



    }*/
}
