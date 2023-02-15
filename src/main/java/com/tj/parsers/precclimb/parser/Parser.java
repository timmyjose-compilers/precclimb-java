package com.tj.parsers.precclimb.parser;

import com.tj.parsers.precclimb.ast.*;
import com.tj.parsers.precclimb.lexer.*;

public class Parser {
  private Lexer lexer;

  public Parser(final Lexer lexer) { this.lexer = lexer; }

  private BinaryOp getBinaryOp(final Token token) {
    return switch (token) {
      case TPlus plus-> new Plus();
      case TMinus minus -> new Minus();
      case TMult mult-> new Mult();
      case TDiv div -> new Div();
      case TMod mod -> new Mod();
      default -> throw new UnsupportedOperationException(token.toString());
    };
  }

  private UnaryOp getUnaryOp(final Token token) {
    return switch (token) {
      case TPlus uplus -> new UPlus();
      case TMinus uminus -> new UMinus();
      default -> throw new UnsupportedOperationException(token.toString());
    };
  }

  private Expression parsePrimary() {
    var token = this.lexer.lex();

    return switch (token) {
      case TInteger i32 -> 
        new ExpInteger(i32.ival());

      case TLparen tlp -> {
        var expr = parseExpression();
        if (!(this.lexer.peek() instanceof TRparen)) {
          throw new ParserException("missing parenethesis while parsing expression");
        }
        this.lexer.lex();
        yield expr;
      }

      case TPlus tplus -> {
        var expr =
            parseExpressionAux(parsePrimary(), Precedence.MAX_PRECEDENCE);
        yield new ExpUnary(getUnaryOp(token), expr);
      }

      case TMinus tminus -> {
        var expr =
            parseExpressionAux(parsePrimary(), Precedence.MAX_PRECEDENCE);
        yield new ExpUnary(getUnaryOp(token), expr);
      }

        default -> throw new UnsupportedOperationException(token.toString());
      };
  }

  private Expression parseExpressionAux(Expression lhs, int minPrecedence) {
    var lookAhead = this.lexer.peek();

    while (lookAhead.isBinaryOp() &&
           (lookAhead.precedence() >= minPrecedence)) {
      var op = lookAhead;
      lexer.lex();
      var rhs = parsePrimary();
      lookAhead = this.lexer.peek();

      while ((lookAhead.isBinaryOp() &&
              lookAhead.precedence() > op.precedence()) ||
             (lookAhead.isRightAssociative() &&
              (lookAhead.precedence() == op.precedence()))) {
        rhs = parseExpressionAux(rhs, lookAhead.precedence());
        lookAhead = lexer.peek();
      }

      lhs = new ExpBinary(lhs, getBinaryOp(op), rhs);
    }

    return lhs;
  }

  private Expression parseExpression() {
    return parseExpressionAux(parsePrimary(), Precedence.MIN_PRECEDENCE);
  }

  public Expression parse() { return parseExpression(); }
}
