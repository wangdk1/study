package com.example.netty.script

/*
class Test1{

    static void main(String[] args) {
        println("main")
    }
}
*/

def swap(int[] arr, int a, int b) {
    int temp = arr[a]
    arr[a] = arr[b]
    arr[b] = temp
}
//插入排序
/*
//int[] arr = [3, 7, 5, 1, 4, 12]
for (int i = 1; i < arr.length; i++) {
    for (int j = i; j >0; j--) {
        if (arr[j-1]>arr[j]){
            swap(arr,j,j-1)
        }
    }
}
println(arr)
*/


//快速排序
int[] arr = [3, 7, 5, 1,4, 4, 12]

def  quickSort(int[] arr, int start, int end) {
    int value = arr[start], i = start, j = end;
    while (i < j) {
        while ((i < j)&&arr[i] < value){
            i++
        }
        while ((i < j)&&arr[j] > value){
            j--
        }
        if ((arr[i]==arr[j])&&(i<j)) {
        } else {
            swap(arr,j,i)
        }

    }
    if (i-1>start) quickSort(arr,start,i-1)
    if (j+1<end) quickSort(arr,j+1,end)

}
quickSort(arr,0,arr.length-1)
println(arr)
/*
//groovy模板
def binding //Map
def file = new File("E:\\test\\test.txt")
binding = ["name":"小名","enter":"正确"]
def engine = new groovy.text.SimpleTemplateEngine()
def template = engine.createTemplate(file).make(binding)
FileWriter fileWriter = new FileWriter("E:\\test\\test_2.txt");
template.writeTo(fileWriter)
fileWriter.close()
*/
