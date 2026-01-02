import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    @Test
    public final void testConstructor() {

        Map<String, String> value = this.constructorTest();
        Map<String, String> ref = this.constructorRef();

        assertEquals(ref, value);

    }

    @Test
    public final void testHasKeyTrue() {
        Map<String, String> map = this.createFromArgsTest("6", "7");
        assertEquals(true, map.hasKey("6"));
    }

    @Test
    public final void testHasKeyFalse() {
        Map<String, String> map = this.createFromArgsTest("6", "7");
        assertEquals(false, map.hasKey("5"));
    }

    @Test
    public final void testAdd() {

        Map<String, String> value = this.constructorTest();
        Map<String, String> expected = this.constructorRef();

        value.add("key", "value");
        expected.add("key", "value");

        assertEquals(expected, value);
    }

    @Test
    public final void testAddToNonEmpty() {

        Map<String, String> value = this.createFromArgsTest("key1", "value1", "key0",
                "value0");
        Map<String, String> expected = this.createFromArgsRef("key1", "value1", "key0",
                "value0");

        value.add("key2", "value2");
        expected.add("key2", "value2");

        assertEquals(expected.toString(), value.toString());

    }

    @Test
    public final void testRemoveToEmpty() {

        Map<String, String> value = this.createFromArgsTest("key", "value");
        Map<String, String> expected = this.createFromArgsRef("key", "value");

        Map.Pair<String, String> testRemoved = value.remove("key");
        expected.remove("key");

        assertEquals(expected, value);
        assertEquals("key", testRemoved.key());
        assertEquals("value", testRemoved.value());

    }

    @Test
    public final void testValue() {

        Map<String, String> value = this.createFromArgsTest("7", "6");
        Map<String, String> expected = this.createFromArgsRef("7", "6");

        String testValue = value.value("7");

        assertEquals(expected, value);
        assertEquals("6", testValue);

    }

}
