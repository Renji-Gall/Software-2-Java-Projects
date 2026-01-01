import components.binarytree.BinaryTree;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Lets the user test the {@code hashCode(String)} method, by reading text lines
 * from a file (whose name is supplied by the user), and then outputting the
 * distribution of lines into buckets.
 *
 * @author Put your name here
 *
 */
public final class HashingExploration {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HashingExploration() {
    }

    /**
     * Computes {@code a} mod {@code b} as % should have been defined to work.
     *
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires b > 0
     * @ensures <pre>
     * 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)
     * </pre>
     */
    public static int mod(int a, int b) {
        assert b > 0 : "Violation of: b > 0";

        // TODO - fill in body

        // This line added just to make the component compilable.

        return a % b;
    }

    /**
     * Returns a hash code value for the given {@code String}.
     *
     * @param s
     *            the {@code String} whose hash code is computed
     * @return a hash code value for the given {@code String}
     * @ensures hashCode = [hash code value for the given String]
     */
    private static int hashCode(String s) {
        assert s != null : "Violation of: s is not null";

        // TODO - fill in body

        // This line added just to make the component compilable.
        int hashValue = 0;
        for (char c : s.toCharArray()) {
            hashValue += c;
        }
        return hashValue;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get hash table size and file name.
         */
        out.print("Hash table size: ");
        int hashTableSize = in.nextInteger();
        out.print("Text file name: ");
        String textFileName = in.nextLine();
        /*
         * Set up counts and counted. All entries in counts are automatically
         * initialized to 0.
         */
        int[] counts = new int[hashTableSize];
        Set<String> counted = new Set1L<String>();
        /*
         * Get some lines of input, hash them, and record counts.
         */
        SimpleReader textFile = new SimpleReader1L(textFileName);
        while (!textFile.atEOS()) {
            String line = textFile.nextLine();
            if (!counted.contains(line)) {
                int bucket = mod(hashCode(line), hashTableSize);
                counts[bucket]++;
                counted.add(line);
            }
        }
        textFile.close();
        /*
         * Report results.
         */
        out.println();
        out.println("Bucket\tHits\tBar");
        out.println("------\t----\t---");
        for (int i = 0; i < counts.length; i++) {
            out.print(i + "\t" + counts[i] + "\t");
            for (int j = 0; j < counts[i]; j++) {
                out.print("*");
            }
            out.println();
        }
        out.println();
        out.println("Total:\t" + counted.size());
        in.close();
        out.close();
    }

}

public static <T> int size(BinaryTree<T> t) {
    int result = 0;

    if (!t.isEmpty()) {
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T root = t.root();

        t.disassemble(left, right);

        int leftSize = size(left);
        int rightSize = size(right);

        result = 1 + leftSize + rightSize;

        t.assemble(root, left, right);
    }

    return result;
}

public static <T> int size(BinaryTree<T> t) {
    int result = 0;

    Stack<BinaryTree<T>> stack = new Stack<>();
    stack.push(t);

    while (!stack.isEmpty()) {
        BinaryTree<T> current = stack.pop();

        BinaryTree<T> left = current.newInstance();
        BinaryTree<T> right = current.newInstance();

        try {
            T root = current.root();
            current.disassemble(left, right);

            result++;

            stack.push(right);
            stack.push(left);

            current.assemble(root, left, right);

        } catch (IllegalStateException e) {

        }
    }

    return result;
}

public static BinaryTree<Integer> copy(BinaryTree<Integer> t) {

    Integer root = t.root();
    BinaryTree<Integer> leftCopy = copy(t.left());
    BinaryTree<Integer> rightCopy = copy(t.right());

    return new BinaryTree<>(root, leftCopy, rightCopy);

}
