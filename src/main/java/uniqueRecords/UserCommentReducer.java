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



       if(!checkIfLarges(key,uw)){
            if(!checkIfSecondLarges(key,uw)){
                if(!checkIftheirdLargest(key,uw)){

                }
            }
       }
    }

    private boolean checkIfLarges(Text key, UserWritable userWritable){
        if(this.largest == null){
            this.largest = userWritable;
            this.largestKey.set(key);
            return true;
        }else {
            if( this.largest.getCount().get() < userWritable.getCount().get()){
                checkIfSecondLarges(this.largestKey, this.largest);
                this.largest = userWritable;
                this.largestKey.set(key);
                return true;
            }else {
                return false;
            }
        }
    }

    private boolean checkIfSecondLarges(Text key, UserWritable userWritable){
        if(this.secondLargest == null){
            this.secondLargest = userWritable;
            this.secondLargestKey.set(key);
            return true;
        }else {
            if(this.secondLargest.getCount().get() < userWritable.getCount().get()){
                checkIftheirdLargest(this.secondLargestKey, this.secondLargest);
                this.secondLargest = userWritable;
                this.secondLargestKey.set(key);
                return true;
            }else {
                return false;

            }

        }
    }

    private boolean checkIftheirdLargest(Text key, UserWritable userWritable){
        if(this.theirdLargest == null){
            this.theirdLargest = userWritable;
            this.theirdLargestKey.set(key);
            return true;
        }else {
            if(this.theirdLargest.getCount().get() < userWritable.getCount().get()){
                this.theirdLargest = userWritable;
                this.theirdLargestKey.set(key);
                return true;
            } else {
                return false;

            }
        }
    }




    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(this.largestKey, this.largest);
        context.write(this.secondLargestKey, this.secondLargest);
        context.write(this.theirdLargestKey, this.theirdLargest);


    }
}
