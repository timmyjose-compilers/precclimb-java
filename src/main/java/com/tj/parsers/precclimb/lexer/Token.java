package com.tj.parsers.precclimb.lexer;

public sealed interface Token permits TLparen, TRparen, TInteger, TPlus, TMinus,
    TMult, TDiv, TMod, TEnd {
  default boolean isRightAssociative() { return false; }

  default boolean isBinaryOp() { return false; }

  default int precedence() { return Precedence.MIN_PRECEDENCE; }
}
