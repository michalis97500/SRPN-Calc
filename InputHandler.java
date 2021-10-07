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
      String noComments = string.replaceAll("#.*?#", ""); // comments must be enclosed with #
      String removeNotAllowed = noComments.replaceAll(notChars, "");// remove everything that we dont care about
      String removeWhitespaces = removeNotAllowed.replaceAll("\\s+", " ");// remove extra whitespaces
      if (removeWhitespaces == null || removeWhitespaces.length() == 0) {
        return " ";
      } else {
        return addWhitetoOperator(removeWhitespaces.trim());
      }
    }
  }

  // This method is called to place a whitespace between user input to make
  // sure everything reaches the math method in the same format
  // 1+1 == 1 + 1
  private static String addWhitetoOperator(String string) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String character : string.split("")) {
      switch (character) {
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