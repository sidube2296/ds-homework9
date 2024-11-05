import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;

import edu.uwm.cs351.Lexicon;


public class TestLexicon extends TestCollection<String> {
	protected <T> void assertException(Class<?> excClass, Runnable f) {
		try {
			f.run();
			assertFalse("Should have thrown an exception, not returned",true);
		} catch (RuntimeException ex) {
			if (!excClass.isInstance(ex)) {
				ex.printStackTrace();
				assertFalse("Wrong kind of exception thrown: "+ ex.getClass().getSimpleName(),true);
			}
		}		
	}

	Lexicon lex;
	String[] set, s0, s1, s2, s3, s4;

	@Override
	protected void initCollections() {
		try {
			assert lex.size() == 42;
			assertTrue("Assertions not enabled.  Add -ea to VM Args Pane in Arguments tab of Run Configuration",false);
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
		c = lex = new Lexicon();
		e = new String[] { 
				"apple", "bread", "carrot", "date", "egg", 
				"fish", "grape", "ham", "ice", "jello"
		};
		permitNulls = false; 
		permitDuplicates = false; 
		preserveOrder = false;
		s0 = new String[0];
		s1 = new String[1];
		s2 = new String[2];
		s3 = new String[3];
		s4 = new String[4];
	}
	
	
	/// text 10x: tests of add/size
	
	public void test100() {
		assertEquals(0,lex.size());
	}

	public void test101() {
		assertTrue(lex.add("apple"));
		assertEquals(1,lex.size());
	}
	
	public void test102() {
		assertTrue(lex.add("apple"));
		assertEquals(false, lex.add("apple"));
		assertEquals(1,lex.size());
	}
	
	public void test103() {
		lex.add("apple");
		assertEquals(true, lex.add("barn"));
		assertEquals(2,lex.size());
	}
	
	public void test104() {
		lex.add("apple");
		lex.add("barn");
		assertFalse(lex.add("apple"));
		assertFalse(lex.add("barn"));
	}
	
	public void test105() {
		lex.add("barn");
		assertTrue(lex.add("apple"));
		assertEquals(2,lex.size());
	}
	
	public void test106() {
		lex.add("barn");
		lex.add("apple");
		assertFalse(lex.add("barn"));
		assertFalse(lex.add("apple"));
	}
	
	public void test107() {
		lex.add("apple");
		lex.add("barn");
		lex.add("crew");
		assertEquals(3,lex.size());
		assertFalse(lex.add("apple"));
		assertFalse(lex.add("barn"));
		assertFalse(lex.add("crew"));
	}
	
	public void test108() {
		set = new String[] { "ant", "but", "he", "one", "other",
				"our", "no", "time", "up", "use"};
		
		assertTrue(lex.add(set[5]));
		assertTrue(lex.add(set[2]));
		assertTrue(lex.add(set[3]));
		assertTrue(lex.add(set[4]));
		assertTrue(lex.add(set[8]));
		assertTrue(lex.add(set[7]));
		assertTrue(lex.add(set[6]));
		assertTrue(lex.add(set[1]));
		assertTrue(lex.add(set[9]));
		assertTrue(lex.add(set[0]));
		
		for (String s: set)
			assertFalse("Should not allow duplicate: "+s, lex.add(s));
		assertEquals(10,lex.size());
	}
	
	public void test109() {
		assertException(NullPointerException.class, () -> lex.add(null));
	}

	
	
	protected String asString(Supplier<?> supp) {
		try {
			Object x = supp.get();
			return "" + x;
		} catch (RuntimeException ex) {
			return ex.getClass().getSimpleName();
		}
	}
	

	/// test11x: testing contains

	public void test110() {
		assertFalse(lex.contains(""));
	}

	public void test111() {
		lex.add("hello");
		// give string of result, or null or name of exception thrown 
		assertEquals("true", asString(() -> lex.contains("hello")));
		assertEquals("false", asString(() -> lex.contains("he")));
		assertEquals("false", asString(() -> lex.contains(null)));
	}

	public void test112() {
		lex.add("other");
		lex.add("time");
		assertFalse(lex.contains("ant"));
		assertFalse(lex.contains("buy"));
		assertFalse(lex.contains("he"));
		assertFalse(lex.contains("no"));
		assertFalse(lex.contains("one"));
		assertTrue(lex.contains("other"));
		assertFalse(lex.contains("our"));
		assertTrue(lex.contains("time"));
		assertFalse(lex.contains("up"));
		assertFalse(lex.contains("use"));
	}

	public void test113() {
		lex.add("other");
		lex.add("time");
		lex.add("he");
		assertFalse(lex.contains("ant"));
		assertFalse(lex.contains("buy"));
		assertTrue(lex.contains("he"));
		assertFalse(lex.contains("no"));
		assertFalse(lex.contains("one"));
		assertTrue(lex.contains("other"));
		assertFalse(lex.contains("our"));
		assertTrue(lex.contains("time"));
		assertFalse(lex.contains("up"));
		assertFalse(lex.contains("use"));
	}

	public void test114() {
		lex.add("other");
		lex.add("time");
		lex.add("he");
		lex.add("our");
		assertFalse(lex.contains("ant"));
		assertFalse(lex.contains("buy"));
		assertTrue(lex.contains("he"));
		assertFalse(lex.contains("no"));
		assertFalse(lex.contains("one"));
		assertTrue(lex.contains("other"));
		assertTrue(lex.contains("our"));
		assertTrue(lex.contains("time"));
		assertFalse(lex.contains("up"));
		assertFalse(lex.contains("use"));
	}

	public void test115() {
		lex.add("other");
		lex.add("time");
		lex.add("he");
		lex.add("our");
		lex.add("buy");
		assertFalse(lex.contains("ant"));
		assertTrue(lex.contains("buy"));
		assertTrue(lex.contains("he"));
		assertFalse(lex.contains("no"));
		assertFalse(lex.contains("one"));
		assertTrue(lex.contains("other"));
		assertTrue(lex.contains("our"));
		assertTrue(lex.contains("time"));
		assertFalse(lex.contains("up"));
		assertFalse(lex.contains("use"));
	}

	public void test116() {
		lex.add("other");
		lex.add("time");
		lex.add("he");
		lex.add("our");
		lex.add("buy");
		lex.add("use");
		assertFalse(lex.contains("ant"));
		assertTrue(lex.contains("buy"));
		assertTrue(lex.contains("he"));
		assertFalse(lex.contains("no"));
		assertFalse(lex.contains("one"));
		assertTrue(lex.contains("other"));
		assertTrue(lex.contains("our"));
		assertTrue(lex.contains("time"));
		assertFalse(lex.contains("up"));
		assertTrue(lex.contains("use"));
	}

	public void test117() {
		lex.add("other");
		lex.add("time");
		lex.add("he");
		lex.add("our");
		lex.add("buy");
		lex.add("use");
		lex.add("one");
		assertFalse(lex.contains("ant"));
		assertTrue(lex.contains("buy"));
		assertTrue(lex.contains("he"));
		assertFalse(lex.contains("no"));
		assertTrue(lex.contains("one"));
		assertTrue(lex.contains("other"));
		assertTrue(lex.contains("our"));
		assertTrue(lex.contains("time"));
		assertFalse(lex.contains("up"));
		assertTrue(lex.contains("use"));
	}

	public void test118() {
		lex.add("other");
		lex.add("time");
		lex.add("he");
		lex.add("our");
		lex.add("buy");
		lex.add("use");
		lex.add("one");
		lex.add("no");
		assertFalse(lex.contains("ant"));
		assertTrue(lex.contains("buy"));
		assertTrue(lex.contains("he"));
		assertTrue(lex.contains("no"));
		assertTrue(lex.contains("one"));
		assertTrue(lex.contains("other"));
		assertTrue(lex.contains("our"));
		assertTrue(lex.contains("time"));
		assertFalse(lex.contains("up"));
		assertTrue(lex.contains("use"));
	}

	public void test119() {
		lex.add("other");
		lex.add("time");
		lex.add("he");
		lex.add("our");
		lex.add("buy");
		lex.add("use");
		lex.add("one");
		lex.add("no");
		lex.add("ant");
		assertTrue(lex.contains("ant"));
		assertTrue(lex.contains("buy"));
		assertTrue(lex.contains("he"));
		assertTrue(lex.contains("no"));
		assertTrue(lex.contains("one"));
		assertTrue(lex.contains("other"));
		assertTrue(lex.contains("our"));
		assertTrue(lex.contains("time"));
		assertFalse(lex.contains("up"));
		assertTrue(lex.contains("use"));
	}

	
	/// test12x: testing getMin
	
	public void test120() {
		//nothing added yet: result is string, or "null" or name of exception
		assertEquals("null", asString(() -> lex.getMin()));
	}
	
	public void test121() {
		lex.add("hello");
		assertEquals("hello", asString(() -> lex.getMin()));
	}
	
	public void test122() {
		lex.add("in");
		lex.add("website");
		assertEquals("in",lex.getMin());
	}

	public void test123() {
		lex.add("in");
		lex.add("website");
		lex.add("check");
		assertEquals("check",lex.getMin());
	}

	public void test124() {
		lex.add("in");
		lex.add("website");
		lex.add("check");
		lex.add("show");
		assertEquals("check",lex.getMin());		
	}
	
	public void test125() {
		lex.add("in");
		lex.add("website");
		lex.add("check");
		lex.add("show");
		lex.add("code");
		assertEquals("check",lex.getMin());
	}
	
	public void test126() {
		lex.add("in");
		lex.add("website");
		lex.add("check");
		lex.add("show");
		lex.add("code");
		lex.add("based");
		assertEquals("based",lex.getMin());		
	}

	public void test127() {
		lex.add("in");
		lex.add("website");
		lex.add("check");
		lex.add("show");
		lex.add("code");
		lex.add("based");
		lex.add("being");
		assertEquals("based",lex.getMin());
	}

	public void test128() {
		lex.add("in");
		lex.add("website");
		lex.add("check");
		lex.add("show");
		lex.add("code");
		lex.add("based");
		lex.add("being");
		lex.add("blank");
		assertEquals("based",lex.getMin());
	}
	
	public void test129() {		
		lex.add("in");
		lex.add("website");
		lex.add("check");
		lex.add("show");
		lex.add("code");
		lex.add("based");
		lex.add("being");
		lex.add("blank");
		lex.add("anchor");
		assertEquals("anchor",lex.getMin());
	}
	
	
	/// test13x: testing getNext
	
	private void testGetNext(String message, String expectedWord, String word) {
		assertEquals(message, expectedWord,lex.getNext(word));
	}

	public void test130() {
		lex.add("hello");
		assertEquals("hello", lex.getNext(""));
	}
	
	public void test131() {
		lex.add("HI");
		assertEquals(null, lex.getNext("HI"));
	}
	
	public void test132() {
		set = new String[] { "but", "hex", "up", "down", "user"};
		
		for (String s: set)
			lex.add(s);
		
		assertEquals("but", lex.getNext("burn"));
		assertEquals("but", lex.getNext("bu"));
		assertEquals("down", lex.getNext("button"));
		assertEquals("but", lex.getNext(""));
		assertEquals("up", lex.getNext("under"));
		assertNull(lex.getNext("users"));
	}
	
	public void test137() {
		assertException(NullPointerException.class, () -> lex.getNext(null));
	}


	public void test138() {
		lex.add("good");
		assertEquals("good", lex.getNext("World"));
	}
	
	public void test139() {
		lex.add("Hello");
		assertEquals(null, lex.getNext("bye"));
	}
	
	
	/// test14x/15x: tests of getNext and min
	
	public void test140() {
		testGetNext("[].next(a)",null,"a");
	}

	public void test141() {
		lex.add("g");
		testGetNext("[g].next(a)","g","a");
		testGetNext("[g].next(gx)",null,"gx");
		testGetNext("[g].next(fx)","g","fx");
		testGetNext("[g].next(g)",null,"g");
	}

	public void test142() {
		lex.add("g");
		lex.add("i");
		testGetNext("[g,i].next(i)",null,"i");
		testGetNext("[g,i].next(hx)","i","hx");
		testGetNext("[g,i].next(h)","i","h");
		testGetNext("[g,i].next(g)","i","g");
		testGetNext("[g,i].next(fx)","g","fx");
	}

	public void test143() {
		lex.add("g");
		lex.add("i");
		lex.add("e");
		testGetNext("[e,g,i].next(a)","e","a");
		testGetNext("[e,g,i].next(hx)","i","hx");
		testGetNext("[e,g,i].next(g)","i","g");
		testGetNext("[e,g,i].next(fx)","g","fx");
		testGetNext("[e,g,i].next(e)","g","e");
		testGetNext("[e,g,i].next(dx)","e","dx");
		testGetNext("[e,g,i].next(d)","e","d");
		testGetNext("[e,g,i].next(j)",null,"j");
	}

	public void test144() {
		lex.add("g");
		lex.add("i");
		lex.add("e");
		lex.add("h");
		testGetNext("[e,g,h,i].next(a)","e","a");
		testGetNext("[e,g,h,i].next(j)",null,"j");
		testGetNext("[e,g,h,i].next(i)",null,"i");
		testGetNext("[e,g,h,i].next(h)","i","h");
		testGetNext("[e,g,h,i].next(gx)","h","gx");
		testGetNext("[e,g,h,i].next(g)","h","g");
		testGetNext("[e,g,h,i].next(fx)","g","fx");
		testGetNext("[e,g,h,i].next(e)","g","e");
		testGetNext("[e,g,h,i].next(dx)","e","dx");
	}

	public void test145() {
		lex.add("g");
		lex.add("i");
		lex.add("e");
		lex.add("h");
		lex.add("f");
		testGetNext("[e,f,g,h,i].next(a)","e","a");
		testGetNext("[e,f,g,h,i].next(j)",null,"j");
		testGetNext("[e,f,g,h,i].next(i)",null,"i");
		testGetNext("[e,f,g,h,i].next(h)","i","h");
		testGetNext("[e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[e,f,g,h,i].next(g)","h","g");
		testGetNext("[e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[e,f,g,h,i].next(f)","g","f");
		testGetNext("[e,f,g,h,i].next(e)","f","e");
	}

	public void test146() {
		lex.add("g");
		lex.add("i");
		lex.add("e");
		lex.add("h");
		lex.add("f");
		lex.add("b");
		testGetNext("[b,e,f,g,h,i].next(a)","b","a");
		testGetNext("[b,e,f,g,h,i].next(ax)","b","ax");
		testGetNext("[b,e,f,g,h,i].next(b)","e","b");
		testGetNext("[b,e,f,g,h,i].next(bx)","e","bx");
		testGetNext("[b,e,f,g,h,i].next(dx)","e","dx");
		testGetNext("[b,e,f,g,h,i].next(e)","f","e");
		testGetNext("[b,e,f,g,h,i].next(ex)","f","ex");
		testGetNext("[b,e,f,g,h,i].next(f)","g","f");
		testGetNext("[b,e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[b,e,f,g,h,i].next(g)","h","g");
		testGetNext("[b,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,e,f,g,h,i].next(h)","i","h");
		testGetNext("[b,e,f,g,h,i].next(hx)","i","hx");
		testGetNext("[b,e,f,g,h,i].next(i)",null,"i");
	}
	
	public void test147() {
		lex.add("g");
		lex.add("i");
		lex.add("e");
		lex.add("h");
		lex.add("f");
		lex.add("b");
		lex.add("c");
		testGetNext("[b,c,e,f,g,h,i].next(a)","b","a");
		testGetNext("[b,c,e,f,g,h,i].next(ax)","b","ax");
		testGetNext("[b,c,e,f,g,h,i].next(b)","c","b");
		testGetNext("[b,c,e,f,g,h,i].next(bx)","c","bx");
		testGetNext("[b,c,e,f,g,h,i].next(c)","e","c");
		testGetNext("[b,c,e,f,g,h,i].next(cx)","e","cx");
		testGetNext("[b,c,e,f,g,h,i].next(dx)","e","dx");
		testGetNext("[b,c,e,f,g,h,i].next(e)","f","e");
		testGetNext("[b,c,e,f,g,h,i].next(ex)","f","ex");
		testGetNext("[b,c,e,f,g,h,i].next(f)","g","f");
		testGetNext("[b,c,e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[b,c,e,f,g,h,i].next(g)","h","g");
		testGetNext("[b,c,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,c,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,c,e,f,g,h,i].next(h)","i","h");
		testGetNext("[b,c,e,f,g,h,i].next(hx)","i","hx");
		testGetNext("[b,c,e,f,g,h,i].next(i)",null,"i");
	}

	public void test148() {
		lex.add("g");
		lex.add("i");
		lex.add("e");
		lex.add("h");
		lex.add("f");
		lex.add("b");
		lex.add("c");
		lex.add("d");
		testGetNext("[b,c,d,e,f,g,h,i].next(a)","b","a");
		testGetNext("[b,c,d,e,f,g,h,i].next(ax)","b","ax");
		testGetNext("[b,c,d,e,f,g,h,i].next(b)","c","b");
		testGetNext("[b,c,d,e,f,g,h,i].next(bx)","c","bx");
		testGetNext("[b,c,d,e,f,g,h,i].next(c)","d","c");
		testGetNext("[b,c,d,e,f,g,h,i].next(cx)","d","cx");
		testGetNext("[b,c,d,e,f,g,h,i].next(d)","e","d");
		testGetNext("[b,c,d,e,f,g,h,i].next(dx)","e","dx");
		testGetNext("[b,c,d,e,f,g,h,i].next(e)","f","e");
		testGetNext("[b,c,d,e,f,g,h,i].next(ex)","f","ex");
		testGetNext("[b,c,d,e,f,g,h,i].next(f)","g","f");
		testGetNext("[b,c,d,e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[b,c,d,e,f,g,h,i].next(g)","h","g");
		testGetNext("[b,c,d,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,c,d,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[b,c,d,e,f,g,h,i].next(h)","i","h");
		testGetNext("[b,c,d,e,f,g,h,i].next(hx)","i","hx");
		testGetNext("[b,c,d,e,f,g,h,i].next(i)",null,"i");
	}
	
	public void test149() {
		lex.add("g");
		lex.add("i");
		lex.add("e");
		lex.add("h");
		lex.add("f");
		lex.add("b");
		lex.add("c");
		lex.add("d");
		lex.add("a");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(a)","b","a");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(ax)","b","ax");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(b)","c","b");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(bx)","c","bx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(c)","d","c");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(cx)","d","cx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(d)","e","d");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(dx)","e","dx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(e)","f","e");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(ex)","f","ex");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(f)","g","f");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(fx)","g","fx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(g)","h","g");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(gx)","h","gx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(h)","i","h");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(hx)","i","hx");
		testGetNext("[a,b,c,d,e,f,g,h,i].next(i)",null,"i");
		testGetNext("[a,b,c,d,e,f,g,h,i].next('')","a","");
	}
	
	public void test152() {
		assertEquals("[].getMin()",null,lex.getMin());
		testGetNext("[].next(j)",null,"j");
	}
	
	public void test153() {
		lex.add("j");
		assertEquals("[j].getMin()","j",lex.getMin());
		testGetNext("[j].next(a)","j","a");//
		testGetNext("[j].next(i)","j","i");
		testGetNext("[j].next(j)",null,"j");
		testGetNext("[j].next(t)",null,"t");
	}

	public void test154() {
		lex.add("j");
		lex.add("e");
		assertEquals("[e,j].getMin()","e",lex.getMin());
		testGetNext("[e,j].next(a)","e","a");
		testGetNext("[e,j].next(e)","j","e");//
		testGetNext("[e,j].next(i)","j","i");
		testGetNext("[e,j].next(j)",null,"j");
		testGetNext("[e,j].next(t)",null,"t");
	}

	public void test155() {
		lex.add("j");
		lex.add("e");		
		lex.add("z");
		assertEquals("[e,j,z].getMin()","e",lex.getMin());
		testGetNext("[e,j,z].next(a)","e","a");
		testGetNext("[e,j,z].next(e)","j","e");
		testGetNext("[e,j,z].next(i)","j","i");
		testGetNext("[e,j,z].next(j)","z","j");
		testGetNext("[e,j,z].next(t)","z","t");
		testGetNext("[e,j,z].next(z)",null,"z");
	}

	public void test156() {
		lex.add("j");
		lex.add("e");
		lex.add("z");
		lex.add("g");
		assertEquals("[e,g,j,z].getMin()","e",lex.getMin());
		testGetNext("[e,g,j,z].next(a)","e","a");
		testGetNext("[e,g,j,z].next(e)","g","e");
		testGetNext("[e,g,j,z].next(g)","j","g");
		testGetNext("[e,g,j,z].next(i)","j","i");
		testGetNext("[e,g,j,z].next(j)","z","j");
		testGetNext("[e,g,j,z].next(t)","z","t");
		testGetNext("[e,g,j,z].next(z)",null,"z");
	}

	public void test157() {
		lex.add("j");
		lex.add("e");
		lex.add("z");
		lex.add("g");
		lex.add("h");
		assertEquals("[e,g,h,j,z].getMin()","e",lex.getMin());
		testGetNext("[e,g,h,j,z].next(a)","e","a");
		testGetNext("[e,g,h,j,z].next(e)","g","e");
		testGetNext("[e,g,h,j,z].next(g)","h","g");
		testGetNext("[e,g,h,j,z].next(i)","j","i");
		testGetNext("[e,g,h,j,z].next(j)","z","j");
		testGetNext("[e,g,h,j,z].next(t)","z","t");
		testGetNext("[e,g,h,j,z].next(z)",null,"z");
	}

	public void test158() {
		lex.add("j");		
		lex.add("e");
		lex.add("z");
		lex.add("g");
		lex.add("h");
		lex.add("c");
		assertEquals("[c,e,g,h,j,z].getMin()","c",lex.getMin());
		testGetNext("[c,e,g,h,j,z].next(a)","c","a");
		testGetNext("[c,e,g,h,j,z].next(c)","e","c");
		testGetNext("[c,e,g,h,j,z].next(e)","g","e");
		testGetNext("[c,e,g,h,j,z].next(g)","h","g");
		testGetNext("[c,e,g,h,j,z].next(i)","j","i");
		testGetNext("[c,e,g,h,j,z].next(j)","z","j");
		testGetNext("[c,e,g,h,j,z].next(t)","z","t");
		testGetNext("[c,e,g,h,j,z].next(z)",null,"z");
	}

	public void test159() {
		lex.add("j");
		lex.add("e");
		lex.add("z");
		lex.add("g");
		lex.add("h");
		lex.add("c");
		lex.add("t");
		assertEquals("[c,e,g,h,j,t,z].getMin()","c",lex.getMin());
		testGetNext("[c,e,g,h,j,t,z].next(a)","c","a");
		testGetNext("[c,e,g,h,j,t,z].next(c)","e","c");
		testGetNext("[c,e,g,h,j,t,z].next(e)","g","e");
		testGetNext("[c,e,g,h,j,t,z].next(g)","h","g");
		testGetNext("[c,e,g,h,j,t,z].next(i)","j","i");
		testGetNext("[c,e,g,h,j,t,z].next(j)","t","j");
		testGetNext("[c,e,g,h,j,t,z].next(t)","z","t");
		testGetNext("[c,e,g,h,j,t,z].next(z)",null,"z");
	}

	
	/// test16x: tests of consumeAll
	
	public void test160() {
		testConsumeAll(new String[] {"atoll","attention", "boat"},"at",new String[] {"atoll","attention"});
	}
	
	public void test161() {		
		testConsumeAll(new String[] {"attention","boat", "atoll"}, "at", new String[] {"atoll","attention"});
	}
	
	public void test162() {		
		testConsumeAll(new String[] {"army","armor", "armistice", "artwork"}, "ar", new String[] {"armistice","armor","army","artwork"});
	}
	
	public void test163() {		
		testConsumeAll(new String[] {"a","aa", "aaa", "aaaa"}, "a", new String[] {"a","aa", "aaa", "aaaa"});
	}
	
	public void test164() {		
		testConsumeAll(new String[] {}, "a", new String[] {});
	}
	
	public void test165() {		
		testConsumeAll(new String[] {"a","b","a","c","dog"}, "", new String[] {"a","b","c","dog"});
	}
	
	public void test166() {		
		testConsumeAll(new String[] {"a","b","a","c","dog"}, "d", new String[] {"dog"});
	}
	
	public void test167() {		
		testConsumeAll(new String[] {"a","b","a","c","dog"}, "none", new String[] {});
	}
	
	public void test168() {		
		testConsumeAll(new String[] { "landlord", "landfill", "label", "lady", "last", "lake", "land", 
				"landing", "labor", "lamp", "lane", "large"}, "lan", new String[] {"land", "landfill","landing", "landlord","lane"});
	}
	
	public void test169() {
		assertException(NullPointerException.class, () -> lex.consumeAllWithPrefix(null, "blah"));
		assertException(NullPointerException.class, () -> lex.consumeAllWithPrefix(s -> {}, null));
	}

	private void testConsumeAll(String[] set, String prefix, String[] expected) {
		lex = new Lexicon();
		for (String s: set)
			lex.add(s);
		
		ArrayList<String> list = new ArrayList<>();
		lex.consumeAllWithPrefix(str -> list.add(str), prefix);

		assertEquals("incorrect amount of strings consumed", expected.length, list.size());
		for (int i = 0; i < expected.length; i++)
			assertEquals(expected[i], list.get(i));
	}
	

	/// test17x: tests of toArray
	
	public void test170() {
		assertSame(s0, lex.toArray(s0));
	}
		
	public void test171() {
		lex.add("hat");
		set = lex.toArray(s0);
		assertEquals(1,set.length);
		assertEquals("hat",set[0]);
	}
	
	public void test172() {
		lex.add("hat");		
		assertEquals(s1,lex.toArray(s1));
		assertEquals("hat",s1[0]);
	}
	
	public void test173() {
		lex.add("hat");
		s3[0] = null;
		s3[1] = "arm";
		s3[2] = "arm";
		assertEquals(s3,lex.toArray(s3));
		assertEquals("hat",s3[0]);
		assertEquals(null,s3[1]);
		assertEquals("arm",s3[2]);
	}
	
	public void test174() {
		lex.add("zoo");
		lex.add("dome");

		set = lex.toArray(s0);
		assertEquals(2,set.length);
		assertEquals("dome",set[0]);
		assertEquals("zoo",set[1]);		
	}
	
	public void test175() {
		lex.add("zoo");
		lex.add("dome");

		s1[0] = "loan";
		set = lex.toArray(s1);
		assertEquals(2,set.length);
		assertEquals("dome",set[0]);
		assertEquals("zoo",set[1]);
		assertEquals("loan",s1[0]);		
	}
	
	public void test176() {
		lex.add("zoo");
		lex.add("dome");
		
		s2[1] = "loan";
		assertEquals(s2,lex.toArray(s2));
		assertEquals("dome",s2[0]);
		assertEquals("zoo",s2[1]);
	}
	
	public void test177() {
		lex.add("zoo");
		lex.add("dome");
		
		s3[1] = "pole";
		assertEquals(s3,lex.toArray(s3));
		assertEquals("dome",s3[0]);
		assertEquals("zoo",s3[1]);
		assertNull(s3[2]);
	}
		
	public void test178() {
		lex.add("quilt");
		lex.add("willow");
		lex.add("ours");
		lex.add("wagon");
		lex.add("peers");
		lex.add("mounts");
		lex.add("neon");
		lex.add("optimum");
		lex.add("lone");

		set = lex.toArray(s4);
		assertEquals(9,set.length);
		assertEquals("lone",set[0]);
		assertEquals("mounts",set[1]);
		assertEquals("neon",set[2]);
		assertEquals("optimum",set[3]);
		assertEquals("ours",set[4]);
		assertEquals("peers",set[5]);
		assertEquals("quilt",set[6]);
		assertEquals("wagon",set[7]);
		assertEquals("willow",set[8]);		
	}
	
	public void test179() {
		lex.add("quilt");
		lex.add("willow");
		lex.add("ours");
		lex.add("wagon");
		lex.add("peers");
		lex.add("mounts");
		lex.add("neon");
		lex.add("optimum");
		lex.add("lone");
		
		String[] s9 = lex.toArray(s0);
		
		set = lex.toArray(s0);
		assertTrue(set != s9);// don't reuse!
		
		assertEquals(9,set.length);
		assertEquals("lone",set[0]);
		assertEquals("mounts",set[1]);
		assertEquals("neon",set[2]);
		assertEquals("optimum",set[3]);
		assertEquals("ours",set[4]);
		assertEquals("peers",set[5]);
		assertEquals("quilt",set[6]);
		assertEquals("wagon",set[7]);
		assertEquals("willow",set[8]);
	}
	
	
	/// test18x/19x: tests of iterator(String)
	
	public void test180() {
		it = lex.iterator("cat");
		assertFalse(it.hasNext());
	}
	
	public void test181() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		Iterator<String> it = lex.iterator("70");
		assertEquals("73",it.next());
		assertEquals("75",it.next());
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test182() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("73");
		assertEquals("73",it.next());
		assertEquals("75",it.next());
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test183() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("74");
		assertEquals("75",it.next());
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test184() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");

		it = lex.iterator("75");
		assertEquals("75",it.next());
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test185() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("76");
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test186() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("77");
		assertEquals("77",it.next());
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test187() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
	
		it = lex.iterator("78");
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test188() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("79");
		assertEquals("79",it.next());
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test189() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("80");
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test190() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("81");
		assertEquals("81",it.next());
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test191() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("82");
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test192() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("83");
		assertEquals("83",it.next());
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test193() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("84");
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test194() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("85");
		assertEquals("85",it.next());
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test195() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("86");
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test196() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("87");
		assertEquals("87",it.next());
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test197() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("88");
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test198() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("89");
		assertEquals("89",it.next());
		assertFalse(it.hasNext());
	}
	
	public void test199() {
		lex.add("85");
		lex.add("89");
		lex.add("81");
		lex.add("87");
		lex.add("83");
		lex.add("75");
		lex.add("77");
		lex.add("79");
		lex.add("73");
		
		it = lex.iterator("90");
		assertFalse(it.hasNext());
	}

}
