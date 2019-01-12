package com.bobo.algorithm;

public class Algorithm_sort {

    private int a[];


    public void sort_max_min_mid(int[] a){
        int len = a.length;
        int mid_index = len / 2;
        int mid_v = a[mid_index];



    }


    public void sort_max_min_1(int[] a){
        int len = a.length;
        for(int i = 0; i < len ; i++){
            int v = a[i];
            int position = i;
            for(int k = position + 1 ; k < len ; k++){
                    if(v < a[k]){
                        v = a[k];
                        position = k;//获取剩余数据中最大值 和最大值的位置
                    }
            }
            a[position] = a[i];//进行数据交换
            a[i] = v;
        }
    }

}
