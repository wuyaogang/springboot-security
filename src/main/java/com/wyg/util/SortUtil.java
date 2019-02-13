package com.wyg.util;

/**
 * Created by A14857 on 2019/2/13.
 */
public class SortUtil {
    /**
     *  冒泡法排序
     *  比较相邻的元素。如果第一个比第二个小，就交换他们两个。
     *  对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最小的数。
     *  针对所有的元素重复以上的步骤，除了最后一个。
     *  持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。

     *
     * @param numbers
     *            需要排序的整型数组
     */
    public static void bubbleSort01(int[] numbers) {
        int temp; // 记录临时中间值
        int size = numbers.length; // 数组大小
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (numbers[i] < numbers[j]) { // 交换两数的位置

                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
    }
    //以上不知是什么排序，将基准位置的元素和后面的元素进行比较，如果基准位置值比后面元素小，则交换位置，交换后的元素为新的基准元素。以下才是真正的冒泡排序。
    public static void bubbleSort(int[] a) {
        int temp;
        int size = a.length;
        for(int i=1; i<size; i++) {
            for(int j=0; j<size-i; j++) {
                if(a[j] < a[j+1]) {
                    temp = a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
            for(int aa : a)
                System.out.print(aa+",");
            System.out.println();
        }
    }

    /**
     * 算法思想：基于分治的思想，是冒泡排序的改进型。
     * 首先在数组中选择一个基准点（该基准点的选取可能影响快速排序的效率，后面讲解选取的方法），
     * 然后分别从数组的两端扫描数组，设两个指示标志（lo指向起始位置，hi指向末尾)，
     * 首先从后半部分开始，如果发现有元素比该基准点的值小，就交换lo和hi位置的值，然后从前半部分开始扫秒，
     * 发现有元素大于基准点的值，就交换lo和hi位置的值，如此往复循环，直到lo>=hi,然后把基准点的值放到hi这个位置。
     * 一次排序就完成了。以后采用递归的方式分别对前半部分和后半部分排序，
     * 当前半部分和后半部分均有序时该数组就自然有序了。
     * @param array
     * @param lo
     * @param hi
     * @return
     */
    public static int partition(int []array,int lo,int hi){
        //固定的切分方式
        int key=array[lo];
        while(lo<hi){
            while(array[hi]>=key&&hi>lo){//从后半部分向前扫描
                hi--;
            }
            array[lo]=array[hi];
            while(array[lo]<=key&&hi>lo){//从前半部分向后扫描
                lo++;
            }
            array[hi]=array[lo];
        }
        array[hi]=key;
        return hi;
    }

    public static void sort(int[] array,int lo ,int hi){
        if(lo>=hi){
            return ;
        }
        int index=partition(array,lo,hi);
        sort(array,lo,index-1);
        sort(array,index+1,hi);
    }

    /**
     * 对于基准位置的选取一般有三种方法：固定切分，随机切分和三取样切分。固定切分的效率并不是太好，
     * 随机切分是常用的一种切分，效率比较高，最坏情况下时间复杂度有可能为O(N2).对于三数取中选择基准点是最理想的一种。
     * @param array
     * @param lo
     * @param hi
     * @return
     */
    public static int partition1(int []array,int lo,int hi){
        //三数取中
        int mid=lo+(hi-lo)/2;
        if(array[mid]>array[hi]){
            swap(array[mid],array[hi]);
        }
        if(array[lo]>array[hi]){
            swap(array[lo],array[hi]);
        }
        if(array[mid]>array[lo]){
            swap(array[mid],array[lo]);
        }
        int key=array[lo];

        while(lo<hi){
            while(array[hi]>=key&&hi>lo){
                hi--;
            }
            array[lo]=array[hi];
            while(array[lo]<=key&&hi>lo){
                lo++;
            }
            array[hi]=array[lo];
        }
        array[hi]=key;
        return hi;
    }

    public static void swap(int a,int b){
        int temp=a;
        a=b;
        b=temp;
    }
    public static void sort1(int[] array,int lo ,int hi){
        if(lo>=hi){
            return ;
        }
        int index=partition(array,lo,hi);
        sort1(array,lo,index-1);
        sort1(array,index+1,hi);
    }

    /**
     * 快速排序
     *
     *  从数列中挑出一个元素，称为“基准”
     *  重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分割之后，
     *  该基准是它的最后位置。这个称为分割（partition）操作。
     *  递归地把小于基准值元素的子数列和大于基准值元素的子数列排序。
     *
     * @param numbers
     * @param start
     * @param end
     *//*
    public static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
            int temp; // 记录临时中间值
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end))
                    i++;
                while ((numbers[j] > base) && (j > start))
                    j--;
                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j)
                quickSort(numbers, start, j);
            if (end > i)
                quickSort(numbers, i, end);
        }
    }

    *//**
     * 完全符合快速排序定义的算法
     * @param a
     * @param start
     * @param end
     *//*
    public static void quickSort01(int[] a, int start, int end) {
        if(start >= end)
            return;
        int i = start;
        int j = end;
        int base = a[start];
        while(i != j) {
            while(a[j] >= base && j > i)
                j--;
            while(a[i] <= base && i < j)
                i++;
            if(i < j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        a[start] = a[i];
        a[i] = base;
        te(a, start, i - 1);
        te(a, i + 1, end);
    }*/

    /**
     * 选择排序
     * 在未排序序列中找到最小元素，存放到排序序列的起始位置
     * 再从剩余未排序元素中继续寻找最小元素，然后放到排序序列起始位置。
     * 以此类推，直到所有元素均排序完毕。
     *
     * @param numbers
     */
    public static void selectSort(int[] numbers) {
        int size = numbers.length;
        int temp;
        for (int i = 0; i < size; i++) {
            int k = i;
            for (int j = size - 1; j >i; j--)  {
                if (numbers[j] < numbers[k]) {
                    k = j;
                }
            }
            temp = numbers[i];
            numbers[i] = numbers[k];
            numbers[k] = temp;
        }
    }

    /**
     * 插入排序
     *
     *  从第一个元素开始，该元素可以认为已经被排序
     *  取出下一个元素，在已经排序的元素序列中从后向前扫描
     *  如果该元素（已排序）大于新元素，将该元素移到下一位置
     *  重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
     *  将新元素插入到该位置中
     *  重复步骤2
     * @param numbers
     */
    public static void insertSort(int[] numbers) {
        int size = numbers.length, temp, j;
        for(int i=1; i<size; i++) {
            temp = numbers[i];
            for(j = i; j > 0 && temp < numbers[j-1]; j--)
                numbers[j] = numbers[j-1];
            numbers[j] = temp;
        }
    }

    /**
     * 归并排序
     *
     *  申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列
     *  设定两个指针，最初位置分别为两个已经排序序列的起始位置
     *  比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置
     *  重复步骤3直到某一指针达到序列尾
     *  将另一序列剩下的所有元素直接复制到合并序列尾
     *
     * @param numbers
     */
    public static void mergeSort(int[] numbers, int left, int right) {
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(numbers, i, i + (s - 1), i + (t - 1));
                i += t;
            }
            if (i + (s - 1) < right)
                merge(numbers, i, i + (s - 1), right);
        }
    }

    /**
     * 归并算法实现
     *
     * @param data
     * @param p
     * @param q
     * @param r
     */
    private static void merge(int[] data, int p, int q, int r) {
        int[] B = new int[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (data[s] <= data[t]) {
                B[k] = data[s];
                s++;
            } else {
                B[k] = data[t];
                t++;
            }
            k++;
        }
        if (s == q + 1)
            B[k++] = data[t++];
        else
            B[k++] = data[s++];
        for (int i = p; i <= r; i++)
            data[i] = B[i];
    }
}
