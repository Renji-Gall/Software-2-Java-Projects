import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.list.List;

/**
 * JUnit test fixture for {@code List<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class ListTest {

    /**
     * Invokes the appropriate {@code List} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new list
     * @ensures constructorTest = (<>, <>)
     */
    protected abstract List<String> constructorTest();

    /**
     * Invokes the appropriate {@code List} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new list
     * @ensures constructorRef = (<>, <>)
     */
    protected abstract List<String> constructorRef();

    /**
     * Constructs a {@code List<String>} with the entries in {@code args} and
     * length of the left string equal to {@code leftLength}.
     *
     * @param list
     *            the {@code List} to construct
     * @param leftLength
     *            the length of the left string in the constructed {@code List}
     * @param args
     *            the entries for the list
     * @updates list
     * @requires list = (<>, <>) and 0 <= leftLength <= args.length
     * @ensures <pre>
     * list = ([first leftLength entries in args], [remaining entries in args])
     * </pre>
     */
    private void createFromArgsHelper(List<String> list, int leftLength, String... args) {
        for (String s : args) {
            list.addRightFront(s);
            list.advance();
        }
        list.moveToStart();
        for (int i = 0; i < leftLength; i++) {
            list.advance();
        }
    }

    /**
     * Creates and returns a {@code List<String>} of the implementation under
     * test type with the given entries.
     *
     * @param leftLength
     *            the length of the left string in the constructed {@code List}
     * @param args
     *            the entries for the list
     * @return the constructed list
     * @requires 0 <= leftLength <= args.length
     * @ensures <pre>
     * createFromArgs =
     *   ([first leftLength entries in args], [remaining entries in args])
     * </pre>
     */
    protected final List<String> createFromArgsTest(int leftLength, String... args) {
        assert 0 <= leftLength : "Violation of: 0 <= leftLength";
        assert leftLength <= args.length : "Violation of: leftLength <= args.length";
        List<String> list = this.constructorTest();
        this.createFromArgsHelper(list, leftLength, args);
        return list;
    }

    /**
     * Creates and returns a {@code List<String>} of the reference
     * implementation type with the given entries.
     *
     * @param leftLength
     *            the length of the left string in the constructed {@code List}
     * @param args
     *            the entries for the list
     * @return the constructed list
     * @requires 0 <= leftLength <= args.length
     * @ensures <pre>
     * createFromArgs =
     *   ([first leftLength entries in args], [remaining entries in args])
     * </pre>
     */
    protected final List<String> createFromArgsRef(int leftLength, String... args) {
        assert 0 <= leftLength : "Violation of: 0 <= leftLength";
        assert leftLength <= args.length : "Violation of: leftLength <= args.length";
        List<String> list = this.constructorRef();
        this.createFromArgsHelper(list, leftLength, args);
        return list;
    }

    /*
     * Test cases for constructor, addRightFront, removeRightFront, advance,
     * moveToStart, leftLength, and rightLength.
     */

    @Test
    public final void testConstructor() {

        List<String> list1 = this.constructorTest();
        List<String> list2 = this.constructorRef();

        assertEquals(list2, list1);
    }

    @Test
    public final void testAddRightFrontEmpty() {

        List<String> list1 = this.createFromArgsTest(0);
        List<String> list2 = this.createFromArgsRef(0, "hello");

        list1.addRightFront("hello");

        assertEquals(list2, list1);
    }

    @Test
    public final void testAddRightFrontLeftEmpty() {

        List<String> list1 = this.createFromArgsTest(0, "hello", "bye");
        List<String> list2 = this.createFromArgsRef(0, "ciao", "hello", "bye");

        list1.addRightFront("ciao");

        assertEquals(list2, list1);
    }

    @Test
    public final void testAddRightFrontRightEmpty() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(3, "hello", "bye", "ciao", "help");

        list1.addRightFront("help");

        assertEquals(list2, list1);
    }

    @Test
    public final void testRemoveRightFront() {

        List<String> list1 = this.createFromArgsTest(0, "hello");
        List<String> list2 = this.createFromArgsRef(0);

        String s = list1.removeRightFront();

        assertEquals("hello", s);
        assertEquals(list2, list1);
    }

    @Test
    public final void testRemoveRightFrontLeftEmpty() {

        List<String> list1 = this.createFromArgsTest(0, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(0, "bye", "ciao");

        String s = list1.removeRightFront();

        assertEquals("hello", s);
        assertEquals(list2, list1);
    }

    @Test
    public final void testRemoveRightFrontLeftNonEmptyRightOne() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao", "help");
        List<String> list2 = this.createFromArgsRef(3, "hello", "bye", "ciao");

        String s = list1.removeRightFront();

        assertEquals("help", s);
        assertEquals(list2, list1);
    }

    @Test
    public final void testAdvanceLeftEmpty() {

        List<String> list1 = this.createFromArgsTest(0, "hello");
        List<String> list2 = this.createFromArgsRef(1, "hello");

        list1.advance();

        assertEquals(list2, list1);
    }

    @Test
    public final void testAdvanceLeftNonEmpty() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao", "help");
        List<String> list2 = this.createFromArgsRef(4, "hello", "bye", "ciao", "help");

        list1.advance();

        assertEquals(list2, list1);
    }

    @Test
    public final void testMoveToStartEmpty() {

        List<String> list1 = this.createFromArgsTest(0);
        List<String> list2 = this.createFromArgsRef(0);

        list1.moveToStart();

        assertEquals(list2, list1);
    }

    @Test
    public final void testMoveToStartLeftEmpty() {

        List<String> list1 = this.createFromArgsTest(0, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(0, "hello", "bye", "ciao");

        list1.moveToStart();

        assertEquals(list2, list1);
    }

    @Test
    public final void testMoveToStartLeftNonEmpty() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(0, "hello", "bye", "ciao");

        list1.moveToStart();

        assertEquals(list2, list1);
    }

    @Test
    public final void testMoveToStartNonEmpty() {

        List<String> list1 = this.createFromArgsTest(2, "hello", "bye", "ciao", "help");
        List<String> list2 = this.createFromArgsRef(0, "hello", "bye", "ciao", "help");
        list1.moveToStart();

        assertEquals(list2, list1);
    }

    @Test
    public final void testRightLengthEmpty() {

        List<String> list1 = this.createFromArgsTest(0);
        List<String> list2 = this.createFromArgsRef(0);

        int i = list1.rightLength();

        assertEquals(0, i);
        assertEquals(list2, list1);
    }

    @Test
    public final void testRightLengthLeftEmpty() {

        List<String> list1 = this.createFromArgsTest(0, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(0, "hello", "bye", "ciao");

        int i = list1.rightLength();

        assertEquals(3, i);
        assertEquals(list2, list1);
    }

    @Test
    public final void testRightLengthLeftNonEmpty() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(3, "hello", "bye", "ciao");

        int i = list1.rightLength();

        assertEquals(0, i);
        assertEquals(list2, list1);
    }

    @Test
    public final void testLeftLengthEmpty() {

        List<String> list1 = this.createFromArgsTest(0);
        List<String> list2 = this.createFromArgsRef(0);

        int i = list1.leftLength();

        assertEquals(0, i);
        assertEquals(list2, list1);
    }

    @Test
    public final void testLeftLengthLeftNonEmpty() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(3, "hello", "bye", "ciao");

        int i = list1.leftLength();

        assertEquals(3, i);
        assertEquals(list2, list1);
    }

    @Test
    public final void testIteratorEmpty() {

        List<String> list1 = this.createFromArgsTest(0);
        List<String> list2 = this.createFromArgsRef(0);
        List<String> list3 = this.createFromArgsRef(0);

        for (String s : list1) {
            list2.addRightFront(s);
        }

        assertEquals(list3, list1);
        assertEquals(list3, list2);
    }

    @Test
    public final void testIteratorOnlyRight() {

        List<String> list1 = this.createFromArgsTest(0, "hello", "bye");
        List<String> list2 = this.createFromArgsRef(0);
        List<String> list3 = this.createFromArgsRef(0, "hello", "bye");
        List<String> list4 = this.createFromArgsRef(0, "bye", "hello");

        for (String s : list1) {
            list2.addRightFront(s);
        }

        assertEquals(list3, list1);
        assertEquals(list4, list2);
    }

    @Test
    public final void testIteratorOnlyLeft() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(0);
        List<String> list3 = this.createFromArgsRef(3, "hello", "bye", "ciao");
        List<String> list4 = this.createFromArgsRef(0, "ciao", "bye", "hello");

        for (String s : list1) {
            list2.addRightFront(s);
        }

        assertEquals(list3, list1);
        assertEquals(list4, list2);
    }

    @Test
    public final void testMoveToFinishEmpty() {

        List<String> list1 = this.createFromArgsTest(0);
        List<String> list2 = this.createFromArgsRef(0);

        list1.moveToFinish();

        assertEquals(list2, list1);
    }

    @Test
    public final void testMoveToFinishLeftEmpty() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(3, "hello", "bye", "ciao");

        list1.moveToFinish();

        assertEquals(list2, list1);
    }

    @Test
    public final void testMoveToFinishRightEmpty() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "bye", "ciao");
        List<String> list2 = this.createFromArgsRef(3, "hello", "bye", "ciao");

        list1.moveToFinish();

        assertEquals(list2, list1);
    }

    @Test
    public final void testMoveToFinishFull() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "goodbye", "ciao");
        List<String> list2 = this.createFromArgsRef(3, "hello", "goodbye", "ciao");

        list1.moveToFinish();

        assertEquals(list2, list1);
    }

    @Test
    public final void testMoveToFinishShowBug() {

        List<String> list1 = this.createFromArgsTest(0);
        List<String> list2 = this.createFromArgsRef(0, "hello");

        list1.moveToFinish();

        list1.addRightFront("hello");
        assertEquals(list2, list1);
    }

    // TODO - add test cases for retreat

    @Test
    public void testRetreatMinCase() {

        List<String> list1 = this.createFromArgsTest(1, "hello");
        List<String> list2 = this.createFromArgsRef(0, "hello");

        list1.retreat();

        assertEquals(list2, list1);

    }

    @Test
    public void testRetreatMaxCase() {

        List<String> list1 = this.createFromArgsTest(4, "hello", "goodbye", "ciao");
        List<String> list2 = this.createFromArgsTest(3, "hello", "goodbye", "ciao");

        list1.retreat();

        assertEquals(list2, list1);

    }

    @Test
    public void testRetreatRandom() {

        List<String> list1 = this.createFromArgsTest(3, "hello", "goodbye", "ciao");
        List<String> list2 = this.createFromArgsTest(2, "hello", "goodbye", "ciao");

        list1.retreat();

        assertEquals(list2, list1);

    }

    @Test
    public void testRetreatToEmptyLeft() {

        List<String> list1 = this.createFromArgsTest(1, "hello", "goodbye", "ciao");
        List<String> list2 = this.createFromArgsRef(0, "hello", "goodbye", "ciao");

        list1.retreat();

        assertEquals(list2, list1);

    }

}
