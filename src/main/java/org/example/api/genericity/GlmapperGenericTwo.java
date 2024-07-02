package org.example.api.genericity;


import java.util.ArrayList;
import java.util.List;

public class GlmapperGenericTwo<A> {


    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }

    public static <T> void addToList(List<T> list, T item) {
        list.add(item);
    }
    public  <T> void updateToList( List<T> list,T item) {

    }
    public static void main(String[] args) {
        List<?> wildcardList = new ArrayList<>();
         //wildcardList.add("Hello");  // 编译错误，无法添加元素
        printList(wildcardList);  // 可以打印列表中的元素

        List<String> stringList = new ArrayList<>();
        addToList(stringList, "World");  // 可以向列表中添加元素
        printList(stringList);  // 可以打印列表中的元素
    }

}
