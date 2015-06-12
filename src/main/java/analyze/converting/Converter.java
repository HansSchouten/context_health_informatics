package analyze.converting;

import java.time.LocalDateTime;
import java.util.HashMap;

import model.ChunkedSequentialData;
import model.datafield.DataField;
import model.datafield.DataFieldBoolean;
import model.datafield.DataFieldInt;
import model.datafield.DataFieldString;
import model.Record;
import model.RecordList;
import model.SequentialData;
import model.UnsupportedFormatException;
import analyze.chunking.ChunkOnPeriod;
import analyze.chunking.ChunkType;
import analyze.chunking.Chunker;

/**This class represents an object that automatically generates behavior of web site response.
 *
 */

public class Converter {

    /**
     * This variable stores the values of the columns that are evaluated.
     */
    private HashMap<String, DataField> columnValues;

    /**
     * This variable stores the user data that needs to be converted.
     */
    private static SequentialData userData;

    /**
     * This variable stores the name of the column containing the measurement values.
     */
    private String column;

    /**
     * This variable stores the measurement after the first five.
     */
    private RecordList afterFive;

    /**
     * This variable stores the kreatinine status of the sixth measurement.
     */
    private int firstStatus;

    /**
     * Construct a conversion that consists of the data that needs to be converted
     * and the column containing measurement values.
     * @param data      the data that needs to be converted
     * @param value     the name of the column containing the measurement values
     */
    public Converter(SequentialData data, String value) {
        userData = data;
        column = value;
        columnValues = new HashMap<String, DataField>();
    }

    /** This method converts measured values into expected web site response.
     * @throws UnsupportedFormatException - thrown when not a numerical value has been entered.
     * @return the user data with calculated border, status and feedback
     */
    public SequentialData convert() throws UnsupportedFormatException {
        int index = 0;
        HashMap<String, DataField> formerFive = new HashMap<String, DataField>();
        columnValues = new HashMap<String, DataField>();
        DataFieldInt border = new DataFieldInt(0);
        afterFive = new RecordList(userData.getColumns());
        firstStatus  = 0;

        for (Record rec : userData) {
            index++;

            columnValues.put(String.valueOf(index), rec.get(column));

            if (index > 5) {
            formerFive = calculateFM(index);
            double gm = calculateGM(formerFive);
            double sd = calculateSD(formerFive, gm);

            border = determineBorder(rec.get(column).getDoubleValue(), gm, sd);
            rec.put("grensgebied", border);

            afterFive.addRecord(rec);

            } else {
                rec.put("grensgebied", new DataFieldString("N.A."));
                rec.put("kreatinine status", new DataFieldString("N.A."));
                rec.put("feedback", new DataFieldString("N.A."));
            }
        }

        SequentialData borders = new SequentialData();
        borders.addRecordList(afterFive);
        ChunkType chunkType = new ChunkOnPeriod(borders, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(borders, chunkType);

        fillDailyStatus(chunks);
        firstStatus = borders.first().get("kreatinine status").getIntegerValue();

        fillFeedback(chunks);

        return userData;
    }

    /** This method fills in the kreatinine status for the user data.
     * @param chunks the user data chunked per day
     * @throws UnsupportedFormatException - thrown when not a numerical value has been entered
     */
    public void fillDailyStatus(ChunkedSequentialData chunks) throws UnsupportedFormatException {

        for (SequentialData chunkValues : chunks.getChunkedData().values()) {
            for (Record rec : chunkValues) {
                String timeStamp = rec.getTimeStamp().toString();
                String date = timeStamp.substring(0, 10);
                SequentialData chunkSameDay = chunks.get(date);

                int first = chunkSameDay.first().get("grensgebied").getIntegerValue();
                int second = chunkSameDay.last().get("grensgebied").getIntegerValue();

                DataFieldInt dailyStatus = determineDailyStatus(first, second);

                rec.put("kreatinine status", dailyStatus);
            }
        }
    }

    /** This method fills in the feedback column for the user data.
     * @param chunks        the user data chunked per day
     * @throws UnsupportedFormatException - thrown when not a numerical value has been entered
     */
    public void fillFeedback(ChunkedSequentialData chunks)
            throws UnsupportedFormatException {

        DataFieldInt previousStatus = new DataFieldInt(firstStatus);

        for (Object key : chunks.getChunkedData().keySet()) {
            DataFieldString feedback =
                    determineFeedback(chunks.get(key).first().get("kreatinine status").getIntegerValue(),
                    previousStatus.getIntegerValue());
            previousStatus = (DataFieldInt) chunks.get(key).first().get("kreatinine status");

            for (Record rec : chunks.get(key)) {
                rec.put("feedback", feedback);
            }
        }
   }


    /** This method selects the five measurements prior to the current one.
     * @param index     index of the current measurement
     * @return fm       the five previous measured values
     */
    public HashMap<String, DataField> calculateFM(int index)  {
        HashMap<String, DataField> fiveM = new HashMap<String, DataField>();
        int index2 = 6;
        for (int i = index - 1; i >= index - 5; i--) {
            index2--;
            fiveM.put(String.valueOf(index2), columnValues.get(String.valueOf(i)));
        }
        return fiveM;
    }

    /** This method calculates the average of the five former measurements.
     * @param fiveM     the five former measurement values
     * @throws UnsupportedFormatException - thrown when not a numerical value is entered
     * @return the average of the five former measurements
     */
    public double calculateGM(HashMap<String, DataField> fiveM) throws UnsupportedFormatException {
        double gm = 0;
        double sum = 0;

        for (int i = 1; i < fiveM.size() + 1; i++) {
            sum = sum + fiveM.get(String.valueOf(i)).getDoubleValue();
        }

        gm = sum / fiveM.size();

        return gm;
    }

    /** This method calculates the standard deviation of the five former measurements.
     * @param fiveM     the five former measurement values
     * @param gm        the average value of the five former measurements
     * @throws UnsupportedFormatException - thrown when not a numerical value is entered
     * @return the standard deviation
     */
    public double calculateSD(HashMap<String, DataField> fiveM, double gm) throws UnsupportedFormatException  {

        double difference = 0;
        double sum = 0;

        for (int i = 1; i < fiveM.size() + 1; i++) {

            sum = sum + Math.pow(fiveM.get(String.valueOf(i)).getDoubleValue() - gm, 2);
        }

        difference = Math.sqrt(sum / 5);

        return difference;
    }


    /** This method determines the corresponding critical border of the measured value.
     * @param value     the measured value
     * @param gm        average of the five former measurements
     * @param sd        standard deviation of the five former measurements
     * @return the border value corresponding to the measurement value
     */
    public DataFieldInt determineBorder(double value, double gm, double sd) {

        DataFieldInt status = null;

        if (value >= 0 && value < gm) {
            status = new DataFieldInt(2);
        } else if (value >= gm && value < Math.max(gm + sd, 1.15 * gm)) {
            status = new DataFieldInt(3);
        } else if (value >= Math.max(gm + sd, 1.15 * gm) && value < Math.max(gm + 1.5 * sd, 1.25 * gm)) {
            status = new DataFieldInt(4);
        } else {
            status = new DataFieldInt(5);
        }

        return status;
    }

    /** This method determines the daily status based on the one or two measurements of that day.
     * @param firstValue    measurement of the previous day
     * @param secondValue   measurement of current day
     * @return the daily kreatinine status corresponding to the chosen record
     */
    public DataFieldInt determineDailyStatus(int firstValue, int secondValue) {

        DataFieldInt status = new DataFieldInt(0);

        if (firstValue == 2 || firstValue == 3) {
            status = new DataFieldInt(firstValue);
        } else if (firstValue == 4) {
            status = new DataFieldInt(secondValue);
        } else if (firstValue == 5) {
            if (secondValue != 5) {
                status = new DataFieldInt(secondValue + 1);
            } else {
                status = new DataFieldInt(5);
            }
        }
        return status;
    }

    /** This method generates the feedback based on kreatinine status of the last two days.
     * @param fstStatus       kreatinine status of the previous day
     * @param secondStatus      kreatinine status of the current day
     * @return the expected feedback based on measured levels
     */
    public DataFieldString determineFeedback(int fstStatus, int secondStatus) {

        DataFieldString feedback = new DataFieldString("");

        if (fstStatus == 2 || fstStatus == 3) {
            if (secondStatus == 2 || secondStatus == 3) {
                feedback = new DataFieldString("niets doen");
            }
            if (secondStatus == 4) {
                feedback = new DataFieldString("meting morgen herhalen");
            }
            if (secondStatus == 5) {
                feedback = new DataFieldString("neem contact met het ziekenhuis");
            }
        } else if (fstStatus == 4) {
            if (secondStatus == 2) {
                feedback = new DataFieldString("niets doen");
            }
            if (secondStatus == 3) {
                feedback = new DataFieldString("meting morgen herhalen");
            }
            if (secondStatus == 4 || secondStatus == 5) {
                feedback = new DataFieldString("neem contact met het ziekenhuis");
            }
        } else if (fstStatus == 5) {
            feedback = new DataFieldString("volg advies arts");
        }
        return feedback;
    }

    /** This method checks if patients follow up the advice to re-measure the same day.
     * @param data              The user data to check in.
     * @param advice            name of the column indicating if second measurement should be conducted
     *                          1 = yes, NULL = no
     * @throws UnsupportedFormatException - when the input type is not as expected
     */
    public static void checkSecondMeasurement(SequentialData data, String advice)throws UnsupportedFormatException {
        ChunkType chunkType = new ChunkOnPeriod(data, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(data, chunkType);

        for (Record rec : data) {
            if (rec.containsKey(advice)) {
                String timeStamp = rec.getTimeStamp().toString();
                String date = timeStamp.substring(0, 10);
                SequentialData chunkSameDay = chunks.get(date);

                if (chunkSameDay.last() != rec) {
                    rec.put("second measurement", new DataFieldBoolean(true));
                } else {
                    rec.put("second measurement", new DataFieldBoolean(false));
                }

            } else {
                rec.put("second measurement", new DataFieldString("N.A."));
            }
        }
    }

    /** This method checks if patients follow up the advice to re-measure the following day.
     * @param rec      the record that should be evaluated
     * @throws UnsupportedFormatException - thrown when the input type is not as expected
     */
    public static void checkRemeasurement(Record rec) throws UnsupportedFormatException {
        ChunkType chunkType = new ChunkOnPeriod(userData, 1);
        Chunker chunker = new Chunker();
        ChunkedSequentialData chunks = (ChunkedSequentialData) chunker.chunk(userData, chunkType);

        if (rec.get("feedback").toString() == "meting morgen herhalen") {
            LocalDateTime timeStamp = rec.getTimeStamp();
            // long one_day = 25 * 60 * 60 * 1000;
            String tomorrow = timeStamp.plusDays(1).toString();
            String dateTomorrow = tomorrow.toString().substring(0, 10);

            if (chunks.get(dateTomorrow) != null && chunks.get(dateTomorrow).size() >= 2) {
            rec.put("remeasurement", new DataFieldBoolean(true));
            } else {
                rec.put("remeasurement", new DataFieldBoolean(false));
            }

        } else {
            rec.put("remeasurement", new DataFieldString("N.A."));
        }
    }
}
