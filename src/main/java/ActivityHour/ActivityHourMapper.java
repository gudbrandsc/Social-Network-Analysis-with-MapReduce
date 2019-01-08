package ActivityHour;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class ActivityHourMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        // tokenize into words.

        JSONObject obj = new JSONObject(value.toString());

        if(obj.getString("author").equals("rocky_m")) {

            long dateTimestamp = obj.getLong("created_utc");
            int hour = Instant.ofEpochSecond(dateTimestamp).atZone(ZoneId.of("UTC")).getHour();
            String textKey = Integer.toString(hour);

            try {
                context.write(new Text(textKey), new IntWritable(1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

