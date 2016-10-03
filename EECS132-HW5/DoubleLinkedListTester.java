import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

/**
 * A class that tests the methods of the DoubleLinkedList class.
 */
public class DoubleLinkedListTester {
  
  /**
   * Tests the addToFront method of DoubleLinkedList.
   */
  @Test
  public void testAddToFront() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(3);
    list.addToFront(2);
    list.addToFront(1);
    DLNode<Integer> head = list.getFront();
    DLNode<Integer> tail = list.getBack();
    
    assertEquals("Testing first node of list", new Integer(1), head.getElement());
    assertEquals("Testing second node of list", new Integer(2), head.getNext().getElement());
    assertEquals("Testing third node of list", new Integer(3), head.getNext().getNext().getElement());
    assertEquals("Testing end of list", null, head.getNext().getNext().getNext());
    
    assertEquals("Testing node at back of list", new Integer(3), tail.getElement());
    assertEquals("Testing next to last node", new Integer(2), tail.getPrevious().getElement());
    assertEquals("Testing third to last node", new Integer(1), tail.getPrevious().getPrevious().getElement());
    assertEquals("Testing front of list", null, tail.getPrevious().getPrevious().getPrevious());
  }

  /**
   * Tests the addToBack method of DoubleLinkedList.
   */
  @Test
  public void testAddToBack() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToBack(1);
    list.addToBack(2);
    list.addToBack(3);
    DLNode<Integer> head = list.getFront();
    DLNode<Integer> tail = list.getBack();
      
    assertEquals("Testing last node of list", new Integer(3), tail.getElement());
    assertEquals("Testing next to last node", new Integer(2), tail.getPrevious().getElement());
    assertEquals("Testing third to last node", new Integer(1), tail.getPrevious().getPrevious().getElement());
    assertEquals("Testing front of list", null, tail.getPrevious().getPrevious().getPrevious());
    
    assertEquals("Testing node at front of list", new Integer(1), head.getElement());
    assertEquals("Testing second node of list", new Integer(2), head.getNext().getElement());
    assertEquals("Testing third node of list", new Integer(3), head.getNext().getNext().getElement());
    assertEquals("Testing end of list", null, head.getNext().getNext().getNext());
  }
  
  /**
   * Tests the removeFromFront method of DoubleLinkedList.
   */
  @Test
  public void testRemoveFromFront() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToFront(1);
    list.addToFront(2);
    list.addToFront(3);
    assertEquals("Removing element of list", new Integer(3), list.removeFromFront());
    assertEquals("Removing a second element", new Integer(2), list.removeFromFront());
    assertEquals("Removing a third element", new Integer(1), list.removeFromFront());
    assertEquals("Removed last element of list", true, list.isEmpty());
    try {
      list.removeFromFront();
      fail("Removing from empty list did not throw an exception.");
    }
    catch (java.util.NoSuchElementException e) {
      /* everything is good */
    }
    catch (Exception e) {
      fail("Removing from empty list threw the wrong type of exception.");
    }
    
    list.addToBack(6);
    list.addToBack(7);
    assertEquals("Removing element added to back of list", new Integer(6), list.removeFromFront());
    assertEquals("Removing second element added to back", new Integer(7), list.removeFromFront());
  }
  
  /**
   * Tests the removeFromBack method of DoubleLinkedList.
   */
  @Test
  public void testRemoveFromBack() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    list.addToBack(5);
    list.addToFront(4);
    list.addToBack(6);
    assertEquals("Removing element from back of list", new Integer(6), list.removeFromBack());
    assertEquals("Removing second element from back of list", new Integer(5), list.removeFromBack());
    assertEquals("Removing element from back that was added to front", new Integer(4), list.removeFromBack());
    assertEquals("Removing last element of list", true, list.isEmpty());
    try {
      list.removeFromBack();
      fail("Removing from empty list did not throw an exception.");
    }
    catch (java.util.NoSuchElementException e) {
      /* everything is good */
    }
    catch (Exception e) {
      fail("Removing from empty list threw the wrong type of exception.");
    }
  }
  
  /**
   * Tests the linked list iterator.
   */
  @Test
  public void testListIterator() {
    DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
    for (int i = 5; i > 0; i--)
      list.addToFront(i);
    
    /* checking that we get out what we put it */
    int i = 1;
    for (Integer x: list)
      assertEquals("Testing value returned by iterator", new Integer(i++), x);
    
    if (i != 6)
      fail("The iterator did not run through all the elements of the list");
  }
  
  /**
   * Tests the remove feature of the linked list iterator.
   */
  @Test
  public void testListIteratorAdd() {
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    ListIterator<Integer> list1It = list1.iterator();
    list1It.add(1);
    assertEquals("Testing add to an empty list", new Integer(1), list1.getBack().getElement());
    list1It.add(2);
    assertEquals("Testing add to list with one element", new Integer(2), list1.getBack().getElement());
    //move cursor back two spaces
    list1It.previous();
    list1It.previous();
    list1It.next();
    list1It.add(3);
    assertEquals("Testing add to list with two elements", new Integer(3), list1.getFront().getNext().getElement());
  }
  
  /** tests the hasNext method of the linked list iterator */
  @Test
  public void testListIteratorHasNext(){
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    ListIterator<Integer> list1It = list1.iterator();
    assertFalse("testing with no next element", list1It.hasNext());
    list1It.add(1);
    list1It.previous();
    assertTrue("testing with a next element", list1It.hasNext());
    assertEquals(new Integer(1), list1It.next());
  }
  
  /** tests the has previous method of the linked list iterartor */
  @Test
  public void testListIteratorHasPrevious(){
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    ListIterator<Integer> list1It = list1.iterator();
    assertFalse("testing with no previous element", list1It.hasPrevious());
    list1It.add(1);
    assertTrue("testing with a previous element", list1It.hasPrevious());
  }
  
  /** tests the next method of the linked list iterator */
  @Test
  public void testListIteratorNext(){
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    ListIterator<Integer> list1It = list1.iterator();
    try{
      list1It.next();
      fail("expected a NoSuchElementException to be thrown");
    }
    catch(NoSuchElementException error){
      assertEquals(error.getMessage(), "There is no next element in the list");
    }
    list1It.add(1);
    list1It.previous();
    assertEquals("testing with an element in the next spot", new Integer(1), list1It.next());
  }
  
  /** tests the previous method of the linked list iterator */
  @Test
  public void testListIteratorPrevious(){
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    ListIterator<Integer> list1It = list1.iterator();
    try{
      list1It.previous();
      fail("expected a NoSuchElementException to be thrown");
    }
    catch(NoSuchElementException error){
      assertEquals(error.getMessage(), "There is no previous element in the list");
    }
    list1It.add(1);
    assertEquals("testing with an element in the next spot", new Integer(1), list1It.previous());
  }
  
  /** tests the set method of the linked list iterator */
  @Test
  public void testListIteratorSet(){
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    for (int i = 5; i > 0; i--)
      list1.addToFront(i);
    ListIterator<Integer> list1It = list1.iterator();
    try{
      list1It.set(99);
      fail("expected IllegalStateException to be thrown");
    }
    catch(IllegalStateException exception){}
    assertEquals(new Integer(1), list1It.next());
    list1It.set(91);
    assertEquals("testing set after calling next()", new Integer(91), list1It.previous());
    assertEquals(new Integer(91), list1It.next());
    assertEquals(new Integer(2), list1It.next());
    assertEquals(new Integer(3), list1It.next());
    assertEquals(new Integer(3), list1It.previous());
    list1It.set(93);
    assertEquals("testing set after calling previous()", new Integer(93), list1It.next());
    list1It.add(4);
    try{
      list1It.set(94);
      fail("expected IllegalStateException to be thrown");
    }
    catch(IllegalStateException exception){}
  }
  
  /** tests the equals method of DoubleLinkedList */
  @Test
  public void testEquals(){
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> list2 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> list3 = new DoubleLinkedList<Integer>();
    //builing of lists
    list1.addToFront(1);
    list1.addToFront(2);
    list1.addToFront(3);
    list2.addToFront(1);
    list2.addToFront(2);
    list2.addToFront(3);
    list3.addToFront(3);
    list3.addToFront(2);
    list3.addToFront(1);
    assertTrue("testing with two equal lists", list2.equals(list1));
    assertFalse("testing with two unequal lists", list2.equals(list3));
    assertFalse("testing with one list and one string", list1.equals("Hello"));
  }
  
  /** tests the append method of DoubleLinkedList */
  @Test
  public void testAppends(){
    DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> list2 = new DoubleLinkedList<Integer>();
    DoubleLinkedList<Integer> list3 = new DoubleLinkedList<Integer>();
    //builing of lists
    list1.addToFront(1);
    list1.addToFront(2);
    list1.addToFront(3);
    list2.addToFront(4);
    list2.addToFront(5);
    list2.addToFront(6);
    list3.addToFront(4);
    list3.addToFront(5);
    list3.addToFront(6);
    list3.addToFront(1);
    list3.addToFront(2);
    list3.addToFront(3);
    list1.append(list2);
    assertTrue("testing append with two lists", list1.equals(list3));
  }
}