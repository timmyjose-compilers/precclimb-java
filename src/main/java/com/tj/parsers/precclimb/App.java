package com.tj.parsers.precclimb;

import com.tj.parsers.precclimb.lexer.*;
import com.tj.parsers.precclimb.parser.*;
import java.util.Scanner;

public class App {
  private static final String PROMPT = ">> ";

  public static void main(String[] args) {
    System.out.println("Welcome to the Precedence Climbing Parsing Demo");

    try (final Scanner in = new Scanner(System.in)) {
      while (true) {
        System.out.print(PROMPT);
        System.out.flush();

        String input = in.nextLine().trim();
        final Parser parser = new Parser(new Lexer(input));
        final var ast = parser.parse();
        System.out.println(ast);
      }
    }
  }
}
