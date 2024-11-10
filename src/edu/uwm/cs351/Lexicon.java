package edu.uwm.cs351;
import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Set of strings, sorted lexicographically.
 */
public class Lexicon extends AbstractSet<String> {
	
	private static class Node {
		String string;
		Node left, right;
		Node (String s) { string = s; }
		@Override
		public String toString() { // useful for debugging
			return super.toString() + "'" + string + "'";
		}
	}
	
	private Node root;
	private int numNodes;
	private int version;
	
	private static Consumer<String> reporter = (s) -> System.out.println("Invariant error: "+ s);
	
	/**
	 * Used to report an error found when checking the invariant.
	 * By providing a string, this will help debugging the class if the invariant should fail.
	 * @param error string to print to report the exact error found
	 * @return false always
	 */
	private boolean report(String error) {
		reporter.accept(error);
		return false;
	}

	private int reportNeg(String error) {
		report(error);
		return -1;
	}

	/**
	 * Count all the nodes in this subtree, 
	 * while checking that all the keys are all in the range (lo,hi),
	 * and that the keys are arranged in BST form.
	 * If a problem is found, -1 is returned and exactly one problem is reported.
	 * <p>
	 * @param n the root of the subtree to check
	 * @param lo if non-null then all strings in the subtree rooted
	 * 				at n must be [lexicographically] greater than this parameter
	 * @param hi if non-null then all strings in the subtree rooted
	 * 				at n must be [lexicographically] less than this parameter
	 * @return number of nodes in the subtree, or -1 is there is a problem.
	 */
	private int checkInRange(Node n, String lo, String hi)
	{
		//must account for checking an empty list or leaf's links
		if (n == null) return 0;
		if (n.string == null) return reportNeg("null word found");
		
		//first check node r
		if (lo != null && (n.string.equals(lo) || n.string.compareTo(lo) < 0))
			return reportNeg("Detected node outside of low bound: "+n.string);
		if (hi != null && (n.string.equals(hi) || n.string.compareTo(hi) > 0))
			return reportNeg("Detected node outside of high bound: "+n.string);
		
		//check subtrees
		int leftSubtree =  checkInRange(n.left, lo, n.string);
		if (leftSubtree < 0) return -1;
		
		int rightSubtree = checkInRange(n.right, n.string, hi);
		if (rightSubtree < 0) return -1;
				
		//otherwise return 1 + nodes in subtrees
		return 1 + leftSubtree + rightSubtree;
	}
	
	/**
	 * Check the invariant.  
	 * Returns false if any problem is found. 
	 * @return whether invariant is currently true.
	 * If false is returned then exactly one problem has been reported.
	 */
	private boolean wellFormed() {
		int n = checkInRange(root, null, null);
		if (n < 0) return false; // problem already reported
		if (n != numNodes) return report("numNodes is " + numNodes + " but should be " + n);
		return true;
	}

	private Lexicon(boolean unused) { } // do not modify, used by Spy
	
	/**
	 * Creates an empty lexicon.
	 */
	public Lexicon() {
		root = null;
		numNodes = 0;
		assert wellFormed() : "invariant false at end of constructor";
	}
	

	@Override // required
	public int size() {
		assert wellFormed() : "invariant false at start of size()";
		return numNodes;
	}
	
	/**
	 * Gets the [lexicographically] least string in the lexicon.
	 * @return the least string or null if empty
	 */
	public String getMin() {
		assert wellFormed() : "invariant false at start of getMin()";
		// TODO: Implement this method using an iterator.
		return null;
	}
	
	/**
	 * Gets the next [lexicographically] greater string than the given string.
	 * @param str the string of which to find the next greatest
	 * @return the next string greater than str, or null if no other
	 * @throws NullPointerException if str is null
	 */
	public String getNext(String str) {
		assert wellFormed() : "invariant false at start of getNext()";
		// TODO: Implement this method using the special iterator constructor.
		// HINT: If you add "\0" to the string and look for it with the iterator, 
		// you are most of the way there.
		return null;
	}
	
	/**
	 * Accept into the consumer all strings in this lexicon.
	 * @param consumer the consumer to accept the strings
	 * @throws NullPointerException if consumer is null
	 */
	public void consumeAll(Consumer<String> consumer) {
		consumeAllWithPrefix(consumer,"");
	}
	
	/**
	 * Accept into the consumer all strings that start with the given prefix.
	 * @param consumer the consumer to accept the strings
	 * @param prefix the prefix to find all strings starting with
	 * @throws NullPointerException if consumer or prefix is null
	 */
	public void consumeAllWithPrefix(Consumer<String> consumer, String prefix) {
		assert wellFormed() : "invariant false at start of consumeAllWithPrefix()";
		if (consumer == null) throw new NullPointerException("Can't accept into null consumer");
		if (prefix == null) throw new NullPointerException("Prefix can't be null");
		// TODO: Implement this method with the special iterator, not with recursion.
	}
	
	// Do not override "toArray" -- inherited version will work fine
	// As long as you implement iterators correctly!
	
	
	/// Mutators
	
	/**
	 * Add a new string to the lexicon. If it already exists, do nothing and return false.
	 * @param str the string to add (must not be null)
	 * @return true if str was added, false otherwise
	 * @throws NullPointerException if str is null
	 */
	@Override // implementation
	public boolean add(String str) {
		assert wellFormed() : "invariant false at start of add()";
		boolean result = false;
		if (str == null) throw new NullPointerException("Cannot add null.");
		Node n = root;
		Node lag = null;
		while (n != null) {
			if (n.string.equals(str)) break;
			lag = n;
			if (str.compareTo(n.string) > 0) n = n.right;
			else n = n.left;
		}
		if (n == null) {
			n = new Node(str);
			if (lag == null)
				root = n;
			else if (str.compareTo(lag.string) > 0)
				lag.right = n;
			else
				lag.left = n;
			++numNodes;
			result = true;
			++version;
		}
		// XXX: Something is missing from this code that is needed for Homework #9
		assert wellFormed() : "invariant false at end of add()";
		return result;
	}
	
	// TODO: some efficiency overrides (and at least one recursive helper method) are needed.
	
	// The following two helper methods are used for the iterators
	// invariant checker.  Do not change.
	
	private boolean isNextGreaterAncestor(Node n, Node a) {
		Node p = a == null ? root : a.left;
		while (p != null) {
			if (n == p) return true;
			p = p.right;
		}
		return false;
	}
	
	private boolean isNext(Node p, Node n) {
		if (p.right == null) return isNextGreaterAncestor(p,n);
		else {
			p = p.right;
			while (p.left != null) p = p.left;
			return p == n;
		}
	}
	
	@Override // required
	public Iterator<String> iterator() {
		return new MyIterator();
	}
	
	/**
	 * Return an iterator that starts at the given element, or the next
	 * available element from the set.
	 * @param start starting element (or element before starting element,
	 * if the start isn't in the set), must not be null
	 * @return iterator starting "in the middle" (never null)
	 */
	public Iterator<String> iterator(String start) {
		return new MyIterator(start);
	}
	
	private class MyIterator implements Iterator<String> {
		private Stack<Node> pending = new Stack<>();
		private Node current = null; // when not null, we have a current element
		private int colVersion = version;
		
		MyIterator(boolean ignored) {} // do not change, used by Spy.
		
		private boolean wellFormed() {
			if (!Lexicon.this.wellFormed()) return false;
			if (version != colVersion) return true;
			Node prev = null;
			// stack iterator starts at BOTTOM, which is what we want!
			for (Node n : pending) {
				if (!isNextGreaterAncestor(n,prev)) return report("pending wrong: " + n + " under " + prev);
				prev = n;
			}
			if (current != null) {
				if (!isNext(current,prev)) return report("current wrong: " + current + " before " + prev);
			}
			return true;
		}
		
		private void checkVersion() {
			if (colVersion != version) {
				throw new ConcurrentModificationException("stale iterator");
			}
		}
		
		/**
		 * Start the iterator at the first (lexicographically) node.
		 */
		public MyIterator() {
			this("");
			assert wellFormed() : "Iterator messed up after default constructor";
		}
		
		/**
		 * Start the iterator at this element, or at the first element after it
		 * (if any).  		 
		 * @param initial string to start at, must not be null
		 */
		public MyIterator(String initial) {
			// TODO Set up an iterator starting with given (non-null) string.
			// NB: Do not attempt to use {@link #getNext} or any other method 
			// of the main class to help.  All the work needs to be done here 
			// so that the pending stack is set up correctly.	
			Node n = root;
			while (n != null) {
			    int cmp = initial.compareTo(n.string);
			    if (cmp < 0) {
			        pending.push(n);
			        n = n.left;
			    } else if (cmp > 0) {
			        n = n.right;
			    } else {
			        current = n;
			        break;
			    }
			}
			if (current == null && !pending.isEmpty()) {
			    do {
			        current = pending.pop();
			    } while (!pending.isEmpty() && current.string.compareTo(initial) < 0);
			    if (current.string.compareTo(initial) < 0) current = null;
			}

			if (current != null && current.right != null) {
			    for(n = current.right;n != null;n = n.left) pending.push(n);			    
			}
			assert wellFormed() : "Iterator messed up after special constructor";
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			checkVersion();
		    return current != null;
		}

		@Override
		public String next() {
			// TODO Auto-generated method stub
			checkVersion(); // Ensure modifications are detected
		    if (!hasNext()) throw new NoSuchElementException("No more elements");
		    String r = current.string;
		    if (!pending.isEmpty()) {
		        current = pending.pop();
		        if (current.right != null) {
		        	for (Node t = current.right;t != null;t = t.left) pending.push(t);
		        }
		    } else {
		        current = null;
		    }

		    return r;
		}

		// TODO: Complete the iterator class
	}
	
	/**
	 * Used for testing the invariant.  Do not change this code.
	 */
	public static class Spy {
		/**
		 * Return the sink for invariant error messages
		 * @return current reporter
		 */
		public Consumer<String> getReporter() {
			return reporter;
		}

		/**
		 * Change the sink for invariant error messages.
		 * @param r where to send invariant error messages.
		 */
		public void setReporter(Consumer<String> r) {
			reporter = r;
		}
		/**
		 * A public version of the data structure's internal node class.
		 * This class is only used for testing.
		 */
		public static class Node extends Lexicon.Node {
			// Even if Eclipse suggests it: do not add any fields to this class!
			/**
			 * Create a node with null data and null next fields.
			 */
			public Node() {
				this(null, null, null);
			}
			/**
			 * Create a node with the given values
			 * @param s data for new node, may be null
			 * @param l left for new node, may be null
			 * @param r right for new node, may be null
			 */
			public Node(String s, Node l, Node r) {
				super(null);
				this.string = s;
				this.left = l;
				this.right = r;
			}
			
			/**
			 * Change the data in the node.
			 * @param s new string to use
			 */
			public void setString(String s) {
				this.string = s;
			}
			
			/**
			 * Change a node by setting the "left" field.
			 * @param n new left field, may be null.
			 */
			public void setLeft(Node n) {
				this.left = n;
			}
			
			/**
			 * Change a node by setting the "right" field.
			 * @param n new right field, may be null.
			 */
			public void setRight(Node n) {
				this.right = n;
			}
		}

		/**
		 * Create a debugging instance of the ADT
		 * with a particular data structure.
		 * @param r root
		 * @param n num nodes
		 * @param v TODO
		 * @return a new instance of a BallSeq with the given data structure
		 */
		public Lexicon newInstance(Node r, int n, int v) {
			Lexicon result = new Lexicon(false);
			result.root = r;
			result.numNodes = n;
			result.version = v;
			return result;
		}

		/**
		 * Create a testing iterator.
		 * @param l outer lexicon object
		 * @param p stack of nodes
		 * @param c current node
		 * @param v local version
		 * @return testing instance of iterator
		 */
		@SuppressWarnings("unchecked") // doing sneaky things
		public Iterator<String> newIterator(Lexicon l, Stack<Node> p, Node c, int v) {
			MyIterator i = l.new MyIterator(false);
			i.pending = (Stack<Lexicon.Node>)(Stack<?>)p;
			i.current = c;
			i.colVersion = v;
			return i;
		}
		
		
		/**
		 * Return whether debugging instance meets the 
		 * requirements on the invariant.
		 * @param lx instance of to use, must not be null
		 * @return whether it passes the check
		 */
		public boolean wellFormed(Lexicon lx) {
			return lx.wellFormed();
		}
		
		/**
		 * Return the result of the helper method checkInRange
		 * @param n node to check for
		 * @param lo lower bound
		 * @param hi upper bound
		 * @return result of running checkInRange on a debugging instance of Lexicon
		 */
		public int checkInRange(Node n, String lo, String hi) {
			Lexicon lx = new Lexicon(false);
			lx.root = null;
			lx.numNodes = -1;
			return lx.checkInRange(n,lo,hi);
		}
		
		/**
		 * Return true if this testing iterator is well formed.
		 * @param it iterator to test, must be a testing iterator.
		 * @return whether it says it is well formed.
		 */
		public boolean wellFormed(Iterator<String> it) {
			return ((MyIterator)it).wellFormed();
		}
	}
}