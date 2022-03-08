public class ArrayStuff2
{
   public static void main(String [] args)
   {
      //copy arrays
      int [] a = {1,3,4,9,6};
      int [] b = a;
      System.out.println(b[2]);
      //not copy of an array, it is a copy of the reference
      //that is the array address, a and b are aliases of the array.
      //actual copy
      b = arrayCopy(a);
      a = makeBigger(a);
   }
   
   public static int [] makeBigger(int [] a)
   {
      int [] bigger = new int[a.length + 1];
      arrayCopy(a, bigger);
      return bigger;
   }
   
   public static void arrayCopy(int [] small, int [] big)
   {
      for(int i = 0; i < small.length; i++)
         big[i] = small[i];
   }
   
   public static int [] arrayCopy(int [] a)
   {
      int [] b = new int[a.length];
      for(int i = 0; i < a.length; i++)
      {
         b[i] = a[i];
      }
      return b;
   }
   
}