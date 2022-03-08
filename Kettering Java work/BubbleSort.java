public class BubbleSort
{
   public static void sort(NumberedWord [] a)
   {
      boolean sorted = false;
      int bottom = a.length - 1;
      while (! sorted)
      {  
         sorted = true;
         for(int index = 0; index < bottom; index++)
         {
            if(a[index].getWord().compareToIgnoreCase(a[index+1].getWord()) > 0)
            {
               swap(a, index, index + 1);
               sorted = false;
            }
         }
         bottom--;
      }  
   }
   
   public static void swap(NumberedWord [] a, int ind1, int ind2)
   {
      NumberedWord temp = a[ind1];
      a[ind1] = a[ind2];
      a[ind2] = temp;
   }
}