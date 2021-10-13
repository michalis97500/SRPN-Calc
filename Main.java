import java.util.*;

class Main {
  public static Stack<Double> stack = new Stack<>();
  // random number identifier,stack max size
  public static int n = 0, stackMaxSize = 23;
  static RandomClass rng = new RandomClass();
  public static boolean wasAnythingUsed = false; //used to recreate a fault, first time d is used,show max negative int until something is popped to stack

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
      for (String oper : expr.split(",")) {
        double number1 = 0.0, number2 = 0.0;
        // Statements that can be executed with stack size < 1:
        // push number to stack, stack display, last answer,push random to stack
        if (oper.matches("-?\\d+(\\.\\d+)?") && oper.isEmpty() == false && oper.isBlank() == false) {
          // many conditions to avoid crashishng when the user inputs a whitespace first
          // i.e. " 1"
          pushDoubleToStack(stack, Double.parseDouble(oper));
        }
        if (oper.matches("d")) { // Display stack
          if(wasAnythingUsed == true){
            Object[] srpnArray = stack.toArray();
            for (int i = 0; i < srpnArray.length; i++) {
              System.out.format("%.0f\n", srpnArray[i]);
            }
          } else {
            System.out.println(Integer.MIN_VALUE); //replicated bug found in original SRPN
          }
        }
        if (oper.matches("=")) { // Show last answer
          double last = stack.pop();
          pushDoubleToStack(stack, last);
          System.out.format("%.0f\n", last);
        }
        if (oper.matches("r")) { // Random
          if (n > 21) {
            n = 0;
          }
          pushDoubleToStack(stack, rng.randomNumber(n));
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
                  System.out.println("Divide by 0.");
                  pushDoubleToStack(stack, number1);
                  pushDoubleToStack(stack, number2);
                  break;
                }
                pushDoubleToStack(stack, number1 / number2);
                break;
              case "^":
                number2 = stack.pop();
                number1 = stack.pop();
                if (number2 < 0){
                  System.out.println("Negative Power.");
                  pushDoubleToStack(stack, number1);
                  pushDoubleToStack(stack, number2);
                  break;
                }
                pushDoubleToStack(stack, Math.pow(number1, number2));
                break;
              case "%":
                number2 = stack.pop();
                number1 = stack.pop();
                pushDoubleToStack(stack, number1 % number2);
                break;
            }
          } else {
            System.out.println("Stack undeflow.");
          }
        }
      }
    } catch (EmptyStackException e) {
      System.out.println("Stack empty");
    }
    return 1;
  }
  public static void pushDoubleToStack(Stack<Double> stack, double number) {
    if (stack.size() >= stackMaxSize) {
      System.out.println("Stack overflow.");
    } else {
      //saturate number if too large
      stack.push((double) (int) number);
      wasAnythingUsed = true;
    }
  }

  public static void main(String[] args) {
    try {
      InputHandler input = new InputHandler(); // Start an input handler class
      while (true) {
        doMath(input.ReadLine());
      }
    } catch (Exception exception) {
      //System.out.println("Error on main : " + exception); //removed to match SRPN
    }
  }
}