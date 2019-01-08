package SignificantDay;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;
import topThreeComments.UserCommentWritable;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class SignificantDayMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // tokenize into words.

        JSONObject obj = new JSONObject(value.toString());

        long dateTimestamp = obj.getLong("created_utc");
        LocalDate date = Instant
                .ofEpochSecond(dateTimestamp)
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();

        int day = date.getDayOfMonth();

        if(day == 31){
            String body = obj.getString("body");
            String daykey = Integer.toString(day);
            try {
                context.write(new Text(daykey), new Text(body));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

