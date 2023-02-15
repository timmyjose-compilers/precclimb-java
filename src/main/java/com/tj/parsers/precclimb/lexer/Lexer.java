package com.tj.parsers.precclimb.lexer;

import java.util.function.Predicate;

public class Lexer {
  private String source;
  private int currPos;
  private int size;

  public Lexer(final String source) {
    this.source = source;
    this.currPos = 0;
    this.size = source.length();
  }

  private void forward() { this.currPos++; }
  private void forwardN(int n) { this.currPos += n; }
  private void backward() { this.currPos--; }
  private void backwardN(int n) { this.currPos -= n; }

  record Pair<T, U>(T first, U second) {}

  private Pair<Integer, String> extractPred(Predicate<Character> pred) {
    int startPos = this.currPos;
    int runningPos = this.currPos;

    while (runningPos < this.size &&
           pred.test(this.source.charAt(runningPos))) {
      runningPos++;
    }

    this.currPos = runningPos;
    return new Pair(runningPos - startPos,
                    this.source.substring(startPos, runningPos));
  }

  private Pair<Integer, Integer> extractInt() {
    var intPair = extractPred(Character::isDigit);
    return new Pair(intPair.first, Integer.parseInt(intPair.second));
  }

  private Token peekChar(char c) {
    switch (c) {
    case ' ':
    case '\t':
    case '\n': {
      forward();
      return peek();
    }

    case '0':
    case '1':
    case '2':
    case '3':
    case '4':
    case '5':
    case '6':
    case '7':
    case '8':
    case '9': {
      var intPair = extractInt();
      var i32 = new TInteger(intPair.second);
      backwardN(intPair.first);
      return i32;
    }

    case '(':
      return new TLparen();

    case ')':
      return new TRparen();

    case '+':
      return new TPlus();

    case '-':
      return new TMinus();

    case '*':
      return new TMult();

    case '/':
      return new TDiv();

    case '%':
      return new TMod();

    default:
      throw new LexerException(String.format("Invalid character: %c", c));
    }
  }

  private Token lexChar(char c) {
    switch (c) {
    case ' ':
    case '\t':
    case '\n': {
      forward();
      return lex();
    }

    case '0':
    case '1':
    case '2':
    case '3':
    case '4':
    case '5':
    case '6':
    case '7':
    case '8':
    case '9': {
      var intPair = extractInt();
      return new TInteger(intPair.second);
    }

    case '(': {
      forward();
      return new TLparen();
    }

    case ')': {
      forward();
      return new TRparen();
    }

    case '+': {
      forward();
      return new TPlus();
    }

    case '-': {
      forward();
      return new TMinus();
    }

    case '*': {
      forward();
      return new TMult();
    }

    case '/': {
      forward();
      return new TDiv();
    }

    case '%': {
      forward();
      return new TMod();
    }

    default:
      throw new LexerException(String.format("Invalid character: %c", c));
    }
  }

  public Token peek() {
    if (this.currPos < this.size) {
      return peekChar(this.source.charAt(this.currPos));
    }
    return new TEnd();
  }

  public Token lex() {
    if (this.currPos < this.size) {
      return lexChar(this.source.charAt(this.currPos));
    }
    return new TEnd();
  }
}
