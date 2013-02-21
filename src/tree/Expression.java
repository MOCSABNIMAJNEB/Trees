package tree;

import java.util.ArrayList;
import java.util.Arrays.*;
import java.util.*;
import static java.lang.Integer.*;

class Expression {
    Tree<String> expression;

    public Expression(String expression) {
	this.expression = Tree.parse(expression);

	if (!(treeLegal(this.expression)))
	    throw new IllegalArgumentException();
    }


    /**
     * Determines whether or not a tree is a legal expression
     * @param tree to evaluate
     * @return boolean whether or not equation is legal
     */
    public static boolean treeLegal(Tree<String> tree) {
	Iterator<Tree<String>> thisIt = tree.iterator();
	
	if (!(isLegal(tree.getValue()))) {
	    return false;
	} else {
	    while (thisIt.hasNext())
		return treeLegal(thisIt.next());
	}

	return true;
    }

    /**
     * Determines whether or not value is legal
     * @param str String representation of value of tree at a particular node
     * @return boolean whether or not that value is legal
     */
    public static boolean isLegal(String str) {
	char[] legalChars = {'+', '-', '*', '/'};
	int i;

	for (i=0; i<4; i++)
	    if (str.charAt(0)==legalChars[i])
		return true;
	
	try {
	    parseInt(str);
	} catch (NumberFormatException e) {
	    return false;
	}

	return true;
    };
		    
    /**
     * Evaluates an expression by calling the recursive function deepEvaluate
     */
    public int evaluate() {
	return deepEvaluate(expression);

    };
    /**
     * Recursively evaluates a function by sorting it either as a 
     * '*', '/', '+', or '-' operation or terminal and then returns
     * either the terminal or the
     */
    public int deepEvaluate(Tree<String> expression) {
        if (expression.getValue().charAt(0) == '*') {
	    Iterator<Tree<String>> prodIt = expression.iterator();
	    int prod = 1;
	    while (prodIt.hasNext()) {
		prod = prod * deepEvaluate(prodIt.next()) ;
	    }
	    return prod;
	}
	    
        if (expression.getValue().charAt(0) == '+') {
	    Iterator<Tree<String>> sumIt = expression.iterator();
	    int sum = 0;
	    while (sumIt.hasNext()) {
		sum = sum + deepEvaluate(sumIt.next()) ;
	    }
	    return sum;
	}

	else if (expression.getValue().charAt(0) == '-')
	    return deepEvaluate(expression.getChild(0)) - deepEvaluate(expression.getChild(1));
	else if (expression.getValue().charAt(0) == '/')
	    return deepEvaluate(expression.getChild(0)) / deepEvaluate(expression.getChild(1));
	
	return parseInt(expression.getValue());
    };

    @Override
    public String toString() { 

	return deepToString(expression);

    }

    public String deepToString(Tree<String> expression) {
	String equation = "";

	if (expression.getValue().charAt(0) == '*' || expression.getValue().charAt(0) == '/' || expression.getValue().charAt(0) == '+' || expression.getValue().charAt(0) == '-')
	    equation += "(" + deepToString(expression.getChild(0)) +")" + expression.getValue() + "(" + deepToString(expression.getChild(1)) + ")";
	else
	    equation += expression.getValue();
	
	return equation;
    }

    public void print() {
	expression.print();
    }


}
