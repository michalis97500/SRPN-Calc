import java.io.*;

//Begine by setting up this class to handle the user input
public class InputHandler{
  public InputHandler(){
    //Let the user know we are ready for input
    System.out.println("Started successfully");
  }

  //By using the BufferedReader we will capture the input of the user
  //as a string. 
  public String ReadLine(){
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String userin;
    //Use try/cath as BufferedReader could throw IOException
    try {
      userin = input.readLine();
      //The user input is now found at "userin" string. We will parse this string
      //to cleanString remove anything that could mess with our program
      return cleanString(userin);
    } 
    catch (IOException e) {
      System.out.println("Error in reading line : " +e);
      userin = " " ;
      return userin;
    }
  }

  //characters NOT to remove
  public static String notChars = "[^dr\\=\\%\\^\\*\\+\\-\\d/\\s\\\\]";

  /* It's very hard to consider everything the user might try to input. This method
  will attempt to strip the string from anything that the user might enter but is not
  actually usefull/accepted by the SRPN calculator. It basically formats the string in a
  way that our doMath method (found in Main) works
  */
  private static String cleanString(String string) {
    //user input of nothing or "". return a plain whitespace that doMath can handle by ignoring it
    if(string == null || string.length()==0){
      return " ";
    }
    //User has entered something
    else{
      //remove the comments first
      String noComments = string.replaceAll("#.*?#", "");
      System.out.println(noComments);
      //remove everything that we dont care about
      String removeNotAllowed = noComments.replaceAll(notChars, "");
      //remove extra whitespaces
      String removeWhitespaces = removeNotAllowed.replaceAll("\\s+", " ");
      if(removeWhitespaces == null || removeWhitespaces.length()==0){
        return " ";
      }
      else{
        //return removeWhitespaces;
        return addWhitetoOperator(removeWhitespaces.trim());
      }
    }
  }

  //This method is called to place a whitespace between user input to make
  //sure everything reaches the math method in the same format
  //1+1 == 1 + 1 
  private static String addWhitetoOperator(String string){
    StringBuilder stringBuilder = new StringBuilder();
    for(String character : string.split("")){
      switch(character){
        case "+":
          stringBuilder.append(" + ");
          break;
        case "-":
          stringBuilder.append(" -");
          break;
        case "/":
          stringBuilder.append(" / ");
          break;
        case "%":
          stringBuilder.append(" % ");
          break;
        case "*":
          stringBuilder.append(" * ");
          break;
        case "=":
          stringBuilder.append(" = ");
          break;
        case "d":
          stringBuilder.append(" d ");
          break;
        case "r":
          stringBuilder.append(" r ");
          break;
        case "^":
          stringBuilder.append(" ^ ");
          break;
        default:
          stringBuilder.append(character);
          break;
      }
    }
    return stringBuilder.toString();
  }
}