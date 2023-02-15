package com.tj.parsers.precclimb.lexer;

public final record TMod() implements Token {
  @Override
  public boolean isBinaryOp() {
    return true;
  }

  @Override
  public int precedence() {
    return Precedence.MOD;
  }
}