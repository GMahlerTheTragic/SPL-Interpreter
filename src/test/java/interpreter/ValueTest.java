package interpreter;

import interpreter.exceptions.TypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValueTest {

    private static final Double DELTA = 0.0001;

    // Test Value constructors and accessors

    @Test
    public void testStringValueConstructor() {
        Value value = new Value("Hello");
        assertEquals("Hello", value.internalString());
        assertTrue(value.isString());
        assertFalse(value.isNumber());
        assertFalse(value.isNaN());
    }

    @Test
    public void testNumberValueConstructor() {
        Value value = new Value(42.0);
        assertEquals(42.0, value.internalNumber(), 0.0001);
        assertFalse(value.isString());
        assertTrue(value.isNumber());
        assertFalse(value.isNaN());
    }

    @Test
    public void testFromPrimaryStringWithEmptyString() {
        Value value = Value.fromPrimaryString("\"\"");
        assertEquals("", value.internalString());
        assertTrue(value.isString());
        assertFalse(value.isNumber());
        assertFalse(value.isNaN());
    }

    @Test
    public void testFromPrimaryStringWithValidString() {
        Value value = Value.fromPrimaryString("\"Hello\"");
        assertEquals("Hello", value.internalString());
        assertTrue(value.isString());
        assertFalse(value.isNumber());
        assertFalse(value.isNaN());
    }

    @Test
    public void testInternalNumberWithNumberValue() {
        Value value = new Value(42.0);
        double result = value.internalNumber();
        assertEquals(42.0, result);
    }

    @Test
    public void testInternalNumberWithStringValue() {
        Value value = new Value("Hello");
        assertThrows(ClassCastException.class, value::internalNumber);
    }

    @Test
    public void testInternalStringWithStringValue() {
        Value value = new Value("Hello");
        String result = value.internalString();
        assertEquals("Hello", result);
    }

    @Test
    public void testInternalStringWithNumberValue() {
        Value value = new Value(42.0);
        assertThrows(ClassCastException.class, value::internalString);
    }

    @Test
    public void testIsStringWithStringValue() {
        Value value = new Value("Hello");
        assertTrue(value.isString());
    }

    @Test
    public void testIsStringWithNumberValue() {
        Value value = new Value(42.0);
        assertFalse(value.isString());
    }

    @Test
    public void testIsNumberWithNumberValue() {
        Value value = new Value(42.0);
        assertTrue(value.isNumber());
    }

    @Test
    public void testIsNumberWithStringValue() {
        Value value = new Value("Hello");
        assertFalse(value.isNumber());
    }

    @Test
    public void testIsNaNWithNaNValue() {
        Value value = Value.NaN;
        assertTrue(value.isNaN());
    }

    @Test
    public void testIsNaNWithNumberValue() {
        Value value = new Value(42.0);
        assertFalse(value.isNaN());
    }

    @Test
    public void testIsNaNWithStringValue() {
        Value value = new Value("Hello");
        assertFalse(value.isNaN());
    }

    @Test
    public void testIsTrueWithTrueValue() {
        Value value = new Value(1.0);
        assertTrue(value.isTrue());
        assertFalse(value.isFalse());
    }

    @Test
    public void testIsTrueWithFalseValue() {
        Value value = new Value(0.0);
        assertFalse(value.isTrue());
        assertTrue(value.isFalse());
    }

    @Test
    public void testMul() {
        Value value1 = new Value(2.0);
        Value value2 = new Value(3.0);
        Value result = value1.mul(value2);
        assertEquals(6.0, result.internalNumber(), DELTA);
    }

    @Test
    public void testDiv() {
        Value value1 = new Value(6.0);
        Value value2 = new Value(2.0);
        Value result = value1.div(value2);
        assertEquals(3.0, result.internalNumber(), DELTA);
    }

    @Test
    public void testMod() {
        Value value1 = new Value(5.0);
        Value value2 = new Value(3.0);
        Value result = value1.mod(value2);
        assertEquals(2.0, result.internalNumber(), DELTA);
    }

    @Test
    public void testAddWithTwoStrings() {
        Value value1 = new Value("Hello");
        Value value2 = new Value(" World!");
        Value result = value1.add(value2);
        assertEquals("Hello World!", result.internalString());
    }

    @Test
    public void testAddWithStringAndNumber() {
        Value value1 = new Value("Hello");
        Value value2 = new Value(42.0);
        Value result = value1.add(value2);
        assertEquals("Hello42.0", result.internalString());
    }

    @Test
    public void testAddWithTwoNumbers() {
        Value value1 = new Value(10.0);
        Value value2 = new Value(5.0);
        Value result = value1.add(value2);
        assertEquals(15.0, result.internalNumber(), DELTA);
    }

    @Test
    public void testGt() {
        Value value1 = new Value(5.0);
        Value value2 = new Value(2.0);
        Value result = value1.gt(value2);
        assertTrue(result.isTrue());
    }

    @Test
    public void testGte() {
        Value value1 = new Value(5.0);
        Value value2 = new Value(5.0);
        Value result = value1.gte(value2);
        assertTrue(result.isTrue());
    }

    @Test
    public void testLt() {
        Value value1 = new Value(2.0);
        Value value2 = new Value(5.0);
        Value result = value1.lt(value2);
        assertTrue(result.isTrue());
    }

    @Test
    public void testLte() {
        Value value1 = new Value(5.0);
        Value value2 = new Value(5.0);
        Value result = value1.lte(value2);
        assertTrue(result.isTrue());
    }

    @Test
    public void testEqWithNumbers() {
        Value value1 = new Value(5.0);
        Value value2 = new Value(5.0);
        Value result = value1.eq(value2);
        assertTrue(result.isTrue());
    }

    @Test
    public void testEqWithStrings() {
        Value value1 = new Value("Hello");
        Value value2 = new Value("Hello");
        Value result = value1.eq(value2);
        assertTrue(result.isTrue());
    }

    @Test
    public void testEqWithDifferentTypes() {
        Value value1 = new Value("Hello");
        Value value2 = new Value(5.0);
        Value result = value1.eq(value2);
        assertTrue(result.isFalse());
    }

    @Test
    public void testNeq() {
        Value value1 = new Value(5.0);
        Value value2 = new Value(5.0);
        Value result = value1.neq(value2);
        assertTrue(result.isFalse());
    }

    @Test
    public void testNotWithZero() {
        Value value = new Value(0.0);
        Value result = value.not();
        assertTrue(result.isTrue());
    }

    @Test
    public void testAndWithTrueValues() {
        Value value1 = new Value(1.0);
        Value value2 = new Value(1.0);
        Value result = value1.and(value2);
        assertTrue(result.isTrue());
    }

    @Test
    public void testOrWithFalseValues() {
        Value value1 = new Value(0.0);
        Value value2 = new Value(0.0);
        Value result = value1.or(value2);
        assertTrue(result.isFalse());
    }

    @Test
    public void testExp() {
        Value value1 = new Value(2.0);
        Value value2 = new Value(3.0);
        Value result = value1.exp(value2);
        assertEquals(8.0, result.internalNumber(), DELTA);
    }

    @Test
    public void testEqualsWithEqualValues() {
        Value value1 = new Value(42.0);
        Value value2 = new Value(42.0);
        assertTrue(value1.equals(value2));
    }

    @Test
    public void testEqualsWithDifferentValues() {
        Value value1 = new Value("Hello");
        Value value2 = new Value("World");
        assertFalse(value1.equals(value2));
    }

    // Test hashCode method

    @Test
    public void testHashCode() {
        Value value = new Value(42.0);
        int hashCode = value.hashCode();
        assertEquals(-933560320, hashCode);
    }

    @Test
    public void testHashCodeNull() {
        Value value = new Value(null);
        int hashCode = value.hashCode();
        assertEquals(0, hashCode);
    }

    @Test
    public void testHashCodeNaN() {
        Value value = Value.NaN;
        int hashCode = value.hashCode();
        assertEquals(1, hashCode);
    }

    @Test
    public void testToString() {
        Value value = new Value("Hello");
        String result = value.toString();
        assertEquals("Hello", result);
    }

    @Test
    public void testAssertNumberWithNumberValue() {
        Value value = new Value(42.0);
        assertDoesNotThrow(value::isTrue);
    }

    @Test
    public void testAssertNumberWithStringValue() {
        Value value = new Value("Hello");
        assertThrows(TypeException.class, value::isTrue);
    }
}
