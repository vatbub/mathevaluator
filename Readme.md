# Math evaluator
This is just a small side project of mine which intends to parse math expressions (saved as a `String`)and calculate the result.

## What it does
The lib can parse expressions which contain
- Additions
- Subtractions
- Multiplications
- Divisions
- Parenthesis (even nested parenthesis)
- Powers
- Functions (`sin`, `cos`, `tan`, `cot`, `sqrt` and `factorial`)

The lib takes string which follows the above rules, parses it and computes the result.

 ## How to use it
 Since I programmed the engine during one evening in front of the TV, I figured that it wouldn't be worth the time to upload the lib to maven central. 
 Hence, you need to clone the repo and run `mvn install` to get it working.
 Then, you can either use the cli by running `java -jar cli/target/math-evaluator.cli-1.0-SNAPSHOT-jar-with-dependencies.jar` 
 or use the engine in your own project:
 
 ```xml
<dependencies>
	<dependency>
		<groupId>com.github.vatbub</groupId>
     <artifactId>mathevaluator</artifactId>
     <version>2.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

After compiling, you can use the lib like so:

```java
MathExpression expression = new MathExpression("(5+10)*4/5*(2+2)");
double result=expression.evaluate().getValue();
```

```kotlin
val expression = "(5+10)*4/5*(2+2)".toMathExpression()
val result: Double = expression.evaluate().value
```

In-depth examples can be found in the CLI-module.

A compiled version of the CLI can be found on the releases page.

## Constants

### Built-in constants

- [Pi](https://en.wikipedia.org/wiki/Pi)
- [e](https://en.wikipedia.org/wiki/E_%28mathematical_constant%29)

You may add your own constants by extending the parser (see below)

### Variables

You may define variables at runtime by inputting the following into the parser:

```
y=2*5 // evaluates to 10
x=2*y // evaluates to 20
y=50  // redefines the value of y, x will change accordingly
x     // evaluates to 100 
```

As shown above, the variables store their relations to each other. Therefore, if `x` depends on `y`
as in the example above and the value of `y` is changed, `x` updates accordingly.

## Implicit multiplication
Yes, the parser understands implicit multiplication! It does so when one of the following conditions is met:

| Previous literal \ current literal | MathNumber | Operator | SingleArgumentOperator | Constant | MathFunction | MathExpression |
|------------------------------------|------------|----------|------------------------|----------|--------------|----------------|
| MathNumber                         | No         | No       | No                     | Yes      | Yes          | Yes            |
| Operator                           | No         | No       | No                     | No       | No           | No             |
| SingleArgumentOperator             | No         | No       | No                     | No       | No           | No             |
| Constant                           | No         | No       | No                     | Yes      | Yes          | Yes            |
| MathFunction                       | No         | No       | No                     | Yes      | Yes          | Yes            |
| MathExpression                     | No         | No       | No                     | Yes      | Yes          | Yes            |

If a literal of the type specified in the first column is followed by the type specified in the first row and the corresponding cell in the above table indicates 'Yes', implicit multiplication is assumed.

When extending the parser (see below), you may override `supportsImplicitMultiplication(MathLiteral previousLiteral)` to tell the parser when your literal supports implicit multiplication and when not.

## Extending the parser
The parser is built in a modular way which allows it to be extended. If you wish to implement your own operators, constants or functions, just do the following:

1. Create a class which extends `MathFunction`, `Constant` or `Operator` (*)
2. Implement all the abstract methods
3. Register your implementation using `MathLiteral.registerFunction(MyFunction.class)`, `MathLiteral.registerConstant(MyConstant.class)` or `MathLiteral.registerOperator(MyOperator.class)` respectively.
4. The parser will now be able to parse your function/constant/operator.

To see working examples, open [FunctionImplementations.java](https://github.com/vatbub/mathevaluator/blob/master/mathevaluator/src/main/java/com/github/vatbub/mathevaluator/FunctionImplementations.java), [ConstantImplementations.java](https://github.com/vatbub/mathevaluator/blob/master/mathevaluator/src/main/java/com/github/vatbub/mathevaluator/ConstantImplementations.java) or [OperatorImplementations.java](https://github.com/vatbub/mathevaluator/blob/master/mathevaluator/src/main/java/com/github/vatbub/mathevaluator/OperatorImplementations.java) which contain the implementations of the built-in functions/constants/operators.

(*) You may also subclass `SingleArgumentOperator` if your operator can operate on one single input.
