import java.util.*;
import java.io.*;

class Main {
  public static Stack<Double> stack = new Stack<>();
  // random number identifier,stack max size
  public static int n = 0, stackMaxSize = 24;
  // array to hold "random" doubles
  public static double[] randomArray = new double[60];

  /*
   * I have found out that "r" in the SRPN given seems to be pseudorandom. It
   * appears there is an array of 60 doubles that are used for the random
   * function.
   */
  public static double randomNumber(int _int) {
    return randomArray[_int];
  }

  public static void populateArray() { // this method will populate an array with "random" numbers
    int howManyLines = 0;
    try {
      // Run a counter on the file to check the lines = array, if so enter the values
      // on each line into array
      File file = new File("random.txt");
      Scanner lineCount = new Scanner(file);
      while (lineCount.hasNextDouble()) {
        howManyLines++;
        double iDoNothing = lineCount.nextDouble(); // without this running on repl.it causes endless loop
      }
      lineCount.close();
      if (howManyLines != 60) {
        System.out.println("File given is not compatible. Lines must be 60");
      }
      if (howManyLines == 60) {
        Scanner line = new Scanner(file);
        int y = 0;
        while (line.hasNextDouble()) {
          Double valueInLine = line.nextDouble();
          randomArray[y] = valueInLine;
          y++;
        }
        line.close();
      }
    } catch (Exception e) {
      System.out.println("Error on array population : " + e);
    }
  }

  // DELETED METHOD -> : This method will determine if the result is saturated and
  // if it is, will yield min/max int limit
  // REASON : casting a double to an int and back again saturates it

  /*
   * Since all inputs are split by either a space or a "\n", the input of this
   * function expects that the string is formatted in that way. In the following
   * method the string is split by whitespaces to a variable "oper" (for both
   * operand and operator) using a loop. Within the loop the method will check
   * what exactly is oper and determine what to do with it.
   */
  public static int doMath(String expr) {
    if (expr == "" || expr == " ") {
      return 0;
    } // Defense mechanism for null/empty input
    try {
      // Split the string into the different operators/operands, loop for all
      for (String oper : expr.split("\\s")) {
        double number1 = 0.0, number2 = 0.0;
        // Statements that can be executed with stack size < 1:
        // push number to stack, stack display, last answer,push random to stack
        if (oper.matches("-?\\d+(\\.\\d+)?") && oper.isEmpty() == false && oper.isBlank() == false) {
          // many conditions to avoid crashishng when the user inputs a whitespace first
          // i.e. " 1"
          pushDoubleToStack(stack, Double.parseDouble(oper));
        }
        if (oper.matches("d")) { // Display stack
          Object[] srpnArray = stack.toArray();
          for (int i = 0; i < srpnArray.length; i++) {
            System.out.format("%.0f\n", srpnArray[i]);
          }
        }
        if (oper.matches("=")) { // Show last answer
          double last = stack.pop();
          pushDoubleToStack(stack, last);
          System.out.println("Answer: " + last);
        }
        if (oper.matches("r")) { // Random
          if (n > 59) {
            n = 0;
          }
          pushDoubleToStack(stack, randomNumber(n));
          n++;
        }
        // Check if the oper is an operator. While this is not strictly necessary,
        // it is used to display stack underflow only when an operator is used
        if (oper.matches("[\\+\\-\\*\\/\\^\\%]")) {
          if (stack.size() > 1) { // Check enough operands exist
            switch (oper) {
              case "+":
                number2 = stack.pop();
                number1 = stack.pop();
                pushDoubleToStack(stack, number1 + number2);
                break;
              case "-":
                number2 = stack.pop();
                number1 = stack.pop();
                pushDoubleToStack(stack, number1 - number2);
                break;
              case "*":
                number2 = stack.pop();
                number1 = stack.pop();
                pushDoubleToStack(stack, number1 * number2);
                break;
              case "/":
                number2 = stack.pop();
                number1 = stack.pop();
                if (number2 == 0.0) {
                  System.out.println("Cannot divide by zero!");
                  pushDoubleToStack(stack, number1);
                  pushDoubleToStack(stack, number2);
                  break;
                }
                pushDoubleToStack(stack, number1 / number2);
                break;
              case "^":
                number2 = stack.pop();
                number1 = stack.pop();
                pushDoubleToStack(stack, Math.pow(number1, number2));
                break;
              case "%":
                number2 = stack.pop();
                number1 = stack.pop();
                pushDoubleToStack(stack, number1 % number2);
                break;
            }
          } else {
            System.out.println("Stack undeflow");
          }
        }
      }
    } catch (EmptyStackException e) {
      System.out.println("Stack does not have a valid size for that operation");
    }
    return 1;
  }

  public static void pushDoubleToStack(Stack<Double> stack, double number) {
    if (stack.size() >= stackMaxSize) {
      System.out.println("Stack Overflow");
    } else {
      // saturate number if too large
      stack.push((double) (int) number);
    }
  }

  public static void main(String[] args) {
    try {
      populateArray(); // fill our "random" array
      InputHandler input = new InputHandler(); // Start an input handler class
      while (true) {
        doMath(input.ReadLine());
      }
    } catch (Exception exception) {
      System.out.println("Error on main : " + exception);
    }
  }
}