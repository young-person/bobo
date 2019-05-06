package com.bobo.algorithm;


public class Algorithm_sort {

    private int a[] = {3,10,11,7,1,5,8,0};


    /**
     * 插入排序
     *
     * 当找到 那个小（大）值的时候 需要把前面的值跟他进行比较 保持插入有序
     */
    public void insert_sort(){
     //   AnnotationConfigWebApplicationContext

        for(int i = 1; i < a.length; i ++){

            if(a[i-1] < a[i]){
                int tmp = a[i];
                int j = i;

                while(j > 0 && a[j - 1] < tmp){
                    a[j] = a[j - 1];
                    j --;
                }
                a[j] = tmp;
            }
        }

        sys(a);
    }


    /**
     * 选择排序
     *
     * 找出 最大最小值 按顺序排放
     */
    public void selection_sort(){

        for(int i = 0; i < a.length - 1; i ++){
            int k = i;
            for(int j = k + 1; j < a.length; j ++){
                if (a[j] < a[k]){
                    k = j;//记录最小值的位置
                }
            }
            if(i != k){
                int tmp = a[i];
                a[i] = a[k];
                a[k] = tmp;
            }
        }
        sys(a);
    }


    /**
     * 冒泡排序
     *
     * 第 index 元素 和 后面的所有元素进行对比
     */
    public void bubble_sort(){
        int pos = 0,k = a.length - 1;
        boolean flag = true;
        for(int index = 0; index < a.length - 1; index ++){
            for(int j = 0; j < k; j ++){
                if (a[j] > a[j + 1]){
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    pos = j + 1;
                    flag = false;
                }
            }
            k = pos;
            if (flag)break;
        }

        for(int index = 0; index < a.length - 1; index ++){
            for(int j = 0; j < a.length - 1 - index; j ++){
                if(a[j] > a[j + 1]){
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
            }
        }
        sys(a);

    }

    private void sys(int[] a){
        for(int index = 0; index < a.length; index ++){
            System.out.println(a[index]);
        }
    }

    public void quick_sort(int left, int right){
        if (left > right)return;

        int tmp = a[left];
        int i = left;
        int j = right;

        while(i != j){
            while(a[j] > tmp && i < j){
                j --;
            }
            while(a[i] <= tmp && i < j){
                i ++;
            }
        }

    }
    public void sort_max_min_1(){
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
        sys(a);
    }

}
