package com.tj.parsers.precclimb.ast;

public record ExpUnary(UnaryOp op, Expression expr) implements Expression {}