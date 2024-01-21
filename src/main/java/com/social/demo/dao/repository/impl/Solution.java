package com.social.demo.dao.repository.impl;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        List<Integer> hFencesList = new ArrayList<>();
        List<Integer> vFencesList = new ArrayList<>();
        for(int i=0;i<hFences.length; i++) {
            hFencesList.add(hFences[i]);

        }
        for(int i=0;i<vFences.length; i++) {
            vFencesList.add(vFences[i]);
        }
        hFencesList.add(1);
        hFencesList.add(m);
        vFencesList.add(1);
        vFencesList.add(n);
        List<Integer> hFencesList1 = new ArrayList<>();
        List<Integer> vFencesList1 = new ArrayList<>();
        for(int i=0;i<hFencesList.size();i++){
            for(int j=i;j<hFencesList.size();j++){
                hFencesList1.add(Math.abs(hFencesList.get(j) - hFencesList.get(i)));
            }
        }
        System.out.println(hFencesList1);
        int max= -1;
        for(int i=0;i<vFencesList.size();i++){
            for(int j=i;j<vFencesList.size();j++){
                int s = Math.abs(vFencesList.get(j)-vFencesList.get(i));
                if(hFencesList1.contains(s)){
                    if(s>max){
                        max = s;
                    }
                }
            }
        }

        int result = (max * max) % 1_000_000_007;
        return result;
    }

    public static void main(String[] args) {
        int[] h = {2,3};
        int[] v = {2};
        System.out.println(maximizeSquareArea(4,3,h,v));
    }
}
