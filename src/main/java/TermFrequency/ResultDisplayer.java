package TermFrequency;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Container for the counts of obama, hillary, and trump found in comments.
 * Demonstrates how to make a custom writable, but note that this shouldn't be
 * used as a key because it is *not* comparable. For that, see
 * WritableComparable.
 *
 * @author malensek
 */
public class ResultDisplayer extends LongWritable implements Writable {
    private LongWritable count;
    private Text key;



    public ResultDisplayer(String key, int count){
        this.count = new LongWritable(count);
        this.key = new Text(key);
    }



    public void readFields(DataInput dataInput) throws IOException {

        this.count.readFields(dataInput);
        this.key.readFields(dataInput);
    }



    @Override
    public String toString() {
        return  ":" + this.count.get();
    }
}