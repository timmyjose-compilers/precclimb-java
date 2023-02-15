package com.tj.parsers.precclimb.lexer;

public final record TPlus() implements Token {
  @Override
  public boolean isBinaryOp() {
    return true;
  }

  @Override
  public int precedence() {
    return Precedence.PLUS_MINUS;
  }
}