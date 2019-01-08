package NextPolitician;

import org.apache.hadoop.io.*;

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


public class PoliticianUserWritable implements Writable {

    private IntWritable upvotes;
    private IntWritable downVotes;
    private BooleanWritable posNeg;

    private IntWritable posUpVotes;
    private IntWritable posDownVotes;
    private IntWritable negUpVotes;
    private IntWritable negDownVotes;
    private IntWritable totPosComments;
    private IntWritable totNegComments;



    public void setPosUpVotes(IntWritable votes) {
        this.posUpVotes.set(this.posUpVotes.get() + votes.get());
    }

    public void setPosDownVotes(IntWritable votes) {
        this.posDownVotes.set(this.posDownVotes.get() + votes.get());
    }

    public void setNegUpVotes(IntWritable votes) {
        this.negUpVotes.set(this.negUpVotes.get() + votes.get());
    }

    public void setNegDownVotes(IntWritable votes) {
        this.negDownVotes.set(this.negDownVotes.get() + votes.get());
    }

    public void setValues(PoliticianUserWritable user){
        if(user.posNeg.get()){
            this.totPosComments.set(this.totPosComments.get() + 1);
            setPosUpVotes(user.upvotes);
            setPosDownVotes(user.downVotes);
        } else {
            this.totNegComments.set(this.totNegComments.get() + 1);
            setNegUpVotes(user.upvotes);
            setNegDownVotes(user.downVotes);
        }
    }



    //Custom Constructor
    public PoliticianUserWritable(int up, int down, boolean type) {
        this.upvotes = new IntWritable(up);
        this.downVotes = new IntWritable(down);
        this.posNeg = new BooleanWritable(type);
        this.posUpVotes = new IntWritable(0);
        this.posDownVotes = new IntWritable(0);
        this.negUpVotes = new IntWritable(0);
        this.negDownVotes = new IntWritable(0);
        this.totPosComments = new IntWritable(0);
        this.totNegComments = new IntWritable(0);

    }

    public PoliticianUserWritable() {
        this.upvotes = new IntWritable(0);
        this.downVotes = new IntWritable(0);
        this.posNeg = new BooleanWritable(true);
        this.posUpVotes = new IntWritable(0);
        this.posDownVotes = new IntWritable(0);
        this.posDownVotes = new IntWritable(0);
        this.negUpVotes = new IntWritable(0);
        this.negDownVotes = new IntWritable(0);
        this.totPosComments = new IntWritable(0);
        this.totNegComments = new IntWritable(0);
    }


    @Override
    //overriding default readFields method.
    //It de-serializes the byte stream data
    public void readFields(DataInput in) throws IOException {
        upvotes.readFields(in);
        downVotes.readFields(in);
        posNeg.readFields(in);
        posUpVotes.readFields(in);
        posDownVotes.readFields(in);
        negUpVotes.readFields(in);
        negDownVotes.readFields(in);
        totPosComments.readFields(in);
        totNegComments.readFields(in);
    }

    @Override
    //It serializes object data into byte stream data
    public void write(DataOutput out) throws IOException {
        upvotes.write(out);
        downVotes.write(out);
        posNeg.write(out);
        posUpVotes.write(out);
        posDownVotes.write(out);
        negUpVotes.write(out);
        negDownVotes.write(out);
        totPosComments.write(out);
        totNegComments.write(out);
    }

    @Override
    public String toString() {
        return "Total positive comments: " + this.totPosComments.get() + " Positive upvotes: " + this.posUpVotes.get() + " Posetive downvotes: " + this.posDownVotes +
                " Total negative comments: " + this.totNegComments.get() + " Negative upvotes: " + this.negUpVotes.get() + " Negative downvotes: " + this.negDownVotes;
    }

}