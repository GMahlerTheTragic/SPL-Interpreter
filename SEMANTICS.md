# SPL' Interpreter Semantic Choices

This document outlines the semantic choices made for the interpreter of the programming language SPL' (pronounced SPL prime).

## 1. Implicit Boolean Conversion
In SPL', all types can be implicitly converted to boolean values. The conversion follows a simple rule: the number 0 will be interpreted as `false`, and everything else will be converted to `true`. For example:

```spl
var num = 0;        // num will be treated as false
var value = 42;     // value will be treated as true
var name = "John";  // name will be treated as true
```

We choose this rule to avoid type checking in conditions.

## 2. Operator Overloading

### Addition Operator (+)

The `+` operator in SPL' is overloaded for several types:

1. **String + String**: When both operands are strings, the `+` operator performs string concatenation. For example:
```spl
var str1 = "Hello, ";
var str2 = "world!";
var result = str1 + str2;  // "Hello, world!"
```

2. **String + Number**: When one operand is a string and the other is a number, the + operator performs string concatenation. The number is implicitly converted to a string before concatenation. For example:
```spl
var message = "The answer is: " + 42;  // "The answer is: 42"
```

3. **Booleans + Boolean**: When both operands are booleans, the + operator interprets them as the addition of the underlying integer values (0 or 1). For example:
```spl
var result1 = true + true;   // 2 (true + true is equivalent to 1 + 1)
var result2 = false + true;  // 1 (false + true is equivalent to 0 + 1)
```
This rule was motivated by ease-of-use considerations, since string concatenation and boolean algebra are
operations which are broadly useful.

### Subtraction Operator (-)

The - operator is overloaded only for the case of boolean - boolean. It results in the subtraction of the underlying numbers as well. For example:
```spl
var result = true - false;  // 1 (true - false is equivalent to 1 - 0)
```

Again the motivation is boolean algebra.

## 3. Redefinition of Variables within the Same Scope

In SPL', variables within the same scope can be redefined without any restrictions. When a variable is redefined, its previous value is replaced with the new assignment. For example:

```spl
var age = 25;
print(age);   // Output: 25

var age = 30;
print(age);   // Output: 30 (The variable 'age' is redefined within the same scope)
```

We choose this rule because it is easier to implement. Especially combined with the fifth rule below.

## 4. Shadowing of Variables in Inner Scopes

SPL' supports variable shadowing, which means that variables declared in inner scopes can overshadow variables with the same name in outer scopes. When a variable is shadowed, the inner variable takes precedence within its scope. For example:

```spl
var count = 10;

if (count > 5) {
    var message = "Count is greater than 5.";
    print(message);
    var count = 2;
    print(count);  // Output: 2 (The inner variable 'count' shadows the outer variable)
}

print(count);      // Output: 10 (The outer variable 'count' is not affected by the inner variable)
```

We choose this rule because comming from a Java background, it is more natural.

## 5. Handling Uninitialized Variables

In SPL', using uninitialized variables is not allowed, and attempting to do so will result in runtime errors. All variables must be initialized with a value before they can be used. For example:

```spl
var x;
print(x);  // This will raise a runtime error as variable 'x' is uninitialized.
```

We choose this rule because it is just easier to implement.
