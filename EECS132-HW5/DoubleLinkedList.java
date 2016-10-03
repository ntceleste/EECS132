import java.util.*;

/**
 * A doubly linked linked list.
 * @author Nate Celeste
 * 
 */
public class DoubleLinkedList<T> implements Iterable<T>{
  /** a reference to the first node of the double linked list */
  private DLNode<T> front;
  
  /** a reference to the last node of a double linked list */
  private DLNode<T> back;
  
  /** Create an empty double linked list. */
  public DoubleLinkedList() {
    front = back = null;
  }
  
  /** 
   * Returns true if the list is empty.
   * @return  true if the list has no nodes
   */
  public boolean isEmpty() {
    return (getFront() == null);
  }
  
  /**
   * Returns the reference to the first node of the linked list.
   * @return the first node of the linked list
   */
  protected DLNode<T> getFront() {
    return front;
  }
  
  /**
   * Sets the first node of the linked list.
   * @param node  the node that will be the head of the linked list.
   */
  protected void setFront(DLNode<T> node) {
    front = node;
  }
  
  /**
   * Returns the reference to the last node of the linked list.
   * @return the last node of the linked list
   */
  protected DLNode<T> getBack() {
    return back;
  }
  
  /**
   * Sets the last node of the linked list.
   * @param node the node that will be the last node of the linked list
   */
  protected void setBack(DLNode<T> node) {
    back = node;
  }
  
  /**
   * Add an element to the head of the linked list.
   * @param element  the element to add to the front of the linked list
   */
  public void addToFront(T element) {
    DLNode<T> add = new DLNode<T>(element, null, this.getFront());
    if(this.isEmpty()){
      this.setFront(add);
      this.setBack(add);
    }
    else
      this.setFront(add);
  }
  
  /**
   * Add an element to the tail of the linked list.
   * @param element  the element to add to the tail of the linked list
   */
  public void addToBack(T element) {
    DLNode<T> add = new DLNode<T>(element, this.getBack(), null);
    if(this.isEmpty()){
      this.setFront(add);
      this.setBack(add);
    }
    else
      this.setBack(add); 
  }
  
  /**
   * Remove and return the element at the front of the linked list.
   * @return the element that was at the front of the linked list
   * @throws NoSuchElementException if attempting to remove from an empty list
   */
  public T removeFromFront() {
    if(this.isEmpty())
      throw new NoSuchElementException();
    else{
      T element = getFront().getElement();
      if(this.getFront().getNext() != null){
        this.setFront(this.getFront().getNext());
        this.getFront().setPrevious(null);
      }
      else{
        this.setFront(null);
        this.setBack(null);
      }
      return element;
    }
  }
  
  /**
   * Remove and return the element at the back of the linked list.
   * @return the element that was at the back of the linked list
   * @throws NoSuchElementException if attempting to remove from an empty list
   */
  public T removeFromBack() {
    if(this.isEmpty())
      throw new NoSuchElementException();
    else{
      T element = getBack().getElement();
      if(this.getBack().getPrevious() != null){
        this.setBack(this.getBack().getPrevious());
        this.getBack().setNext(null);
      }
      else{
        this.setBack(null);
        this.setFront(null);
      }
      return element;
    }
  }
  
  /**
   * Returns an interator for the linked list that starts at the head of the list and runs to the tail.
   * @return  the iterator that runs through the list from the head to the tail
   */
  @Override
  public ListIterator<T> iterator() {
    return new ListIterator<T>(){
      
      /** stores the cursor for ListIterator */
      DLNode<T> cursor = new DLNode<T>(null, null, DoubleLinkedList.this.getFront());
      
      /** stores replica of a node that can be set if set is legal */
      DLNode<T> setNode = null;
      
      
      /** Inserts the specified element into the list
        * @param T element the element to be added */
      @Override
      public void add(T element){
        setNode = null; //sets the setNode check to null
        DLNode<T> insertationNode = new DLNode<T>(element, null, null);
        //checks to see if the cursor has any nodes before or after
        if( !this.hasNext() && !this.hasPrevious() ){
          DoubleLinkedList.this.setFront(insertationNode);
          DoubleLinkedList.this.setBack(insertationNode);
        }
        //checks to see if the cursor has any nodes before or after
        else if( this.hasPrevious() && !this.hasNext() ){
          insertationNode.setPrevious(cursor.getPrevious());
          cursor.getPrevious().setNext(insertationNode);
          DoubleLinkedList.this.setBack(insertationNode);
        }
        //checks to see if the cursor has any nodes before or after
        else if( !this.hasPrevious() && this.hasNext() ){
          insertationNode.setNext(cursor.getNext());
          cursor.getNext().setPrevious(insertationNode);
          DoubleLinkedList.this.setFront(insertationNode);
        }
        //checks to see if the cursor has any nodes before or after
        else{
          insertationNode.setNext(cursor.getNext());
          insertationNode.setPrevious(cursor.getPrevious());
          cursor.getPrevious().setNext(insertationNode);
          cursor.getNext().setPrevious(insertationNode);
        }
        cursor.setPrevious(insertationNode);
      }
      
      /** Replaces the last element returned by next() or previous() with the specified element (optional operation).
        * @param T element the element to be set into the list
        * @throws IllegalStateException if set() cannot be called in the current state */
      @Override
      public void set(T element){
        //checks to make sure set() is a llegal move
        if(setNode != null){
          DLNode<T> newNode = new DLNode<T>(element, setNode.getPrevious(), setNode.getNext());
          setNode.getPrevious().setNext(newNode);
          setNode.getNext().setPrevious(newNode);
          //checks to see where the cursor is pointing and sets it appropiately
          if(cursor.getPrevious().equals(setNode)){
            cursor.setPrevious(newNode);
          }
          else{
            cursor.setNext(newNode);
          }
        }
        else
          throw new IllegalStateException();
      }
      
      /** @throws UnsupportedOperationException as the operation is not supported by this class */
      @Override
      public void remove(){
        throw new UnsupportedOperationException();
      }
      

      /** @throws UnsupportedOperationException as the operation is not supported by this class */
      @Override
      public int previousIndex(){
        throw new UnsupportedOperationException();
      }
      

      /** @throws UnsupportedOperationException as the operation is not supported by this class */
      @Override
      public int nextIndex(){
        throw new UnsupportedOperationException();
      }
      
      /** Returns the previous element in the list and moves the cursor position backwards.
        * @return the previous element in the list
        * @throws NoSuchElementException if there is no previous element */
      @Override
      public T previous(){
        setNode = cursor.getPrevious(); //sets the setNode to the current node
        //checks to see if a previous node exists
        if(cursor.getPrevious() == null)
          throw new NoSuchElementException("There is no previous element in the list");
        T currentElement = cursor.getPrevious().getElement();
        cursor.setNext(cursor.getPrevious());
        cursor.setPrevious(cursor.getPrevious().getPrevious());
        return currentElement;
      }
      
      /** Returns true if this list iterator has more elements when traversing the list in the reverse direction.
        * @return true if this list iterator has more elements when traversing the list in the reverse direction */
      @Override
      public boolean hasPrevious(){
        return(cursor.getPrevious() != null);
      }
      
      /** Returns the next element in the list and advances the cursor position.
        * @return the next element in the list
        * @throws NoSuchElementException if there is no previous element */
      @Override
      public T next(){
        setNode = cursor.getNext(); //sets the setNode to the current node
        //checks to see if a next node exists
        if(cursor.getNext() == null)
          throw new NoSuchElementException("There is no next element in the list");
        T currentElement = cursor.getNext().getElement(); 
        cursor.setPrevious(cursor.getNext());
        cursor.setNext(cursor.getNext().getNext());
        return currentElement;
      }
      
      /** Returns true if this list iterator has more elements when traversing the list in the forward direction.
        * @return true if this list iterator has more elements when traversing the list in the forward direction. */
      @Override
      public boolean hasNext(){
        return(cursor.getNext() != null);
      }
    };
  }
  
  /** determines if two objects are equal.
    * @param obj the object to be compared to the value of DoubleLinkedList
    * @return returns true/false depending on whether the value of the input is equal to value of object */
  @Override
  public boolean equals(Object obj){
    //checks to make sure input is an instance of DoubleLinkedList
    if(obj instanceof DoubleLinkedList){
      DoubleLinkedList castedObj = (DoubleLinkedList) obj; //stores the casted input
      DLNode comparisonNode = castedObj.getFront(); //stores a comparison node
      DLNode currentNode = this.getFront(); //stores an equivalent comparison node for this list
      //compares each element while the comparison node is not null
      while(currentNode != null && comparisonNode != null){
        //checks to see if elements are equal
        if(currentNode.getElement().equals(comparisonNode.getElement())){
          comparisonNode = comparisonNode.getNext();
          currentNode = currentNode.getNext();
        }
        else
          return false;
      }
      //checks to make sure that currentNode isn't the comparisonNode
      if(currentNode != comparisonNode)
        return false;
      else
        return true;
    }
    else
      return false;
  }
  
  /** appends the input to the back of this list
    * @param input the list to be appended to this list */
  public void append(DoubleLinkedList<T> input){
    this.getBack().setNext(input.getFront());
    input.getFront().setPrevious(this.getBack());
    this.setBack(input.getBack());
  }
}
