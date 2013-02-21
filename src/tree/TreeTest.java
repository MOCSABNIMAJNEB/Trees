package tree;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TreeTest {
    Tree a, b, c, d, e, f, t, u, v, w, x, y, z;
    char ch;

    @Before
    public void setUp() {
	u = new Tree<String>("u");
	v = new Tree<String>("v");
	t = new Tree<String>("t", u, v);
    };

    @Test
    public void testGetValue() {
	assertEquals("t", t.getValue());
	assertEquals("u", u.getValue());
	assertEquals("v", v.getValue());
	
    };

    @Test
    public void testSetValue() {
	assertEquals("t", t.getValue());
	t.setValue("x");
	assertEquals("x", t.getValue());

    };

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddChildIndexOutOfBoundsException() {
	w = new Tree<String>("w");
	t.addChild(3,w);
    };

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddChildIndexOutOfBoundsExceptionNeg() {
	w = new Tree<String>("w");
	t.addChild(-1,w);
    };

    @Test
    public void testaddChild() {
	w = new Tree<String>("w");
	t.addChild(1, w);
	assertEquals(3, t.getNumberOfChildren());
	assertSame(u, t.getChild(0));
	assertSame(w, t.getChild(1));
	assertSame(v, t.getChild(2));
    };

    @Test(expected = IllegalArgumentException.class)
    public void testAddChildIllegalArgumentExceptionIdentity() {
	t.addChild(1, t);
    };

    @Test(expected = IllegalArgumentException.class)
    public void testAddChildIllegalArgumentException() {
	t.addChild(1, u);
    };

    @Test(expected = IllegalArgumentException.class)
    public void testAddChildIllegalArgumentExceptionDeep() {
	w = new Tree<String>("w");
	u.addChild(0,w);
	t.addChild(2,w);
    };

    @Test
    public void testAddChildren() {
	w = new Tree<String>("w");
	x = new Tree<String>("x");
	y = new Tree<String>("y");
	z = new Tree<String>("z");

	t.addChildren(w, x, y, z);
	assertEquals(6, t.getNumberOfChildren());
    };

    @Test(expected = IllegalArgumentException.class)
    public void testAddChildrenIllegalArgumentExceptionIdentity() {
	w = new Tree<String>("w");
	t.addChildren(w, t);
    };

    @Test(expected = IllegalArgumentException.class)
    public void testAddChildrenIllegalArgumentException() {
	t.addChildren(u, v);
    };

    @Test(expected = IllegalArgumentException.class)
    public void testAddChildrenIllegalArgumentExceptionDeep() {
	w = new Tree<String>("w");
	u.addChild(0,w);
	t.addChild(2,w);
    };

    @Test
    public void testGetNumberOfChildren() {
	assertEquals(2, t.getNumberOfChildren());
	assertEquals(0, u.getNumberOfChildren());
    };

    @Test
    public void testGetChild() {
	assertSame(u, t.getChild(0));
	assertSame(v, t.getChild(1));

    };

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetChildIndexOutOfBoundsException() {
	t.getChild(3);
    };

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetChildIndexOutOfBoundsExceptionNeg() {
	t.getChild(-1);
    };

    @Test
    public void testContains() {

	assertTrue(t.contains(u));
	w = new Tree<String>("w");

	u.addChild(0, w);
	assertTrue(t.contains(w));

	x = new Tree<String>("x");
	w.addChild(0, x);
	assertTrue(t.contains(x));

	y = new Tree<String>("y");
	v.addChild(0, y);
	assertTrue(t.contains(y));

	assertTrue(w.contains(x));
	assertTrue(t.contains(t));
		  
		   
    };


    @Test
    public void testToString() {
	w = new Tree<String>("w");
	u.addChild(0, w);

	y = new Tree<String>("y");
	z = new Tree<String>("z");
	w.addChild(0, y);
	v.addChild(0, z);
	assertEquals("t (u (w (y )) v (z ))", t.toString());

	a = new Tree<Integer>(1);
	b = new Tree<Integer>(2);
	c = new Tree<Integer>(3, a, b);
	assertEquals("3 (1 2 )", c.toString());
    };

    @Test
    public void testParse() {

	//Base case
	String treeString = "t (u v )";
	assertEquals(t.toString(), treeString);
	String mathString = "+ (* (2 4 ) 5 )";
	Tree<String> mathTree = Tree.parse(mathString);
	assertEquals(mathTree.toString(), mathString);
	String textyString = "dog (cat (bird (lush sparrow) fish) mouse (rat muskrat ))";
	Tree<String> textyTree = Tree.parse(textyString);
	assertEquals(textyTree.toString(), textyString);
	String single = "5";
 	Tree<String> rootTree = Tree.parse(single);
	assertTrue("5".equals(rootTree.getValue()));
	Tree<String> baseCase = Tree.parse(treeString);
	assertTrue(t.equals(baseCase));
	w = new Tree<String>("w");
	u.addChild(0, w);
	y = new Tree<String>("y");
	z = new Tree<String>("z");
	w.addChild(0, y); 
	v.addChild(0, z);
	Tree<String> thisTree = Tree.parse(t.toString());
	assertTrue(t.equals(thisTree));


	

    };

    @Test
    public void testStripSpace() {
	String treeString = " t  (u  (   w  (    y )    )   v ( z ) ) ";
	String treeStringTwo = "t (u (w (y)) v (z))";
	assertEquals("t (u ( w ( y )) v ( z ))", Tree.stripSpace(treeString));
    	assertEquals("t (u (w (y)) v (z))", Tree.stripSpace(treeStringTwo));
    };


    @Test
    public void testMatchParen() {
	String treeString = "t (u (w (y)) v (z))";
	String treeStringTwo = "t (u v)";
	assertEquals(18, Tree.matchParen(2, treeString));
	assertEquals(11, Tree.matchParen(5, treeString));
	assertEquals(6, Tree.matchParen(2, treeStringTwo));
    };

    /*
    @Test
    public void testPrint() {
	w = new Tree<String>("w");
	u.addChild(0, w);
	y = new Tree<String>("y");
	z = new Tree<String>("z");
	w.addChild(0, y); 
	v.addChild(0, z);
	a = new Tree<String>("a");
	b = new Tree<String>("b");
	c = new Tree<String>("c");
	d = new Tree<String>("d");
	t.addChild(2, a);
	a.addChild(0, b);
	a.addChild(1, c);
	a.addChild(2, d);
	System.out.println("Print: ");
	t.print();	
    };
    */

    
    @Test
    public void testEquals() {
	f = new Tree<Integer>(0);
	ch = 't';
	assertFalse(t.equals(ch));
	assertFalse(t.equals(f));

	b = new Tree<String>("u");
	assertTrue(b.equals(u));
	assertTrue(u.equals(b));
	c = new Tree<String>("v");
	a = new Tree<String>("t", b, c);
	
	assertTrue(t.equals(a));
	assertTrue(a.equals(t));
	w = new Tree<String>("w");
	d = new Tree<String>("w");
	u.addChild(0, w);
	assertFalse(t.equals(a));

	b.addChild(0, d);
	assertTrue(t.equals(a));
	assertTrue(a.equals(t));
	    
    };

    


}
