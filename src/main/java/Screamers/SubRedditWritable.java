package Screamers;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
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
public class SubRedditWritable implements Writable {
    private DoubleWritable score;
    private IntWritable numberOfComments;


    public SubRedditWritable(double score, int numberOfComments){
        this.score = new DoubleWritable(score);
        this.numberOfComments = new IntWritable(numberOfComments);
    }

    public SubRedditWritable(){
        this.score = new DoubleWritable(0.0);
        this.numberOfComments = new IntWritable(1);

    }

    public DoubleWritable getScore() {
        return score;
    }

    public IntWritable getNumberOfComments() {
        return numberOfComments;
    }

    public void write(DataOutput dataOutput) throws IOException {
        this.score.write(dataOutput);
        this.numberOfComments.write(dataOutput);


    }

    public void readFields(DataInput dataInput) throws IOException {
        this.score.readFields(dataInput);
        this.numberOfComments.readFields(dataInput);


    }

    @Override
    public String toString() {
        return this.score.get() + " " + this.numberOfComments.get();
    }

}