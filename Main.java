import java.util.*;
import java.io.*;

class Main {
  public static void main(String[] args) {
    try {
      populateArray(); //fill our "random" array with doubles
      InputHandler input = new InputHandler(); //Start an input handler class 
      //Begin recieveing commands
      while (true) {
        doMath(input.ReadLine());
      }
    } catch (Exception exception) {
      System.out.println("Error on main : " + exception);
    }
  }
  //Stack to store operands and operators
  public static Stack <Double> stack = new Stack < > ();
  //random number identifier
  public static int n = 0;
  //array to hold "random" doubles
  public static double[] randomArray = new double[60];

  /*I have found out that "r" in the SRPN given seems to be 
  pseudorandom. It appears there is an array of 60 doubles that are used for
  the random function.
  */
  public static double randomNumber(int _int) {
    return randomArray[_int];
  }

  public static void populateArray() {
    //this method will populate an array with "random" numbers that the user might need
    int i = 0;
    try {
      //Begin by checking that our file is compatible with the array that we have
      File file = new File("random.txt");
      Scanner lineCount = new Scanner(file);
      //simple counter
      while (lineCount.hasNextDouble()) {
        i++;
        double dummy = lineCount.nextDouble();
      }
      lineCount.close();
      if (i != 60) {
        System.out.println("File given is not compatible. Lines must be 60");
      }
      if (i == 60) {
        //file looks to be okay, write the doubles in the array
        Scanner line = new Scanner(file);
        int y = 0;
        while (line.hasNextDouble()) {
          Double data = line.nextDouble();
          randomArray[y] = data;
          y++;
        }
        line.close();
      }
    } catch (Exception e) {
      System.out.println("Error on array population : " + e);
    }
  }

  /*Since all inputs are split by either a space or a "\n", the input of this function expects
  that the string is formatted in that way. In the following method the string is broken 
  split by whitespaces to a variable "oper" (for both operand and operator) using a loop. Within the loop the method will check what exactly is oper and determine what to do with it. 
  */
  public static int doMath(String expr) {
    //2ndary defense mechanism, if somehow a null, empty or plain whitespace string
    //manages to get through, this is cancel the method
    if (expr == "" || expr == " ") {
      return 0;
    }
    try {
      //Split the string into the different operators/operands, loop for all
      for (String oper: expr.split("\\s")){
        double secondOperand = 0.0;
        double firstOperand = 0.0;
        //Statements that can be executed with stack size < 1:
        //push number to stack, stack display, last answer,push random to stack
        if (oper.matches("[0-9]*") && oper.isEmpty() == false && oper.isBlank() == false ){
            //many conditions to avoid crashishng when the user inputs a whitespace first
            //i.e. " 1"
            stack.push(Double.parseDouble(oper));    
        }
        if (oper.matches("d")) {
          Object[] srpnArray = stack.toArray();
          for (int i = 0; i < srpnArray.length; i++) {
            System.out.format("%.0f\n",srpnArray[i]);
          }
        }
        if (oper.matches("=")) {
          double last = stack.pop();
          stack.push(last);
          System.out.println("Last Answer: " + last);
        }
        if (oper.matches("r")) {
          if (n > 59) {
            n = 0;
          }
          stack.push(randomNumber(n));
          n++;
        }
        //Check if the oper is an operator. While this is not strictly necessary,
        //it is used to display stack underflow only when an operator is used
        if(oper.matches("[\\+\\-\\*\\/\\^\\%]")){
          //if this is an operator, we require at least 2 operands so stack size must be > 1
          if (stack.size() > 1) {
            switch (oper) {
            case "+":
              secondOperand = stack.pop();
              firstOperand = stack.pop();
              stack.push(firstOperand + secondOperand);
              break;
            case "-":
              secondOperand = stack.pop();
              firstOperand = stack.pop();
              stack.push(firstOperand - secondOperand);
              break;
            case "*":
              secondOperand = stack.pop();
              firstOperand = stack.pop();
              stack.push(firstOperand * secondOperand);
              break;
            case "/":
              secondOperand = stack.pop();
              firstOperand = stack.pop();
              if (secondOperand == 0.0) {
                System.out.println("Cannot divide by zero!");
                break;
              }
              stack.push(firstOperand / secondOperand);
              break;
            case "^":
              secondOperand = stack.pop();
              firstOperand = stack.pop();
              stack.push(Math.pow(firstOperand, secondOperand));
              break;
            case "%":
              secondOperand = stack.pop();
              firstOperand = stack.pop();
              stack.push(firstOperand % secondOperand);
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
}