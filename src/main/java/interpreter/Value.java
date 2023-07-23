package interpreter;

import java.util.function.BiFunction;

import interpreter.exceptions.TypeException;

/**
 * Value class that encapsulates numeric and string values and their
 * corresponding operations.
 */
public class Value {

    public static final Value FALSE = new Value(0.0);
    public static final Value TRUE = new Value(1.0);
    public static final Value NaN = new Value(null, true);

    private Object value;
    private boolean isNaN;

    public Value(String value) {
        this.value = value;
    }

    // effectively making sure that quotation marks are not doubly printed
    public static Value fromPrimaryString(String value) {
        if (value.length() <= 2) {
            return new Value("");
        } else {
            return new Value(value.substring(1, value.length() - 1));
        }
    }

    public Value(double value) {
        this.value = value;
    }

    private Value(Object value, boolean isNaN) {
        this.value = value;
        this.isNaN = isNaN;
    }

    public double internalNumber() {
        return (double) value;
    }

    public String internalString() {
        return (String) value;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public boolean isNumber() {
        return value instanceof Double;
    }

    public boolean isNaN() {
        return isNaN;
    }

    public boolean isTrue() {
        assertNumber();
        return internalNumber() != 0;
    }

    public boolean isFalse() {
        assertNumber();
        return internalNumber() == 0;
    }

    private void assertNumber() {
        if (!isNumber()) {
            throw new TypeException("Couldn't evaluate numeric expression. Value \"" + value + "\" is not a number");
        }
    }

    public Value mul(Value right) {
        return arithmeticEval(right, (l, r) -> l * r);
    }

    public Value div(Value right) {
        return arithmeticEval(right, (l, r) -> l / r);
    }

    public Value mod(Value right) {
        return arithmeticEval(right, (l, r) -> l % r);
    }

    // operator overlaoding, see SEMANTICS.md
    public Value add(Value right) {
        if (isString() && right.isString()) {
            return new Value(internalString() + right.internalString());
        } else if (isString() && right.isNumber()) {
            return new Value(internalString() + right.internalNumber());
        } else if (isNumber() && right.isString()) {
            return new Value(internalNumber() + right.internalString());
        } else {
            return arithmeticEval(right, (l, r) -> l + r);
        }
    }

    public Value sub(Value right) {
        return arithmeticEval(right, (l, r) -> l - r);
    }

    private Value arithmeticEval(Value right, BiFunction<Double, Double, Double> oper) {
        assertNumber();
        right.assertNumber();
        return new Value(oper.apply(internalNumber(), right.internalNumber()));
    }

    public Value gt(Value right) {
        return relEval(right, (l, r) -> l > r);
    }

    public Value gte(Value right) {
        return relEval(right, (l, r) -> l >= r);
    }

    public Value lt(Value right) {
        return relEval(right, (l, r) -> l < r);
    }

    public Value lte(Value right) {
        return relEval(right, (l, r) -> l <= r);
    }

    // Check for equality
    public Value eq(Value right) {
        if (isNumber() && right.isNumber()) {
            return relEval(right, (l, r) -> l.equals(r));
        } else if (isString() && right.isString()) {
            return internalString().equals(right.internalString()) ? TRUE : FALSE;
        }
        return FALSE;
    }

    public Value neq(Value right) {
        Value eq = eq(right);
        return eq.equals(TRUE) ? FALSE : TRUE;
    }

    private Value relEval(Value right, BiFunction<Double, Double, Boolean> comparison) {
        assertNumber();
        right.assertNumber();
        if (comparison.apply(internalNumber(), right.internalNumber())) {
            return TRUE;
        }
        return FALSE;
    }

    public Value not() {
        assertNumber();
        if (internalNumber() == 0) {
            return TRUE;
        }
        return FALSE;
    }

    public Value and(Value right) {
        return isTrue() && right.isTrue() ? TRUE : FALSE;
    }

    public Value or(Value right) {
        return isTrue() || right.isTrue() ? TRUE : FALSE;
    }

    public Value exp(Value right) {
        assertNumber();
        right.assertNumber();
        return new Value(Math.round(Math.pow(internalNumber(), right.internalNumber())));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Value))
            return false;

        Value value1 = (Value) o;

        if (isNaN != value1.isNaN)
            return false;
        return value != null ? value.equals(value1.value) : value1.value == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (isNaN ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

}
