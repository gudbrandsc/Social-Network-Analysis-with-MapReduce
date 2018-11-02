package uniqueRecords;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Container for the counts of obama, hillary, and trump found in comments.
 * Demonstrates how to make a custom writable, but note that this shouldn't be
 * used as a key because it is *not* comparable. For that, see
 * WritableComparable.
 *
 * @author malensek
 */
public class UserWritable extends LongWritable implements Writable {
    private LongWritable count;

    public UserWritable(){
        this.count = new LongWritable(1);

    }

    public UserWritable(long value){
        this.count = new LongWritable(value);
    }

    public LongWritable getCount() {
        return count;
    }



    private void incrementCount(long count ){
        this.count.set(this.count.get() + count);
    }

    public void incrementCount(UserWritable user){
        incrementCount(user.count.get());
    }

    public void write(DataOutput dataOutput) throws IOException {
        this.count.write(dataOutput);

    }


    public void readFields(DataInput dataInput) throws IOException {
        this.count.readFields(dataInput);

    }

    @Override
    public String toString() {
        return count.toString();
    }

}