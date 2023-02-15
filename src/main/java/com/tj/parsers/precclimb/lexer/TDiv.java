package com.tj.parsers.precclimb.lexer;

public final record TDiv() implements Token {
  @Override
  public boolean isBinaryOp() {
    return true;
  }

  @Override
  public int precedence() {
    return Precedence.MULT_DIV;
  }
}