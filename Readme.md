# Math evaluator
This is just a small side project of mine which intends to parse math expressions and calculate the result.

## What it does
The lib can parse expressions which contain
- Additions
- Subtractions
- Multiplications
- Divisions
- Parenthesis
- Powers
- Functions (`sin`, `cos`, `tan`, `cot` and `sqrt`)

The lib takes string which follows the above rules, parses it and computes the result.

 ## How to use it
 Since I programmed the engine during one evening in front of the TV, I figured that it wouldn't be worth the time to upload the lib to maven central. 
 Hence, you need to clone the repo and run `mvn install` to get it working.
 Then, add the fllowing dependency:
 
 ```xml
<dependencies>
	<dependency>
		<groupId>com.github.vatbub</groupId>
		<artifactId>mathevaluator</artifactId>
		<version>1.0-SNAPSHOT</version>
	</dependency>
</dependencies>
```

After compiling, you can use the lib like so:

```java
MathExpression expression = new MathExpression("(5+10)*4/5*(2+2)");
double result =  expression.evaluate().getValue();
```

## Extending the parser
The parser is built in a modular way which allows it to be extended. If you wish to implement your own operators, constants or functions, just do the following:

1. Create a class which extends `Function`, `Constant` or `Operator` (*)
2. Implement all the abstract methods
3. Register your implementation using `MathLiteral.registerFunction(MyFunction.class)`, `MathLiteral.registerConstant(MyConstant.class)` or `MathLiteral.registerOperator(MyOperator.class)` respectively.
4. The parser will now be able to parse your function/constant/operator.

To see working examples, open [FunctionImplementations.java](https://github.com/vatbub/mathevaluator/blob/master/mathevaluator/src/main/java/com/github/vatbub/mathevaluator/FunctionImplementations.java), [ConstantImplementations.java](https://github.com/vatbub/mathevaluator/blob/master/mathevaluator/src/main/java/com/github/vatbub/mathevaluator/ConstantImplementations.java) or [OperatorImplementations.java](https://github.com/vatbub/mathevaluator/blob/master/mathevaluator/src/main/java/com/github/vatbub/mathevaluator/OperatorImplementations.java) which contain the implementations of the built-in functions/constants/operators.

(*) You may also subclass `SingleArgumentOperator` if your operator can operate on one single input.
