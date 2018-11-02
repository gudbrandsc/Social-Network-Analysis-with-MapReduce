package topThreeComments;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Container for the counts of obama, hillary, and trump found in comments.
 * Demonstrates how to make a custom writable, but note that this shouldn't be
 * used as a key because it is *not* comparable. For that, see
 * WritableComparable.
 *
 * @author malensek
 */
public class UserCommentWritable extends LongWritable implements Writable {
    private LongWritable commentVotes;
    private Text commentBody;
    private Text largestText;
    private LongWritable largestNum ;
    private Text secondLargestText;
    private LongWritable secondLargestNum;
    private Text theirdLargestText;
    private LongWritable theirdLargestnum;



    public UserCommentWritable(String body, int votes){
        this.commentVotes = new LongWritable(votes);
        this.commentBody = new Text(body);

    }

    public UserCommentWritable(){
        this.commentVotes = new LongWritable();
        this.commentBody = new Text();
        this.largestText = new Text();
        this.largestNum = new LongWritable(0);
        this.secondLargestText =  new Text();
        this.secondLargestNum = new LongWritable(0);
        this.theirdLargestText =  new Text();
        this.theirdLargestnum = new LongWritable(0);
    }

    public Text getCommentBody() {
        return commentBody;
    }

    public void isLarge(Text body, LongWritable votes){
        checkIfLarges(body, votes);
      /*  if(!checkIfLarges(body, votes)){
            if(!checkIfSecondLarges(body, votes)){
                if(!checkIftheirdLargest(body, votes)){

                }
            }
        }*/
    }


    private boolean checkIfLarges(Text body, LongWritable votes){
        if (this.largestNum.get() < votes.get()) {
           // checkIfSecondLarges(this.largestText, this.largestNum);
            this.largestNum = votes;
            this.largestText = body;
            return true;
        }
        return false;
    }

    private boolean checkIfSecondLarges(Text body, LongWritable votes ){
        if(this.secondLargestNum.get() < votes.get()){
            checkIftheirdLargest(this.secondLargestText, this.secondLargestNum);
            this.secondLargestNum = votes;
            this.secondLargestText = body;
            return true;
        }
        return false;
    }

    private boolean checkIftheirdLargest(Text body, LongWritable votes ){
        if(this.theirdLargestnum.get() < votes.get()){
            this.theirdLargestnum = votes;
            this.theirdLargestText = body;
            return true;
        }
        return false;
    }




    public LongWritable getCommentVotes() {
        return commentVotes;
    }

    public void write(DataOutput dataOutput) throws IOException {
        this.commentVotes.write(dataOutput);
        this.commentBody.write(dataOutput);

    }


    public void readFields(DataInput dataInput) throws IOException {

        this.commentVotes.readFields(dataInput);
        this.commentBody.readFields(dataInput);
    }

    @Override
    public String toString() {
        return "\nComment count: " + this.largestNum.get()+ "\nText: " + this.largestText.toString() + "\n";
    }
}