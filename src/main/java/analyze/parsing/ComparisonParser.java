package analyze.parsing;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import model.DataFieldInt;
import model.DateUtils;
import model.Record;
import model.SequentialData;
import analyze.AnalyzeException;
import analyze.labeling.LabelFactory;

/**
 * This class parses Compare constructs.
 * @author Hans Schouten
 *
 */
public class ComparisonParser implements SubParser {

	@Override
	public SequentialData parseOperation(String operation, SequentialData data) throws AnalyzeException {
		if (operation.startsWith("PATTERN")) {
			int occurenceCount = 0;
			ArrayList<Integer> labelSequence = getLabels(operation);
			int currentLabelIndex = 0;
			for (Record record : data) {
				if (record.containsLabel(labelSequence.get(currentLabelIndex))) {
					if (currentLabelIndex == labelSequence.size() - 1) {
						occurenceCount++;
						currentLabelIndex = 0;
					} else {
						currentLabelIndex++;
					}
				}
			}
			SequentialData result = new SequentialData();
			Record rec;
			try {
				rec = new Record(DateUtils.parseDate("1111/11/11", "yyyy/mm/DD"));
				rec.put("pattern_count", new DataFieldInt(occurenceCount));
				result.add(rec);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;


		} else if (operation.startsWith("TIME")) {

		}

		return data;
	}

	/**
	 * Return the label sequence this comparison needs to look for.
	 * @param operation		- the string containing the labels.
	 * @return				- the label sequence.
	 */
	public ArrayList<Integer> getLabels(String operation) {
		LabelFactory labelFactory = LabelFactory.getInstance();
		ArrayList<Integer> labelSequence = new ArrayList<Integer>();
		while (operation.contains("(")) {
			String[] labelSplitted = operation.split("\\(", 2);
			String label = labelSplitted[1].split("\\)", 2)[0];
			int labelNumber = labelFactory.getNumberOfLabel(label);
			labelSequence.add(labelNumber);
			operation = labelSplitted[1];
		}
		return labelSequence;
	}

}
