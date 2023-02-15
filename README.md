# Precendence Climbing Parsing in Java

## Grammar

```
  unary_op ::= + | -

  binary_op ::= + | - | * | / | %

  expression ::= integer | unary_op expression | expression binary_op expression | '(' expression ')'
```

## Build

```
  $ mvn clean && mvn compile && mvn exec:java -Dexec,mainClass=com.tj.parsers.precclimb.App
```

## LICENCE

See [LICENSE](LICENSE).