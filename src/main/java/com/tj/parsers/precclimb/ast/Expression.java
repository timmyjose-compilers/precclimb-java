package com.tj.parsers.precclimb.ast;

public sealed interface Expression permits ExpInteger, ExpUnary, ExpBinary {}
