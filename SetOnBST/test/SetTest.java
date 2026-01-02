import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    @Test
    public final void testDefault() {

        Set<String> value = this.constructorTest();
        Set<String> expected = this.constructorRef();

        assertEquals(expected, value);
    }

    @Test
    public final void testAdd() {

        Set<String> value = this.createFromArgsTest();
        Set<String> expected = this.createFromArgsRef("hello");

        value.add("hello");

        assertEquals(expected, value);

    }

    @Test
    public final void remove() {

        Set<String> value = this.createFromArgsTest("hello");
        Set<String> expected = this.createFromArgsRef();

        String expectedString = "hello";

        String valueString = value.remove("hello");

        assertEquals(expected, value);
        assertEquals(expectedString, valueString);

    }

    @Test
    public final void testSize() {

        Set<String> value = this.createFromArgsTest();
        Set<String> expected = this.createFromArgsRef();

        int size = value.size();

        assertEquals(expected, value);
        assertEquals(0, size);
    }

    @Test
    public final void testSizeOne() {

        Set<String> value = this.createFromArgsTest("hello");
        Set<String> expected = this.createFromArgsRef("hello");

        int size = value.size();

        assertEquals(expected, value);
        assertEquals(1, size);
    }

}
