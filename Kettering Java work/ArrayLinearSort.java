import java.util.*;
public class ArrayLinearSort
{   
   public static void main(String [] args)
   {
      int [] a = MakeArray.random(10, 5, 37);
      ArraySorter.sort(a);
      Scanner in = new Scanner (System.in);
      System.out.print("What value do you want to search for? ");
      int value = in.nextInt();
      int location = search(a, value);
      if (location == -1)
         System.out.println(value + " was not found in the array");
      else
         System.out.println(value + " was found at index " + location);
         
   }
   
   public static int search(int [] a, int val)
   {
      int lowIndex = 0;
      int highIndex = a.length - 1;
      while(highIndex >= lowIndex)
      {
         int midIndex = (highIndex + lowIndex) / 2;
         if (val == a[midIndex])
         {
            System.out.println("count = " + count);
            return midIndex;
         }
         else if (val > a[midIndex])
         {
            lowIndex = midIndex + 1;//throw away lower half of array
         }
         else
         {
            highIndex = midIndex - 1;//throw away upper half of array
         }
         System.out.println("low = " + lowIndex + "\thigh = " + highIndex);
      }
      System.out.println("count " + count);
      return -1;
   }
}