package org.crayzer.jvm;

public class Hello {

     public int maxArea(int[] height) {
         int max = 0;
         for (int i = 0, j = height.length - 1; i < j; ) {
             int minHeight = height[i] < height[j] ? height[i++] : height[j--];
             int area = (j - i + 1) * minHeight;
              if (area > max) max = area;
         }
         return max;
     }

//    public static void main(String[] args) {
//        Hello hello = new Hello();
//        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
//        System.out.println(hello.maxArea(height));
//    }
}
