package BackgroundStory;

import org.apache.hadoop.io.*;

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


public class UserStoryWritable implements WritableComparable<UserStoryWritable>
{

    private Text fromText,subReddit;
    private DoubleWritable sentimentScore;
    private int numberOfComments;
    private BooleanWritable haveFrom;


    //Custom Constructor
    public UserStoryWritable() {
        this.fromText = new Text();
        this.subReddit = new Text();
        this.numberOfComments = 0;
        this.sentimentScore = new DoubleWritable();
        this.haveFrom = new BooleanWritable(false);
    }

    public boolean isHaveFrom() {
        return haveFrom.get();
    }
    public void setHaveFrom(){
        haveFrom = new BooleanWritable(true);
    }

    public void setFromText(Text fromText) {
        this.fromText = fromText;
    }

    public void setSubReddit(Text subReddit) {
        this.subReddit = subReddit;
    }

    public void setSentimentScore(DoubleWritable sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }



    public void setSentimentScore(double score){
        this.sentimentScore.set(score);
    }


    public Text getFromText() {
        return this.fromText;
    }

    public Text getSubReddit() {
        return this.subReddit;
    }

    public DoubleWritable getSentimentScore() {
        return this.sentimentScore;
    }

    @Override
    //overriding default readFields method.
    //It de-serializes the byte stream data
    public void readFields(DataInput in) throws IOException {
        fromText.readFields(in);
        subReddit.readFields(in);
        sentimentScore.readFields(in);
        haveFrom.readFields(in);

    }

    @Override
    //It serializes object data into byte stream data
    public void write(DataOutput out) throws IOException
    {
        fromText.write(out);
        subReddit.write(out);
        sentimentScore.write(out);
        haveFrom.write(out);
    }

    @Override
    public String toString() {

        return "\n---------------------------------------------\n"+ "subReddit:" + this.subReddit+ "\nFrom text: " + this.fromText + "\nNumber of comments on subReddit: " + this.numberOfComments + "\nSentimentScore: " + this.sentimentScore.toString() + "\n";
    }

    @Override
    public int compareTo(UserStoryWritable o) {
        return 0;
    }
}