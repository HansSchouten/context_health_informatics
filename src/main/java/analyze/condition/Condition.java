package analyze.condition;

import java.util.HashMap;
import java.util.Stack;

import model.DataField;
import model.Record;

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
     * This variable stores the values of the columns that are evaluated.
     */
    protected HashMap<String, DataField> columnValues;
    
    /**
     * Construct a condition that consists of a string.
     * @param condition     - String containing the condition.
     * @throws ConditionParseException 
     */
    public Condition(String condition) throws ConditionParseException {
        expression = parseStringToExpression(condition);
        columnValues = new HashMap<String, DataField>(); // Possibly not with copying
    }
    
    /**
     * This method should evaluate a condition with a given record.
     * @param record    - Record to evaluate with.
     * @return          - True if evaluation is right, false otherwise.
     */
    public boolean evaluateWithRecord(Record record) {
        columnValues.clear();
        
        //TODO:: implement that it can be evaluated with every record.
        return false;
    }

    /**
     * This method parses a string to an expression.
     * @param condition     - Condition that needs to be parsed.
     * @return              - Expression that represents the condition.
     * @throws ConditionParseException 
     */
    private Expression parseStringToExpression(String condition) throws ConditionParseException {
        int stringPosition = 0;
        
        Stack<String> expressionStack = new Stack<String>();

        expressionStack.push("(");
        condition += ")";
        
        String token = null;
        StringBuilder pf = new StringBuilder();
        StringBuilder postFix = new StringBuilder();
        
        while(expressionStack.isEmpty() && stringPosition < condition.length()) {
            
            token = readToken(token, condition, stringPosition);
            
        }
        
        if (!expressionStack.isEmpty()) {
            throw new ConditionParseException("The expression does not have a valid nested format, check you parentheses!"); 
        }
        return null;
        
    }
    
//    /**
//     * Parse the expression into something easily evaluable.
//     * @param expression
//     * @return 
//     */
//    private synchronized ExpressionTerm parseExpression(String expression) {
//        
//        StringBuilder postFix = new StringBuilder();
//        
//        /**
//         * Go through the expression.
//         */
//        while(!precedenceStack.isEmpty() && position < expression.length()) {
//            
//            //use the token from previous iteration
//            token = readToken(token, expression);
//            
//            //if is a left parentheses
//            if ("(".equals(token)) {
//                precedenceStack.push(token);
//                postFix.append(" "); //a separation
//                continue;
//            }
//            
//            //check if it is an operator
//            Operator operator =  Operator.getOperaorByString(token);
//            if (operator != null) {
//                postFix.append(" "); //add a seprarator char to the result.
//                while(operator.precedence(precedenceStack.peek())) {
//                    postFix.append(precedenceStack.pop());
//                    postFix.append(" ");
//                }
//                precedenceStack.push(token);
//                continue;
//            }
//            
//            //check if it is a right parenthesis
//            if (")".equals(token)) {
//                postFix.append(" "); //add a separator to the result.
//                while (!"(".equals(precedenceStack.peek())) {
//                    String stackElement = precedenceStack.pop();
//                    
//                    if (isOperator(stackElement)) {
//                        postFix.append(stackElement);
//                        postFix.append(" ");
//                    } 
//                }
//                //remove the extra parenthesis
//                precedenceStack.pop();
//                continue;
//            }
//            
//            //if everything else fails, just add the token to the postfix expr
//            postFix.append(token);
//            //and we're done with the loop here
//        }
//        
//        //at this point we need to convert the postfix expression into terms.
//        if (!precedenceStack.isEmpty()) {
//           throw new IllegalArgumentException("Could not parse expression!"); 
//        }
//        
//        return parsePostfixExpr(postFix.toString());
//    }

    /**
     * This method reads a new token from the string.
     * @param previousToken     - Token that is used before.
     * @param expression        - Expression to evaluate.
     * @param position          - The position in the expression
     * @return                  - the next token.
     */
    private String readToken(String previousToken, String expression, int position) {
        
        boolean operatorAllowed = true;
        
        //logic for negative numbers
//        if (previousToken == null || previousToken.equals("(") || isOperator(previousToken)) {
//            operatorAllowed = false;
//        }
        
        StringBuilder tokenBuilder = null;
        
        //convenience variable
        while(position < expression.length()) {
            //get and increment the character
            char character = expression.charAt(position++);
            
            //if is space, then ignore it
            if (character == ' ') {
                continue;
            }
            
//            //if is parentheses
//            if (character == '('  || character == ')') {
//                return Character.toString(character);
//            }
//            
//            if (isOperator(character) && operatorAllowed) {
//                return Character.toString(character);
//            }
//            
//            if (isOperator(character) && character != '-') {
//                throw new IllegalArgumentException("Operator "+character+" not allowed here");
//            }
            
            //if none of the previous, then read a number or variable.
//            tokenBuilder = getOrCreateBuilder(tokenBuilder);
            tokenBuilder.append(character);
            while(position < expression.length()) {
                character = expression.charAt(position++);
                if (character == ' ') {
                    //this character is not important
                    break;
                }
//                if (character == ')' || isOperator(character)) {
//                    position--;
//                    break;
//                } else {
//                    tokenBuilder.append(character);
//                }
            }
            return tokenBuilder.toString();
        }
        return null;
    }
    
//    private boolean isOperator(String previousToken) {
//        Operator.getOperaorByCharacter(character) != null;
//    }
//    
//    /**
//     * Get or create a string builder.
//     * @param builder
//     * @return 
//     */
//    private StringBuilder getOrCreateBuilder(StringBuilder builder) {
//        if (builder == null) {
//            return new StringBuilder();
//        } else {
//            return builder;
//        }
//    }
//
//    private ExpressionTerm parsePostfixExpr(String postfix) {
//        //split the string
//        String[] tokens = StringUtils.split(postfix, ' ');
//        
//        LinkedList<ExpressionTerm> termStack = new LinkedList<ExpressionTerm>();
//        
//        for (String token : tokens) {
//            //if is not an operator, then read a literal or variable
//            if (!isOperator(token)) {
//                termStack.push(buildTerm(token));
//            } else {
//                //try to build a compound term and push it.
//                Operator op = Operator.getOperaorByString(token);
//                ExpressionTerm right = termStack.pop(); //first is the right side
//                ExpressionTerm left = termStack.pop(); //and then the left side
//                
//                ExpressionTerm term = new CompoundTerm(op, left, right);
//                termStack.push(term);
//            }
//        }
//        
//        //at this point the stack should have just one element.
//        
//        return termStack.pop();
//    }
//
//    private ExpressionTerm buildTerm(String token) {
//        if (token.matches("[A-Za-z]{1}+[.[A-Za-z]]*")) {
//            return new VariableTerm(token, varValues, resolver);
//        } else {
//            //try to parse or let the user blow with a number format exception
//            return new LiteralTerm(Double.parseDouble(token));
//        }
//    }
//
//    /**
//     * Set the value of a variable by name.
//     * @param var the name of the variable.
//     * @param value the value of the variable.
//     */
//    public void setVariable(String var, Number value) {
//        varValues.put(var, value);
//    }
//    
//    /**
//     * Get all of the variable names for this expresion.
//     * @return the list of variable names found on the expression.
//     */
//    public Set<String> getVariableNames() {
//        return varValues.keySet();
//    }
}
