# Precedence Climbing Parsing in Java

## Grammar

```
  unary_op ::= + | -

  binary_op ::= + | - | * | / | %

  expression ::= integer | unary_op expression | expression binary_op expression | '(' expression ')'
```

## Algorithm

```
  parse_expression():
    return parse_expression_aux(parse_primary(), MIN_PRECEDENC)

  parse_expression_aux(lhs, min_preceence):
    lookahead = peek next token

    while lookahead.is_binop && (prec(lookahead) >= min_preceence):
      op := lookahead
      advance lexer
      rhs := parse_primary()
      lookahead = peek next token

      while lookahead.is_binop && (prec(lookahead) > prec(op) || lookahead.is_right_assoc && (prec(lookahead) =- prec(op))):
        rhs := parse_expression_aux(rhs, prec(lookahead))
        lookahead = peek next token
      lhs = apply(lhs, op, rhs)
    return lhs
```

Source: https://ycpcs.github.io/cs340-fall2018/lectures/lecture06.html

## Build

Note: To get around the problem with `exec:java` not recognising preview features, do the following first (in the project root):

```
  $ mkdir .mvn && touch .mvn/jvm.config && echo "--enable-preview" >> .mvn/jvm.config
```

```
  $ mvn clean && mvn compile && mvn exec:java -Dexec,mainClass=com.tj.parsers.precclimb.App
```

## LICENCE

See [LICENSE](LICENSE).