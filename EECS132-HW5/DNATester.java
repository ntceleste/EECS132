import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.lang.reflect.*;
import java.lang.reflect.Method.*;

/** tests the DNA class 
  * @author Nate Celeste */
public class DNATester{
  
  /** tests the toString method of DNA */
  @Test
  public void testToString(){
    DNA a = DNA.string2DNA("ATCG");
    DNA b = new DNA();
    assertEquals("testing with full DNA sequence", "ATCG", a.toString());
    assertEquals("testing with an empty DNA sequence", "", b.toString());
  }
  
  /** tests the string2DNA method of DNA */
  @Test
  public void testString2DNA(){
    DNA a = DNA.string2DNA("ATCG");
    try{
      DNA b = DNA.string2DNA("ABCD");
      fail("expected IllegalArgumentException to be thrown");
    }
    catch(IllegalArgumentException error){
      assertEquals(error.getMessage(), "Input must comprise of only the bases A, C, T, G ");
    }
    try{
      DNA c = DNA.string2DNA("");
      fail("Expected IllegalArguementException to be Thrown.");
    }
    catch(IllegalArgumentException error){
      assertEquals(error.getMessage(), "Input must not be empty");
    }
    assertEquals("ATCG", a.toString());
  }
  
  /** tests the splice method of DNA */
  @Test
  public void testSplice(){
    DNA<DNA.Base> a = DNA.string2DNA("GCTA");
    DNA<DNA.Base> b = DNA.string2DNA("TAGC");
    DNA<DNA.Base> c = DNA.string2DNA("GCTAGC");
    a.splice(b, 2);
    assertEquals(c.toString(), a.toString());
    try{
      c.splice(b, 5);
      fail("Expected IllegalArguementException to be Thrown.");
    }
    catch(IllegalArgumentException error){
      assertEquals(error.getMessage(), "The Inputted DNA strand is shorter than numbases.");
    }
  }
  
  /** tests the overlap method of DNA */
  @Test
  public void testOverlaps(){
    DNA<DNA.Base> a = DNA.string2DNA("TCGATA");
    DNA<DNA.Base> b = DNA.string2DNA("ATATGC");
    DNA<DNA.Base> c = DNA.string2DNA("TGCA");
    DNA<DNA.Base> d = DNA.string2DNA("ATA");
    DNA<DNA.Base> e = DNA.string2DNA("AT");
    assertTrue("Testing a True Overlap", DNA.overlaps(a, b, 3));
    assertFalse("Testing a False Overlap", DNA.overlaps(b, c, 1));
    assertFalse("Testing a False Overlap", DNA.overlaps(d, e, 3));
  }
  
  /** tests the comparable method of DNA */
  @Test
  public void testComparable(){
    DNA<DNA.Base> a = DNA.string2DNA("ACT");
    DNA<DNA.Base> b = DNA.string2DNA("ACTG");
    DNA<DNA.Base> c = DNA.string2DNA("TGCA");
    assertTrue( "tests comparison with 1 shorter string", a.compareTo(b) < 0);
    assertTrue( "tests coamparison with 1 shorter string", b.compareTo(a) > 0);
    assertTrue( "tests comparison with DNA of the same size", b.compareTo(c) < 0);
    assertTrue( "tests comparison with DNA of the same size", c.compareTo(b) > 0);
    assertTrue( "tests compariosn with the same DNA", c.compareTo(c) == 0);
  }
  
  /** tests the greatestOverlap method of DNA */
  @Test
  public void testGreatestOverlap(){
    DNA<DNA.Base> a = DNA.string2DNA("GCTA");
    DNA<DNA.Base> b = DNA.string2DNA("TAGC");
    DNA<DNA.Base> c = DNA.string2DNA("TTGCGC");
    assertEquals(2, DNA.greatestOverlap(a,b));
    assertEquals(0, DNA.greatestOverlap(a,c));
  }
}