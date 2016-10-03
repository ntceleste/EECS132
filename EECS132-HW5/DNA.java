import java.util.*;

/** represents a sequence of DNA, it is able to be compared, and spliced
  * @author Nate Celeste */
public class DNA<T extends DNA.Base> extends DoubleLinkedList<T> implements Comparable<DNA<T>>{
  
  /** represents each of the 4 DNA bases
    * @author Nate Celeste */
  enum Base{
    A('A'), T('T'), C('C'), G('G');
    
    /** stores the char that makes the DNA base */
    private char baseChar;
    
    /** creates a new instance of base 
      * @param baseChar the character of the base to be created*/
    private Base(char baseChar){
      this.baseChar = baseChar;
    }
    
    /** returns the value of baseChar
      * @return the value of baseChar */
    public char getBaseChar(){
      return baseChar;
    }
  }
  
  /** Takes two strands of DNA, splices them together in the most efficent way
    * @param args the strings to be converted into DNA */
  public static void main(String[] args){
    DNA<Base> dna1 = null; //stores the 1st DNA strand
    DNA<Base> dna2 = null; //stroes the 2nd DNA strand
    //checks to see if String inputs were valid DNA
    try{
      dna1 = DNA.string2DNA(args[0]);
      dna2 = DNA.string2DNA(args[1]);
    }
    catch(IllegalArgumentException exception){
      System.out.println("You must provide two valid DNA inputs, try again.");
      return;
    }
    catch(ArrayIndexOutOfBoundsException excecption2){
      System.out.println("You must provide two valid DNA inputs, try again.");
      return;
    }
    int abOverlap = DNA.greatestOverlap(dna1, dna2); //stores the greatest overlap between 1 and 2
    int baOverlap = DNA.greatestOverlap(dna2, dna1); //stores the greatest overlap between 2 and 1
    //checks to see which overlap is greater, prints greatest splice
    if(abOverlap > baOverlap){
      dna1.splice(dna2, abOverlap);
      System.out.println(dna1);
    }
    else{
      dna2.splice(dna1, baOverlap);
      System.out.println(dna2);
    }
  }
  
  /** returns the length of the longest overlap between two DNA strands
    * @param input1 the first DNA strand
    * @param input2 the second DNA strand
    * @param <E> resticts greatestOverlap to DNA lists of Base
    * @return the length of the longest overlap between input1 and input2 */
  public static <E extends Base> int greatestOverlap(DNA<E> input1, DNA<E> input2){
    int shortestInputLength = input1.toString().length(); //stores the length of the shortest input
    //checks to see whcih DNA strand has the shortest length
    if(shortestInputLength > input2.toString().length())
      shortestInputLength = input2.toString().length();
    int i = 1;
    int longestOverlap = 0; //counts the longest overlap
    //counts the overlaps between the strands
    while(i <= shortestInputLength){
      if(DNA.overlaps(input1, input2, i))
        longestOverlap = i;
      i++;
    }
    return longestOverlap;
  }
  
  /** creates a DNA strand out of a string
    * @param input the string to be converted into a DNA strand
    * @return the DNA equivilent of input
    * @throws IllegalArgumentException if string is empty
    * @throws IllegalArgumentException is string contains non-valid char's */
  public static DNA<Base> string2DNA(String input){
    DNA<Base> newDNA = new DNA<Base>(); //stores the new DNA strand
    //checks to see if string is empty
    if(input.equals(""))
      throw new IllegalArgumentException("Input must not be empty");
    else{
      //checks each char in the string to see what base is alligns with
      for(int i = 0; i < input.length(); i++){
        if(input.charAt(i) == 'C'){
          newDNA.addToBack(Base.C);
        }
        else if(input.charAt(i) == 'T'){
          newDNA.addToBack(Base.T);
        }
        else if(input.charAt(i) == 'A'){
          newDNA.addToBack(Base.A);
        }
        else if(input.charAt(i) == 'G'){
          newDNA.addToBack(Base.G);
        }
        else
          throw new IllegalArgumentException("Input must comprise of only the bases A, C, T, G ");
      }
    }
    return newDNA;
  }
  
  /** returns the value of DNA as a String
    * @return the value of DNA as a String */
  @Override
  public String toString(){
    StringBuilder dnaString = new StringBuilder(); //stores the DNA strand as a StringBuilder
    //for each base it appends it do a stringbuilder
    for (Base x : this){
      dnaString.append(x);
    }
    return dnaString.toString();
  }
  
  /** removes numbases from the start of dna and then appends the remaining 
    * bases of dna to the end of this DNA.
    * @param data the DNA to be spliced to this DNA
    * @param numbases the number of bases to be spliced
    * @throws IllegalArgumentException if a DNA strand is shorter than numbases */
  public void splice(DNA<T> data, int numbases){
    //removes the correct number of bases from data
    try{
      for(int i = 0; i < numbases; i++){
        data.removeFromFront();
      }
    }
    catch(NoSuchElementException e){
      throw new IllegalArgumentException("The Inputted DNA strand is shorter than numbases.");
    }
    this.append(data);
  }
  
  /** checks to see if two DNA strands have overlapping bases
    * @param dna1 the first DNA strand
    * @param dna2 the second DNA strand
    * @param n the number of bases to check
    * @param <E> resticts greatestOverlap to DNA lists of Base
    * @return true if the strands overlap, false if they don't */
  public static <E extends Base> boolean overlaps(DNA<E> dna1, DNA<E> dna2, int n){
    DLNode<E> dna1Node = dna1.getBack(); //stores the pointer node for dna1
    DLNode<E> dna2Node = dna2.getFront(); //stores the pointer node for dna2
    DNA<E> modifiedDNA1 = new DNA<E>(); //stores the modified dna1 strand
    DNA<E> modifiedDNA2 = new DNA<E>(); //stores the modified dna2 strand
    //grabs the last n bases of dna1/first n bases of dna2 and stores them in original order
    for(int i = 0; i < n; i++){
      if(dna1Node.getPrevious() != null){
        modifiedDNA1.addToFront(dna1Node.getElement());
        dna1Node = dna1Node.getPrevious();
      }
      if(dna2Node.getNext() != null){
        modifiedDNA2.addToBack(dna2Node.getElement());
        dna2Node = dna2Node.getNext();
      }
    }
    return modifiedDNA1.equals(modifiedDNA2);
  }
  
  /** compares one DNA strand to another
    * @param input the strand that this should be compared to
    * @return -int if this is shorter than input or alphabetically less than input, +int if this is longer or alphabetically greater, 0 if equal to eachother */
  @Override
  public int compareTo(DNA<T> input){
    boolean dna1Alpha = false; //stores the aplhabetical value of dna1
    boolean dna2Alpha = false; //stores the aplhabetical value of dna2
    DLNode<T> dna1Node = this.getFront(); //stores the pointer node for dna1
    DLNode<T> dna2Node = input.getFront(); //stores the pointer node for dna2
    //compares each element to eachother to checks to see which comes alphabetically first
    while(dna1Node != null && dna2Node != null){
      //checks to see if an alpha has been set yet
      if(!dna1Alpha && !dna2Alpha){
        //checks to see if an alpha needs to be set
        if(dna1Node.getElement().getBaseChar() < dna2Node.getElement().getBaseChar())
          dna2Alpha = true;
        else if(dna1Node.getElement().getBaseChar() > dna2Node.getElement().getBaseChar())
          dna1Alpha = true;
      }
      dna1Node = dna1Node.getNext();
      dna2Node = dna2Node.getNext();
    }
    //checks to see if both lists reached their ends and returns the proper value based on alphabet
    if(dna1Node == null && dna2Node == null){
      if(dna2Alpha)
        return -1;
      else if(dna1Alpha)
        return 1;
      else
        return 0;
    }
    //returns proper value based on length
    if(dna1Node == null)
      return -1;
    return 1;
  }
}