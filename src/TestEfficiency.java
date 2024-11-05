import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;
import edu.uwm.cs351.Lexicon;


public class TestEfficiency extends TestCase {
	Set<String> b;
	Lexicon lex;
	private Random random;

	private static final int POWER = 20;
	private static final int MAX = (1 << POWER);
	private static final int TESTS = 500000;

	private String makeNumericString(int i) {
		return String.format("%08d", i);
	}

	protected void setUp() throws Exception {
		super.setUp();
		random = new Random();

		try {assert 1/0 == 42 : "OK";}
		catch (ArithmeticException ex) {
			assertFalse("Assertions must NOT be enabled while running efficiency tests.",true);}

		b = lex = new Lexicon();
		int max = (1 << (POWER)); // 2^(POWER) = one million
		for (int power = POWER; power > 1; --power) {
			int incr = 1 << power;
			for (int i=1 << (power-1); i < max; i += incr) {
				b.add(makeNumericString(i));
			}
		}
	}

	@Override
	protected void tearDown() {
		b = null;
	}

	public void testA() {
		for (int i=0; i < TESTS; ++i) {
			assertEquals((1<<(POWER-1))-1,b.size());
		}
	}

	public void testB() {
		for (int i=1; i < TESTS; ++i) {
			assertEquals((i&1) == 0, b.contains(makeNumericString(i)));
		}
	}

	public void testC() {
		for (int i=1; i < TESTS; i+=5) {
			assertEquals((i&1) == 0, b.remove(makeNumericString(i)));
		}
	}

	public void testD() {
		Lexicon l = (Lexicon)b;
		for (int i=0; i < TESTS; ++i) {
			assertEquals(makeNumericString(2),l.getMin());
		}
	}

	public void testE() {
		Lexicon l = (Lexicon)b;
		for (int i=0; i < TESTS; ++i) {
			int r = random.nextInt(TESTS);
			assertEquals(makeNumericString(r*2+2),l.getNext(makeNumericString(r*2)));
		}
	}

	public void testF() {
		String two = makeNumericString(2);
		for (int i=0; i < TESTS; ++i) {
			assertEquals(two,b.iterator().next());
		}
	}

	public void testG() {
		Iterator<String> it = b.iterator();
		for (int i=0; i < TESTS; ++i) {
			assertTrue(it.hasNext());
			assertEquals(makeNumericString(i*2+2),it.next());
		}
	}

	public void testH() {
		Lexicon l = (Lexicon)b;
		Iterator<String> it;
		for (int i=0; i < TESTS; ++i) {
			it = l.iterator(makeNumericString(i*2+1));
			assertEquals(makeNumericString(i*2+2),it.next());
		}
	}

    public void testI() {
    	ArrayList<String> list = new ArrayList<>();
    	lex.consumeAllWithPrefix(str -> list.add(str), "");
    	
    	for (int i=2; i < MAX; i += 2)
    		assertEquals(makeNumericString(i),list.get(i/2-1));
    }
    
    public void testJ() {
    	ArrayList<String> list = new ArrayList<>();
    	for (int i=1; i < MAX; i++) {
    		String pre = makeNumericString(i);
    		lex.consumeAllWithPrefix(str -> list.add(str), pre);
    		if ((i % 2) == 0) {
    			assertEquals(pre, 1, list.size());
    		} else {
    			assertEquals(pre, 0, list.size());
    		}
    		list.clear();
    	}
    }

	public void testK() {
		ArrayList<String> list = new ArrayList<>();
		for (int i=1; i < TESTS; i++) {
			String pre = makeNumericString(i*2);
			lex.consumeAllWithPrefix(str -> list.add(str), pre);
			assertEquals(pre, 1,list.size());
			list.clear();
		}
	}

	public void testL() {
		assertEquals((1 << (POWER-1))-1, b.size());
		assertEquals(makeNumericString(2), lex.getMin());
		for (int i=0; i < TESTS; ++i) {
			int r = random.nextInt(TESTS)+1;
			assertEquals(makeNumericString(r*2),lex.getNext(makeNumericString(r*2-1)));
		}
	}
}
