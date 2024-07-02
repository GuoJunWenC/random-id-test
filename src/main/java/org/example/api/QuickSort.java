package org.example.api;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {29, 10, 14, 37, 13}; // 待排序数组
        quickSort(arr, 0, arr.length - 1); // 调用快速排序函数
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high); // 获取基准值的位置
            quickSort(arr, low, pivot - 1); // 递归排序左半部分
            quickSort(arr, pivot + 1, high); // 递归排序右半部分
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[low]; // 选择第一个元素作为基准值
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            arr[low] = arr[high]; // 将小于基准值的元素移到低端
            while (low < high && arr[low] <= pivot) {
                low++;
            }
            arr[high] = arr[low]; // 将大于基准值的元素移到高端
        }
        arr[low] = pivot; // 将基准值插入最终位置
        return low; // 返回基准值的位置
    }
}
