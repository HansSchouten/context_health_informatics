package graphs;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;

import model.DateUtils;
import model.Record;
import model.SequentialData;
import model.datafield.DataFieldInt;

import org.junit.Test;

import analyze.labeling.Label;
import analyze.labeling.LabelFactory;

public class GraphTransformerTest {

    @Test
    public void setDataTest() {
        GraphDataTransformer gtc = new GraphDataTransformer();
        
        gtc.setData(null);
        assertEquals(null, gtc.data);
    }
    
    @Test
    public void setData1Test() {
        GraphDataTransformer gtc = new GraphDataTransformer();
        SequentialData sd = new SequentialData();
        gtc.setData(sd);
        assertEquals(sd, gtc.data);
        assertTrue(gtc.cols.length == 0);
    }
    
    @Test
    public void setData2Test() throws ParseException {
        GraphDataTransformer gtc = new GraphDataTransformer();
        SequentialData sd = new SequentialData();
        Record rec = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        sd.add(rec);
        rec.put("hoi", new DataFieldInt(10));
        
        gtc.setData(sd);
        assertEquals(sd, gtc.data);
        assertTrue(gtc.cols.length == 1);
    }
    
    @Test
    public void getJSONFromColumnTest() throws ParseException {
        GraphDataTransformer gtc = new GraphDataTransformer();
        SequentialData sd = new SequentialData();
        Record rec = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        sd.add(rec);
        rec.put("hoi", new DataFieldInt(10));
        
        Record rec1 = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        sd.add(rec1);
        rec1.put("hoi", new DataFieldInt(120));
        gtc.setData(sd);
        
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("hoi");
        
        ArrayList<String> inputNames = new ArrayList<String>();
        inputNames.add("hoi1");
        
        assertEquals("[[{\"hoi1\" : \"120\"}, {\"hoi1\" : \"10\"}]]", gtc.getJSONFromColumn(columns, inputNames, "test", false));
        assertTrue(gtc.cols.length == 1);
    }
    
    @Test
    public void getJSONFromColumnSingleAllowedTest() throws ParseException {
        GraphDataTransformer gtc = new GraphDataTransformer();
        SequentialData sd = new SequentialData();
        Record rec = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        sd.add(rec);
        rec.put("hoi", new DataFieldInt(10));
        
        Record rec1 = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        sd.add(rec1);
        rec1.put("hoi", new DataFieldInt(120));
        
        Record rec2 = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        sd.add(rec2);
        rec1.put("hoi1", new DataFieldInt(130));
        gtc.setData(sd);
        
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("hoi");
        columns.add("hoi1");
        
        ArrayList<String> inputNames = new ArrayList<String>();
        inputNames.add("hoi1");
        inputNames.add("hoi2");
        
        assertEquals("[[{}, {\"hoi1\" : \"120\", \"hoi2\" : \"130\"}, {\"hoi1\" : \"10\"}]]", gtc.getJSONFromColumn(columns, inputNames, "test", true));
        assertTrue(gtc.cols.length == 2);
    }
    
    @Test
    public void getJSONFromColumn1Test() throws ParseException {
        GraphDataTransformer gtc = new GraphDataTransformer();
        SequentialData sd = new SequentialData();
        Record rec = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        sd.add(rec);
        rec.put("hoi1", new DataFieldInt(10));
        
        Record rec1 = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        sd.add(rec1);
        rec1.put("hoi", new DataFieldInt(120));
        gtc.setData(sd);
        
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("hoi");
        
        ArrayList<String> inputNames = new ArrayList<String>();
        inputNames.add("hoi1");
        
        assertEquals("[[{\"hoi1\" : \"120\"}]]", gtc.getJSONFromColumn(columns, inputNames, "test", false));
        assertTrue(gtc.cols.length == 2);
    }
    
    @Test
    public void getPropertyTest() throws ParseException {
        GraphDataTransformer gtc = new GraphDataTransformer();
        Record rec = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        rec.put("hoi1", new DataFieldInt(10));
        
        assertEquals( "\"hoi\" : \"2015-06-10T00:00\"", gtc.getProperty("timestamp", "hoi", rec));
    }
    
    @Test
    public void getProperty1Test() throws ParseException {
        GraphDataTransformer gtc = new GraphDataTransformer();
        Record rec = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        rec.put("hoi1", new DataFieldInt(10));
        
        assertEquals( "\"hoi\" : \"10\"", gtc.getProperty("hoi1", "hoi", rec));
    }
    
    @Test
    public void getProperty2Test() throws ParseException {
        GraphDataTransformer gtc = new GraphDataTransformer();
        Record rec = new Record(DateUtils.parseDate("10-06-2015", "dd-MM-yyyy"));
        Label label = LabelFactory.getInstance().getNewLabel("hallo");
        rec.addLabel(label.getNumber());
        
        assertEquals( "\"hoi\" : \"hallo\"", gtc.getProperty("labels", "hoi", rec));
    }
}
