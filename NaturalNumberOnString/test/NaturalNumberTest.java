import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    @Test
    public final void testEmptyConstructor() {

        NaturalNumber number = this.constructorTest();
        NaturalNumber expected = this.constructorRef();

        assertEquals(expected, number);
    }

    @Test
    public final void testConstructorInt() {

        NaturalNumber num = this.constructorTest(67);
        NaturalNumber expected = this.constructorRef(67);

        assertEquals(expected, num);

    }

    @Test
    public final void testConstructorString() {

        NaturalNumber num = this.constructorTest("67");
        NaturalNumber expected = this.constructorRef("67");

        assertEquals(expected, num);
    }

    @Test
    public final void testConstructorNaturalNumber() {
        NaturalNumber test = new NaturalNumber1L(67);

        NaturalNumber num = this.constructorTest(test);
        NaturalNumber expected = this.constructorRef(test);

        assertEquals(expected, num);

    }

    @Test
    public final void testIsZeroWithNumber() {

        int test = 67;

        NaturalNumber num = this.constructorTest(test);
        NaturalNumber expected = this.constructorRef(test);

        assertEquals(expected, num);
        assertFalse(num.isZero());
    }

    @Test
    public final void testIsZeroWithZero() {

        int test = 0;

        NaturalNumber num = this.constructorTest(test);
        NaturalNumber expected = this.constructorRef(test);

        assertEquals(expected, num);
        assertFalse(num.isZero());
    }

    @Test
    public final void testIsZeroWithStringNumber() {

        String test = "67";

        NaturalNumber num = this.constructorTest(test);
        NaturalNumber expected = this.constructorRef(test);

        assertEquals(expected, num);
        assertFalse(num.isZero());
    }

    @Test
    public final void testIsZeroWithStringZero() {

        String test = "0";

        NaturalNumber num = this.constructorTest(test);
        NaturalNumber expected = this.constructorRef(test);

        assertEquals(expected, num);
        assertFalse(num.isZero());
    }

}
