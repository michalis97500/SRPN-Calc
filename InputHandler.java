import java.io.*;

public class InputHandler {
  public InputHandler() {
    // Let the user know we are ready for input
    System.out.println("You may now interact with the SRPN calculator");
  }

  public String ReadLine() {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String userin;
    try { // Note : Handle exception
      userin = input.readLine();
      return cleanString(userin);
    } catch (IOException e) {
      System.out.println("Error in reading line : " + e);
      userin = " ";
      return userin;
    }
  }

  // characters NOT to remove
  public static String notChars = "[^dr\\=\\%\\^\\*\\+\\-\\d/\\s\\\\]";

  /*
   * It's very hard to consider everything the user might try to input. This
   * method will attempt to strip the string from anything that the user might
   * enter but is not actually usefull/accepted by the SRPN calculator. It
   * basically formats the string in a way that our doMath method (found in Main)
   * works
   */
  private static String cleanString(String string) {
    if (string == null || string.length() == 0) { // NullorEmpty string, send plain whitespace handled by doMath
      return " ";
    } else {
      String noComments = string.replaceAll("# .*? #", ""); // comments must be enclosed with # and a space - bug replicated - fix with "#.*?#" regex
      String reversedEqualSignOperators = reverseEqualSignOperator(noComments);
      String removedCharacters = removeNotAllowed(reversedEqualSignOperators);
      if (removedCharacters == null || removedCharacters.length() == 0) {
        return " ";
      } else {
        return removedCharacters.trim();
      }
    }
  }

  public static String reverseEqualSignOperator(String stringToModify){
    try{
      //Method to add functionality that reverses any operator attached to equal sign i.e. ^= becomes =^
      if (stringToModify!=null){
        int indexOfReversal = 0;
        StringBuilder stringWithModifications = new StringBuilder();
        String [] differentCases = {"+=" , "-=", "*=" , "/=", "%=","^="};
        for (String item : differentCases){ //look for all different cases
          if(stringToModify.indexOf(item) != -1){ //case found
            indexOfReversal = stringToModify.indexOf(item);
            char[] stringInChars = stringToModify.toCharArray();
            int appendInt = 0;
            for(appendInt=0;appendInt<indexOfReversal;appendInt++){
              stringWithModifications.append(stringInChars[appendInt]);
            }
            stringWithModifications.append(stringInChars[appendInt+1]);
            stringWithModifications.append(stringInChars[appendInt]);
            for(appendInt=appendInt+2;appendInt<stringInChars.length;appendInt++){
              stringWithModifications.append(stringInChars[appendInt]);
            }
            stringToModify = stringWithModifications.toString();
          }
        }
        return stringToModify;
      } else {
        return " ";
      } 
    } catch (Exception e){
      return " ";
    }
  }

  private static String removeNotAllowed(String stringToClean){
    StringBuilder cleanString = new StringBuilder();
    for (String character : stringToClean.split("")){
      switch(character) {
        case " ":
          cleanString.append(",");
          break;
        case "+":
          cleanString.append(",+,");
          break;
        case "-":
          cleanString.append(",-");
          break;
        case "/":
          cleanString.append(",/,");
          break;
        case "%":
          cleanString.append(",%,");
          break;
        case "*":
          cleanString.append(",*,");
          break;
        case "=":
          cleanString.append(",=");
          break;
        case "d":
          cleanString.append(",d,");
          break;
        case "r":
          cleanString.append(",r,");
          break;
        case "^":
          cleanString.append(",^,");
          break;
        default:
          if (character.matches("[0-9]*")){
            cleanString.append(character);
          } else {
            System.out.println("Unrecognised operator or operand \"" + character + "\".");
          }
      }
    }
    return cleanString.toString();
  }
}