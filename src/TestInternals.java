import java.util.Iterator;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Lexicon;

public class TestInternals extends LockedTestCase {

	protected Lexicon.Spy spy;
	protected Lexicon self;
	protected Iterator<String> it;
	protected Stack<Lexicon.Spy.Node> pending;
	
	@Override // implementation
	public void setUp() {
		spy = new Lexicon.Spy();
		pending = new Stack<>();
	}
	
	protected Lexicon.Spy.Node n0, n1, n2, n3, n4, n5, n6, n7, n8, n9;
	
	protected Lexicon.Spy.Node newNode(String key, Lexicon.Spy.Node l, Lexicon.Spy.Node r) {
		return new Lexicon.Spy.Node(key, l, r);
	}
	
	protected int reports = 0;
	
	protected <T> void assertReporting(T expected, boolean expectReport, Supplier<T> test) {
		reports = 0;
		Consumer<String> savedReporter = spy.getReporter();
		try {
			spy.setReporter((String message) -> {
				++reports;
				if (message == null || message.trim().isEmpty()) {
					assertFalse("Uninformative report is not acceptable", true);
				}
				if (!expectReport) {
					assertFalse("Reported error incorrectly: " + message, true);
				}
			});
			assertEquals(expected, test.get());
			if (expectReport) {
				assertEquals("Expected exactly one invariant error to be reported", 1, reports);
			}
			spy.setReporter(null);
		} finally {
			spy.setReporter(savedReporter);
		}
	}
	
	protected void assertCheckInRange(int res, Lexicon.Spy.Node r, String lo, String hi) {
		assertReporting(res, res < 0, () -> spy.checkInRange(r, lo, hi));
	}
	
	protected void assertWellFormed(boolean expected, Lexicon r) {
		assertReporting(expected, !expected, () -> spy.wellFormed(r));
	}

	protected void assertWellFormed(boolean expected, Iterator<String> it) {
		assertReporting(expected, !expected, () -> spy.wellFormed(it));
	}

	
	/// Locked tests
	
	@SuppressWarnings("unused")
	public void testA00() {
		// Assume the tree has the following form:
		// tree is   5 
		//         /   \
		//        3     8       
		//       /     /
		//      1     6
		//       \     \
		//        2     7
		// and that we write stacks with the top to the right, 
		// so 586 means the stack with three nodes with 6 at the top.
		String s1 = Ts(2023918012); // what is the initial stack for the iterator?
		String s2 = Ts(1998508621); // what is the next stack after Node("1") is visited?
		String s3 = Ts(1188713359); // what is the next stack after Node("2") is visited?
		String s4 = Ts(1060437683); // what is the next stack after Node("3") is visited?
		String s5 = Ts(1530577576); // what is the next stack after Node("5") is visited? 
	}
	
	
	
	public void testC00() {
		assertCheckInRange(0, null, null, null);
	}
	
	public void testC01() {
		assertCheckInRange(0, null, "A", null);
	}

	public void testC02() {
		assertCheckInRange(0, null, null, "Z");
	}
	
	public void testC03() {
		assertCheckInRange(0, null, "A", "Z");
	}
	
	public void testC04() {
		n0 = newNode(null, null, null);
		assertCheckInRange(-1, n0, null, null);
	}
	
	public void testC05() {
		n0 = newNode(null, null, null);
		assertCheckInRange(-1, n0, "A", "Z");
	}
	
	public void testC10() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(1, n1, null, null);
	}
	
	public void testC11() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(1, n1, "A", null);
	}
	
	public void testC12() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(1, n1, null, "ball");
	}
	
	public void testC13() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(1, n1, "all", "ball");
	}
	
	public void testC14() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(-1, n1, "ball", null);
	}
	
	public void testC15() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(-1, n1, null, "Z");
	}
	
	public void testC16() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(-1, n1, "A", "Z");
	}
	
	public void testC17() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(-1, n1, "apple", "ball");
	}
	
	public void testC18() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(-1, n1, "all", "apple");
	}
	
	public void testC19() {
		n1 = newNode("apple", null, null);
		assertCheckInRange(-1, n1, null, "apple");
	}
	
	public void testC20() {
		n1 = newNode("orange", null, null);
		n2 = newNode("apple", null, n1);
		assertCheckInRange(2, n2, null, null);
	}
	
	public void testC21() {
		n1 = newNode("orange", null, null);
		n2 = newNode("apple", null, n1);
		assertCheckInRange(2, n2, null, "pear");
	}
	
	public void testC22() {
		n1 = newNode("orange", null, null);
		n2 = newNode("apple", null, n1);
		assertCheckInRange(2, n2, "all", "set");
	}
	
	public void testC23() {
		n1 = newNode("orange", null, null);
		n2 = newNode("apple", null, n1);
		assertCheckInRange(-1, n2, "all", "ball");
	}
	
	public void testC24() {
		n1 = newNode("orange", null, null);
		n2 = newNode("apple", null, n1);
		assertCheckInRange(-1, n2, "all", "orange");
	}
	
	public void testC25() {
		n1 = newNode("orange", null, null);
		n2 = newNode("apple", null, n1);
		assertCheckInRange(-1, n2, null, "lemon");
	}
	
	public void testC26() {
		n1 = newNode(null, null, null);
		n2 = newNode("apple", null, n1);
		assertCheckInRange(-1, n2, null, null);
	}
	
	public void testC27() {
		n1 = newNode("apple", null, null);
		n2 = newNode("apple", null, n1);
		assertCheckInRange(-1, n2, null, null);
	}
	
	public void testC28() {
		n1 = newNode("orange", null, null);
		n2 = newNode("apple", n1, null);
		assertCheckInRange(-1, n2, null, null);
	}
	
	public void testC29() {
		n1 = newNode("apple", null, null);
		n2 = newNode("apple", n1, null);
		assertCheckInRange(-1, n2, null, null);
	}
	
	public void testC30() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		assertCheckInRange(2, n2, null, null);
	}
	
	public void testC31() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		assertCheckInRange(2, n2, "act", null);
	}
	
	public void testC32() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		assertCheckInRange(2, n2, null, "quince");
	}
	
	public void testC33() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		assertCheckInRange(2, n2, "act", "raspberry");
	}
	
	public void testC34() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		assertCheckInRange(-1, n2, "date", null);
	}
	
	public void testC35() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		assertCheckInRange(-1, n2, null, "orange");
	}
	
	public void testC36() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		assertCheckInRange(-1, n2, "date", "orance");
	}
	
	public void testC37() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		assertCheckInRange(-1, n2, "apple", "raspberry");
	}
	
	public void testC38() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", null, n1);
		assertCheckInRange(-1, n2, null, "raspberry");
	}
	
	public void testC39() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", n1, null);
		n1.setRight(n2);
		assertCheckInRange(-1, n2, null, "quince");
	}
	
	public void testC40() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", null, null);
		n3 = newNode("orange", n1, n2);
		assertCheckInRange(3, n3, null, null);
	}
	
	public void testC41() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", null, null);
		n3 = newNode("orange", n2, n1);
		assertCheckInRange(-1, n3, null, null);
	}
	
	public void testC42() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", null, null);
		n3 = newNode("orange", n1, n2);
		assertCheckInRange(-1, n3, "date", null);
	}
	
	public void testC43() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", null, null);
		n3 = newNode("orange", n1, n2);
		assertCheckInRange(-1, n3, null, "paper");
	}
	
	public void testC44() {
		n1 = newNode("apple", null, null);
		n2 = newNode("pear", null, null);
		n3 = newNode("orange", n1, n2);
		assertCheckInRange(-1, n3, "date", "paper");
	}
	
	public void testC45() {
		n1 = newNode("apple", null, null);
		n2 = newNode("banana", n1, null);
		n3 = newNode("orange", n2, null);
		assertCheckInRange(3, n3, null, "pear");
	}
	
	public void testC46() {
		n1 = newNode("apple", null, null);
		n2 = newNode("banana", n1, null);
		n3 = newNode("orange", n2, null);
		assertCheckInRange(-1, n3, "awful", "pear");
	}
	
	public void testC47() {
		n1 = newNode("pear", null, null);
		n2 = newNode("melon", null, n1);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(3, n3, "cherry", "raspberry");
	}
	
	public void testC48() {
		n1 = newNode("pear", null, null);
		n2 = newNode("melon", null, n1);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, "cherry", "orange");
	}
	
	public void testC49() {
		n1 = newNode("pear", null, null);
		n2 = newNode("melon", null, n1);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, null, "pear");
	}
	
	public void testC50() {
		n1 = newNode("grape", null, null);
		n2 = newNode("banana", null, n1);
		n3 = newNode("lemon", n2, null);
		assertCheckInRange(3, n3, null, null);
	}
	
	public void testC51() {
		n1 = newNode("grape", null, null);
		n2 = newNode("banana", null, n1);
		n3 = newNode("lemon", n2, null);
		assertCheckInRange(3, n3, "apple", "orange");
	}
	
	public void testC52() {
		n1 = newNode("orange", null, null);
		n2 = newNode("banana", null, n1);
		n3 = newNode("lemon", n2, null);
		assertCheckInRange(-1, n3, "apple", "pear");
	}
	
	public void testC53() {
		n1 = newNode("orange", null, null);
		n2 = newNode("banana", null, n1);
		n3 = newNode("lemon", n2, null);
		assertCheckInRange(-1, n3, null, null);
	}
	
	public void testC54() {
		n1 = newNode("orange", null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(3, n3, null, null);
	}
	
	public void testC55() {
		n1 = newNode("orange", null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(3, n3, "apple", "raspberry");
	}
	
	public void testC56() {
		n1 = newNode("banana", null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, "apple", "raspberry");
	}
	
	public void testC57() {
		n1 = newNode("banana", null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, null, null);
	}
	
	public void testC58() {
		n1 = newNode("orange", null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, "apple", "party");
	}
	
	public void testC59() {
		n1 = newNode("orange", null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, "lemon", "raspberry");
	}

	public void testC60() {
		n0 = newNode(null, null, null);
		n2 = newNode("melon", null, n0);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, "cherry", null);
	}
	
	public void testC61() {
		n0 = newNode(null, null, null);
		n2 = newNode("banana", n0, null);
		n3 = newNode("orange", n2, null);
		assertCheckInRange(-1, n3, null, null);
	}
	
	public void testC62() {
		n1 = newNode(null, null, null);
		n2 = newNode("banana", null, n1);
		n3 = newNode("lemon", n2, null);
		assertCheckInRange(-1, n3, "apple", "orange");
	}
	
	public void testC63() {
		n1 = newNode(null, null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, "apple", "raspberry");
	}
	
	public void testC64() {
		n1 = newNode(new String("grape"), null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(-1, n3, "apple", "raspberry");
	}
	
	public void testC65() {
		n1 = newNode("null", null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		assertCheckInRange(3, n3, "apple", "raspberry");
	}
	
	public void testC66() {
		n1 = newNode("apple", null, null);
		n2 = newNode("banana", n1, null);
		n3 = newNode("orange", n2, null);
		n1.setRight(n3);
		assertCheckInRange(-1, n3, null, "pear");
	}
	
	public void testC67() {
		n1 = newNode("pear", null, null);
		n2 = newNode("melon", null, n1);
		n3 = newNode("grape", null, n2);
		n1.setLeft(n3);
		assertCheckInRange(-1, n3, "cherry", "raspberry");
	}
	
	public void testC68() {
		n1 = newNode("orange", null, null);
		n2 = newNode("pear", n1, null);
		n3 = newNode("grape", null, n2);
		n1.setLeft(n3);
		assertCheckInRange(-1, n3, "apple", "raspberry");
	}
	
	public void testC69() {
		n1 = newNode("grape", null, null);
		n2 = newNode("banana", null, n1);
		n3 = newNode("lemon", n2, null);
		assertCheckInRange(3, n3, null, null);
		n1.setRight(n3);
		assertCheckInRange(-1, n3, "apple", "raspberry");
	}
	
	
	private Lexicon.Spy.Node makeBig() {
		n1 = newNode("12", null, null);
		n2 = newNode("21", null, null);
		n3 = newNode("28", null, null);
		n4 = newNode("42", null, null);
		n5 = newNode("59", null, null);
		n6 = newNode("68", null, null);
		n7 = newNode("81", null, null);
		n8 = newNode("92", null, null);
		return newNode("54",
				newNode("23",
				 newNode("17", n1, n2),
				 newNode("33", n3, n4)),
				newNode("75",
				 newNode("60", n5, n6),
				 newNode("85", n7, n8)));
	}
	
	public void testC70() {
		n9 = makeBig();
		assertCheckInRange(15, n9, "10", "95");
	}
	
	public void testC71() {
		n9 = makeBig();
		assertCheckInRange(-1, n9, "13", "95");
	}
	
	public void testC72() {
		n9 = makeBig();
		n1.setString("19");
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC73() {
		n9 = makeBig();
		n2.setString("26");
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC74() {
		n9 = makeBig();
		n3.setString("22");
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC75() {
		n9 = makeBig();
		n4.setString("55");
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC76() {
		n9 = makeBig();
		n5.setString("51");
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC77() {
		n9 = makeBig();
		n6.setString("77");
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC78() {
		n9 = makeBig();
		n7.setString("73");
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC79() {
		n9 = makeBig();
		n8.setString("97");
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC81() {
		n9 = makeBig();
		n1.setString(null);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC82() {
		n9 = makeBig();
		n2.setString(null);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC83() {
		n9 = makeBig();
		n3.setString(null);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC84() {
		n9 = makeBig();
		n4.setString(null);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC85() {
		n9 = makeBig();
		n5.setString(null);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC86() {
		n9 = makeBig();
		n6.setString(null);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC87() {
		n9 = makeBig();
		n7.setString(null);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC88() {
		n9 = makeBig();
		n8.setString(null);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC91() {
		n9 = makeBig();
		n1.setRight(n9);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC92() {
		n9 = makeBig();
		n2.setRight(n9);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC93() {
		n9 = makeBig();
		n3.setRight(n9);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC94() {
		n9 = makeBig();
		n4.setRight(n9);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC95() {
		n9 = makeBig();
		n5.setLeft(n9);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC96() {
		n9 = makeBig();
		n6.setLeft(n9);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC97() {
		n9 = makeBig();
		n7.setLeft(n9);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	public void testC98() {
		n9 = makeBig();
		n8.setLeft(n9);
		assertCheckInRange(-1, n9, "10", "95");
	}
	
	
	public void testW00() {
		self = spy.newInstance(null, 0, 42);
		assertWellFormed(true, self);
	}
	
	public void testW01() {
		self = spy.newInstance(null, 1, 42);
		assertWellFormed(false, self);
	}
	
	public void testW02() {
		self = spy.newInstance(null, -1, 42);
		assertWellFormed(false, self);
	}
	
	public void testW03() {
		n0 = newNode(null, null, null);
		self = spy.newInstance(n0, 0, 42);
		assertWellFormed(false, self);
	}

	public void testW04() {
		n0 = newNode(null, null, null);
		self = spy.newInstance(n0, 1, 42);
		assertWellFormed(false, self);
	}

	public void testW05() {
		n0 = newNode(null, null, null);
		self = spy.newInstance(n0, -1, 42);
		assertWellFormed(false, self);
	}

	public void testW10() {
		n1 = newNode("", null, null);
		self = spy.newInstance(n1, 1, 42);
		assertWellFormed(true, self);
	}

	public void testW11() {
		n1 = newNode("", null, null);
		self = spy.newInstance(n1, 0, 42);
		assertWellFormed(false, self);
	}

	public void testW12() {
		n1 = newNode("hello", null, null);
		self = spy.newInstance(n1, 1, 42);
		assertWellFormed(true, self);
	}

	public void testW13() {
		n1 = newNode("hello", null, null);
		self = spy.newInstance(n1, 2, 42);
		assertWellFormed(false, self);
	}

	public void testW20() {
		n1 = newNode("hello", null, null);
		n2 = newNode("goodbye", null, n1);
		self = spy.newInstance(n2, 2, 42);
		assertWellFormed(true, self);
	}

	public void testW21() {
		n1 = newNode("hello", null, null);
		n2 = newNode("goodbye", null, n1);
		self = spy.newInstance(n2, 1, 42);
		assertWellFormed(false, self);
	}

	public void testW22() {
		n1 = newNode("hello", null, null);
		n2 = newNode("goodbye", null, n1);
		self = spy.newInstance(n2, 3, 42);
		assertWellFormed(false, self);
	}

	public void testW23() {
		n1 = newNode("hello", null, null);
		n2 = newNode("goodbye", n1, null);
		self = spy.newInstance(n2, 2, 42);
		assertWellFormed(false, self);
	}

	public void testW24() {
		n1 = newNode("hello", null, null);
		n2 = newNode("goodbye", n1, null);
		self = spy.newInstance(n2, -1, 42);
		assertWellFormed(false, self);
	}
	
	public void testW25() {
		n1 = newNode("hello", null, null);
		n2 = newNode("goodbye", null, n1);
		n1.setLeft(n2);
		self = spy.newInstance(n2, 2, 42);
		assertWellFormed(false, self);
	}
	
	public void testW26() {
		n1 = newNode(null, null, null);
		n2 = newNode("goodbye", null, n1);
		self = spy.newInstance(n2, 2, 42);
		assertWellFormed(false, self);
	}
	
	public void testW27() {
		n1 = newNode(null, null, null);
		n2 = newNode("goodbye", null, n1);
		self = spy.newInstance(n2, 1, 42);
		assertWellFormed(false, self);
	}
	
	public void testW28() {
		n1 = newNode(null, null, null);
		n2 = newNode("goodbye", null, n1);
		self = spy.newInstance(n2, -1, 42);
		assertWellFormed(false, self);
	}
	
	public void testW29() {
		n1 = newNode("null", null, null);
		n2 = newNode("goodbye", null, n1);
		self = spy.newInstance(n2, 2, 42);
		assertWellFormed(true, self);
	}

	public void testW30() {
		n9 = makeBig();
		self = spy.newInstance(n9, 15, 42);
		assertWellFormed(true, self);
	}

	public void testW31() {
		n9 = makeBig();
		self = spy.newInstance(n9, 14, 42);
		assertWellFormed(false, self);
	}

	public void testW32() {
		n9 = makeBig();
		self = spy.newInstance(n9, 30, 42);
		assertWellFormed(false, self);
	}
	
	public void testW33() {
		n9 = makeBig();
		self = spy.newInstance(n9, 15, 42);
		n4.setString(null);
		assertWellFormed(false, self);
	}
	
	public void testW34() {
		n9 = makeBig();
		self = spy.newInstance(n9, -1, 42);
		n4.setString(null);
		assertWellFormed(false, self);
	}
	
	public void testW35() {
		n9 = makeBig();
		self = spy.newInstance(n9, 15, 42);
		n4.setString(new String("54"));
		assertWellFormed(false, self);
	}
	
	public void testW36() {
		n9 = makeBig();
		self = spy.newInstance(n9, -1, 42);
		n4.setString(new String("54"));
		assertWellFormed(false, self);
	}

	public void testW37() {
		n9 = makeBig();
		self = spy.newInstance(n9, 15, 42);
		n4.setRight(n9);
		assertWellFormed(false, self);
	}

	public void testW38() {
		n9 = makeBig();
		self = spy.newInstance(n9, -1, 42);
		n4.setRight(n9);
		assertWellFormed(false, self);
	}

	public void testW39() {
		n9 = makeBig();
		self = spy.newInstance(n9, 15, 42);
		n1.setRight(n9);
		n2.setRight(n9);
		n3.setRight(n9);
		n5.setLeft(n9);
		n6.setLeft(n9);
		n7.setLeft(n9);
		assertWellFormed(false, self);
	}
	
	
	public void testX00() {
		self = spy.newInstance(null, 0, 42);
		it = spy.newIterator(self, new Stack<>(), null, 42);
		assertWellFormed(true, it);
	}
	
	public void testX01() {
		self = spy.newInstance(null, 0, 42);
		it = spy.newIterator(self, new Stack<>(), null, 15);
		assertWellFormed(true, it);
	}
	
	public void testX02() {
		self = spy.newInstance(null, 0, 55);
		n1 = newNode("hello", null, null);
		it = spy.newIterator(self, new Stack<>(), n1, 42);
		assertWellFormed(true, it);
	}
	
	public void testX03() {
		self = spy.newInstance(null, 0, 15);
		n1 = newNode("hello", null, null);
		it = spy.newIterator(self, new Stack<>(), n1, 15);
		assertWellFormed(false, it);
	}
	
	public void testX04() {
		self = spy.newInstance(null, 1, 15);
		it = spy.newIterator(self, new Stack<>(), null, 55);
		assertWellFormed(false, it);
	}

	public void testX05() {
		self = spy.newInstance(null, 0, 42);
		pending.add(null);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(false, it);
	}

	public void testX10() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		it = spy.newIterator(self, pending, n1, 32);
		assertWellFormed(true, it);
	}
	
	public void testX11() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		it = spy.newIterator(self, pending, null, 32);
		assertWellFormed(true, it);
	}
	
	public void testX12() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		pending.add(n1);
		it = spy.newIterator(self, pending, null, 32);
		assertWellFormed(true, it);
	}
	
	public void testX13() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		pending.add(n1);
		it = spy.newIterator(self, pending, n1, 32);
		assertWellFormed(false, it);
	}
	
	public void testX14() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		pending.add(newNode("ice", null, null));
		it = spy.newIterator(self, pending, null, 32);
		assertWellFormed(false, it);
	}
	
	public void testX15() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		it = spy.newIterator(self, pending, newNode("ice", null, null), 32);
		assertWellFormed(false, it);
	}

	public void testX16() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		pending.add(n1);
		it = spy.newIterator(self, pending, n1, 42);
		assertWellFormed(true, it);
	}
	
	public void testX17() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		pending.add(null);
		it = spy.newIterator(self, pending, null, 32);
		assertWellFormed(false, it);
	}
	
	public void testX18() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		pending.add(n1);
		pending.add(n1);
		it = spy.newIterator(self, pending, null, 32);
		assertWellFormed(false, it);
	}
	
	public void testX19() {
		n1 = newNode("ice", null, null);
		self = spy.newInstance(n1, 1, 32);
		pending.add(null);
		pending.add(n1);
		it = spy.newIterator(self, pending, null, 32);
		assertWellFormed(false, it);
	}

	protected void makeStringTree() {
		n2 = newNode("2", null, null);
		n1 = newNode("1", null, n2);
		n3 = newNode("3", n1, null);
		n7 = newNode("7", null, null);
		n6 = newNode("6", null, n7);
		n8 = newNode("8", n6, null);
		n5 = newNode("5", n3, n8);
		// tree is   5 
		//         /   \
		//        3     8       
		//       /     /
		//      1     6
		//       \     \
		//        2     7
		self = spy.newInstance(n5, 7, 42);
		assertWellFormed(true, self);
	}
	
	public void testX50() {
		makeStringTree();
		pending.push(n5);
		pending.push(n3);
		pending.push(n1);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(true, it);
	}
	
	public void testX51() {
		makeStringTree();
		pending.push(n5);
		// pending.push(n3);
		pending.push(n1);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(false, it);
	}
	
	public void testX52() {
		makeStringTree();
		pending.push(n5);
		pending.push(n3);
		pending.push(n2);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(true, it);
	}

	public void testX53() {
		makeStringTree();
		pending.push(n5);
		pending.push(n3);
		pending.push(n1);
		pending.push(n2);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(false, it);
	}

	public void testX54() {
		makeStringTree();
		pending.push(n5);
		pending.push(n2);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(false, it);
	}
	
	public void testX55() {
		makeStringTree();
		pending.push(n3);
		pending.push(n2);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(false, it);
	}

	public void testX56() {
		makeStringTree();
		pending.push(n8);
		pending.push(n6);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(true, it);
	}

	public void testX57() {
		makeStringTree();
		pending.push(n5);
		pending.push(n8);
		pending.push(n6);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(false, it);
	}

	public void testX58() {
		makeStringTree();
		pending.push(n8);
		pending.push(n7);
		pending.push(n6);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(false, it);
	}

	public void testX59() {
		makeStringTree();
		pending.push(n7);
		it = spy.newIterator(self, pending, null, 42);
		assertWellFormed(false, it);
	}
	
	public void testX60() {
		makeStringTree();
		pending.push(n5);
		pending.push(n3);
		pending.push(n2);
		it = spy.newIterator(self, pending, n1, 42);
		assertWellFormed(true, it);
	}
	
	public void testX61() {
		makeStringTree();
		pending.push(n5);
		pending.push(n3);
		it = spy.newIterator(self, pending, n1, 42);
		assertWellFormed(false, it);
	}
	
	public void testX62() {
		makeStringTree();
		pending.push(n5);
		pending.push(n3);
		pending.push(n2);
		it = spy.newIterator(self, pending, n2, 42);
		assertWellFormed(false, it);
	}
	
	public void testX63() {
		makeStringTree();
		pending.push(n5);
		pending.push(n3);
		it = spy.newIterator(self, pending, n2, 42);
		assertWellFormed(true, it);
	}
	
	public void testX64() {
		makeStringTree();
		pending.push(n5);
		it = spy.newIterator(self, pending, n3, 42);
		assertWellFormed(true, it);
	}
	
	public void testX65() {
		makeStringTree();
		pending.push(n5);
		n4 = newNode("4", null, null);
		it = spy.newIterator(self, pending, n4, 42);
		assertWellFormed(false, it);
	}
	
	public void testX66() {
		makeStringTree();
		pending.push(n5);
		it = spy.newIterator(self, pending, n8, 42);
		assertWellFormed(false, it);
	}
	
	public void testX67() {
		makeStringTree();
		it = spy.newIterator(self, pending, n8, 42);
		assertWellFormed(true, it);
	}
	
	public void testX68() {
		makeStringTree();
		pending.push(n8);
		it = spy.newIterator(self, pending, n6, 42);
		assertWellFormed(false, it);
	}
	
	public void testX69() {
		makeStringTree();
		pending.push(n7);
		it = spy.newIterator(self, pending, n6, 42);
		assertWellFormed(false, it);
	}
}