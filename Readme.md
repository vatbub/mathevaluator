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
double result =  expression.evaluate().getValue(), 0);
```
