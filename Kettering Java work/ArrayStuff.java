import java.util.*;
public class ArrayStuff
{
   public static void main(String [] args)
   {
      //Declare arrays
      char [] carray; 
      int n [];
      double [] x, y, z; //double array x, double array y, double array z
      long f, g[], h; //long array g, everything else is just a long
      //instantiate arrays
      carray = new char[5];
      n = new int[30];
      //combine declare and instantiate
      float [] ft = new float[7];
      String [] s = new String[4];
      //instantiating arrays
      //accessing array elements
      n[1] = 5;
      n[3] = 17;
      //x = {1.0, 2.0, 3.0};
      int [] m = {1, 3, 5, 7, 9};
      String [] days = {"sunday", "Monday", "Tuesday", "Wednesday", 
                         "Thursday", "Friday", "Saturday"};
      
      int temp = n[1];
      n[1] = n[3];
      n[3] = temp;
      
      //read values into an array
      Scanner in = new Scanner(System.in);
      int [] k = new int[5];
      System.out.println("enter " + k.length + " values");
      for(int i = 0; i < k.length; i++ )
      {
         System.out.print("Enter value: ");
         k[i] = in.nextInt();
      }
      
      
      System.out.println("enter " + k.length + " values");
      for(int i = 0; i < k.length; i++ )
      {
         System.out.print("Enter value: ");
         k[i] = in.nextInt();
      }
      
      
      int sum = 0;
      for(int i : k)
      {
         sum += i;
      }
      System.out.print("Sum is " + sum);
      
      
   }
  
   
}