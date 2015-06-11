package analyze.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Record;
import model.SequentialData;
import model.datafield.DataField;
import analyze.AnalyzeException;

/**
 * This class represents a project parser, to exclude a column.
 * @author Matthijs
 *
 */
public class ProjectParser implements SubParser {

    /** This variables stores the pattern to detect a column. */
    protected Pattern pattern;

    /**
     * Construct a new ProjectParser and set the pattern.
     */
    public ProjectParser() {
        pattern = Pattern.compile("(col|COL)\\((\\w+)\\)");
    }

    @Override
    public ParseResult parseOperation(String operation, SequentialData data)
            throws AnalyzeException {

        String[] projects = operation.split(",");

        for (String project: projects) {
            doOneProject(project, data);
        }

        return data;
    }

    /**
     * This method does one project opartion on the dataset.
     * @param project           - Project operation to do.
     * @param data              - Data to do the project operation on.
     * @throws ProjectException - Thrown when the projection cannot be performed.
     */
    private void doOneProject(String project, SequentialData data) throws ProjectException {
        if (data == null) {
            throw new ProjectException("there should be data specified.");
        }

        project = project.trim();
        Matcher matcher = pattern.matcher(project);

        if (!matcher.matches()) {
            throw new ProjectException("One of your columns is wrongly specified.");
        } else {
            String col = matcher.group(2);
            for (Record rec: data) {
                rec.remove(col);
            }
        }
    }

    @Override
    public ParseResult parseOperation(String operation, DataField data)
            throws AnalyzeException {
        throw new ProjectException("projection should be done on a dataset.");
    }

}

