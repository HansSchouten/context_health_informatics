package analyze.condition;

import java.util.Stack;

import model.Record;
import model.UnsupportedFormatException;
import model.datafield.DataField;
import model.datafield.DataFieldBoolean;
import model.datafield.DataFieldDouble;
import model.datafield.DataFieldInt;
import model.datafield.DataFieldString;

/**
 * This class is used to implement an condition in the scripting language.
 * idea from: https://github.com/jDTOBinder/jDTO-Binder/tree/master/jdto/src/main/java/org/jdto/util/expression
 * @author Matthijs
 *
 */
public class Condition {

    /**
     * This variable stores the expression to evaluate.
     */
    protected Expression expression;

    /**
     * This variable stores the position of in the string that has been read.
     */
    private int position;

    /**
     * Construct a condition that consists of a string.
     * @param condition     - String containing the condition.
     * @throws ConditionParseException - thrown when parsing goes wrong.
     */
    public Condition(String condition) throws ConditionParseException {
        if (condition != null && !condition.isEmpty()) {
            expression = parseStringToExpression(condition);
            System.out.println(expression.toString());
        } else {
            throw new ConditionParseException("Empty condition given");
        }
    }

    /**
     * This method should evaluate a condition with a given record.
     * @param record    - Record to evaluate with.
     * @return          - True if evaluation is right, false otherwise.
     * @throws ConditionParseException - thrown when parsing goes wrong.
     */
    public boolean evaluateWithRecord(Record record) throws ConditionParseException {
        try {
            DataField result = expression.evaluate(record);
            try {
                return result.getBooleanValue();
            } catch (UnsupportedFormatException e) {
                throw new ConditionParseException("Expression does not result in a boolean");
            }
        } catch (UnsupportedFormatException e) {
            return false;
        }
    }

    /**
     * This method parses a string to an expression.
     * @param condition     - Condition that needs to be parsed.
     * @return              - Expression that represents the condition.
     * @throws ConditionParseException - thrown when parsing goes wrong.
     */
    private Expression parseStringToExpression(String condition) throws ConditionParseException {
        position = 0;

        Stack<String> expressionStack = new Stack<String>();

        expressionStack.push("(");
        condition += ")";

        String token = null;
        StringBuilder pf = new StringBuilder();

        while (!expressionStack.isEmpty() && position < condition.length()) {

            token = readToken(token, condition);

            if ("(".equals(token)) {
              expressionStack.push(token);
              pf.append(" "); //a separation
              continue;
            }

            if (isOperator(token)) {
                parseOperator(token, expressionStack, pf);
                continue;
            }

            if (")".equals(token)) {
                pf.append(" "); //add a separator to the result.
                while (!"(".equals(expressionStack.peek())) {
                    String stackElement = expressionStack.pop();

                    if (isOperator(stackElement)) {
                        pf.append(stackElement);
                        pf.append(" ");
                    }
                }
                //remove the extra parenthesis
                expressionStack.pop();
                continue;
            }

            pf.append(token);
        }

        if (!expressionStack.isEmpty()) {
            throw new ConditionParseException(
                    "The expression does not have a valid nested format, check you parentheses!");
        }
        return parsePostfixExpr(pf.toString());
    }

    /**
     * This method parses an operator.
     * @param token     - Token to be parsed.
     * @param stack     - Stack to be used during parsing.
     * @param pf        - Stackbuilder, to be used for parsing.
     */
    protected void parseOperator(String token,
            Stack<String> stack, StringBuilder pf) {
        BinaryOperator operator =  BinaryOperator.getOperator(token);
        if (operator != null) {
            pf.append(" "); //add a seprarator char to the result.
            while (precedence(operator, stack.peek())) {
                pf.append(stack.pop());
                pf.append(" ");
            }
            stack.push(token);
        } else {
            UnaryOperator op = UnaryOperator.getOperator(token);
            pf.append(" ");
            while (precedence(op, stack.peek())) {
                pf.append(stack.pop());
                pf.append(" ");
            }
            stack.push(token);
        }
    }

    /**
     * This method checks whether an operator has higher priority than another.
     * @param op        - First operator.
     * @param other     - Second operator.
     * @return          - True if the other has an higher priority, false otherwise.
     */
    protected boolean precedence(Operator op, String other) {

        Operator compare = BinaryOperator.getOperator(other);

        if (compare == null) {
            compare = UnaryOperator.getOperator(other);
            if (compare == null) {
                return false;
            }
        }

        return op.getPriority() < compare.getPriority();
    }

    /**
     * This method reads a new token from the string.
     * @param previousToken     - Token that is used before.
     * @param expr        - Expression to evaluate.
     * @return                  - the next token.
     */
    private String readToken(String previousToken, String expr) {

        StringBuilder token = new StringBuilder();

        boolean minusAllowed = false;
        if (previousToken == null || previousToken.equals("(") || isOperator(previousToken)) {
            minusAllowed = true;
        }

        //convenience variable
        while (position < expr.length()) {
            //get and increment the character
            char character = expr.charAt(position);
            position++;

            if (character == ' ') {
                continue;
            }

            if (character == '('  || character == ')') {
                return Character.toString(character);
            }

            // Check for an operator
            String op = findsOperator(expr);
            if (op != null && !(minusAllowed && op.equals("-"))) {
                position += op.length() - 1;
                return op;
            }

            token.append(character);
            while (position < expr.length()) {
                character = expr.charAt(position++);
                if (character == ' ' || character == '(') {
                    break;
                }
                if (character == ')' || findsOperator(expr) != null) {
                    position--;
                    break;
                } else {
                    token.append(character);
                }
            }
            return token.toString();

        }
        return null;
    }

    /**
     * This method finds an operator in a string.
     * @param expr    - Expression to get the operator from.
     * @return              - 0 if no operator, length of the operator otherwise.
     */
    private String findsOperator(String expr) {
        int opPos = position - 1;
        String result = null;

        int maxOperator = BinaryOperator.maxOperatorLength();
        if (UnaryOperator.maxOperatorLength() > maxOperator) {
            maxOperator = UnaryOperator.maxOperatorLength();
        }

        for (int i = 1; i < maxOperator  && opPos +  i < expr.length(); i++) {
            if (isOperator(expr.substring(opPos, opPos + i))) {
                result = expr.substring(opPos, opPos + i);
            }
        }
        return result;
    }

    /**
     * This method returns whether the boolean is an operator.
     * @param previousToken     - Token to search in.
     * @return                  - True if the string is an operator.
     */
    private boolean isOperator(String previousToken) {

        return BinaryOperator.isSupportedOperator(previousToken) || UnaryOperator.isSupportedOperator(previousToken);
    }

    /**
     * This method parses the postfix string. In other words the desugared String.
     * @param postfix   - Desugared string of the script.
     * @return          - Expression that represents the script line.
     */
    private Expression parsePostfixExpr(String postfix) {

        String[] tokens = postfix.split(" ");

        Stack<Expression> termStack = new Stack<Expression>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("")) {
                continue;
            } else if (isOperator(token)) {
                Expression term;
                if (UnaryOperator.isSupportedOperator(token)) {
                    UnaryOperator op = UnaryOperator.getOperator(token);
                    Expression prev = termStack.pop();
                    term = new UnaryOpTerm(op, prev);
                } else {
                    BinaryOperator op = BinaryOperator.getOperator(token);
                    Expression right = termStack.pop(); //first is the right side
                    Expression left = termStack.pop(); //and then the left side
                    term = new BinaryOpTerm(op, left, right);
                }
                termStack.push(term);
            } else {
                termStack.push(parseToDataType(token));
            }
        }
        return termStack.pop();
    }

    /**
     * This method parses a string to the right data type.
     * @param token     - Token to be parsed
     * @return          - Expression containing the value of the token.
     */
    private Expression parseToDataType(String token) {
        DataField df;
        if (token.equals("false")) {
            df = new DataFieldBoolean(false);
        } else if (token.equals("true")) {
            df = new DataFieldBoolean(true);
        } else if (token.matches("-?\\d+")) {
            df = new DataFieldInt(Integer.parseInt(token));
        } else if (token.matches("-?\\d+(\\.\\d+)?")) {
            df = new DataFieldDouble(Double.valueOf(token));
        } else {
            df = new DataFieldString(token);
        }

        return new LiteralTerm(df);
    }
}
