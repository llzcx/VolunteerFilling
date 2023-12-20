package com.social.demo.demo;
public class Solution {
    static long countSubarrays(int[] nums, int k) {
        long count =0;
        long  Max = -1;
        for(int i =0;i<nums.length;i++){
            Max = Math.max(Max,nums[i]);
        }

        for(int i=0;i<nums.length;i++){
            int flag=0;
            for(int j=i;j<nums.length;j++){
             if(nums[j]==Max){
                 flag++;
             }
                if(flag>=k){
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 3, 3};
        int k =2;
        long s = countSubarrays(arr,k);
        System.out.println(s);

    }
}
