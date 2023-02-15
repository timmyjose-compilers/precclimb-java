package com.tj.parsers.precclimb.ast;

public record ExpBinary(Expression expr1, BinaryOp op, Expression expr2)
    implements Expression {}