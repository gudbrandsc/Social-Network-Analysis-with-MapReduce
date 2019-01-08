package FeatureScaling;

        import org.apache.hadoop.io.IntWritable;
        import org.apache.hadoop.io.LongWritable;
        import org.apache.hadoop.io.Text;
        import org.apache.hadoop.mapreduce.Mapper;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.time.Instant;
        import java.time.LocalDate;
        import java.time.ZoneId;

public class FeatureScalingMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Mapper.Context context)
            throws IOException, InterruptedException {

        JSONObject obj = new JSONObject(value.toString());

        long dateTimestamp = obj.getLong("created_utc");
        LocalDate date = Instant
                .ofEpochSecond(dateTimestamp)
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();

        String keyDate =  date.getMonthValue() + "/" + date.getYear();

        try {
            context.write(new Text(keyDate),  new IntWritable(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}