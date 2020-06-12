package com.example.netty.script

/*
class Test1{

    static void main(String[] args) {
        println("main")
    }
}
*/
/*
int[] arr = [3,4 ,4,12]


for (int i = 0; i < arr.length; i++) {
    println(arr[i])
}

println ((1 ..<10).size())

*/


/*def test(){
    println("sdsfdf")
}

test()*/
def text ='This Tutorial focuses on $TutorialName. In this tutorial you will learn about $Topic'
def binding = ["TutorialName":"Groovy", "Topic":"Templates"]
def engine = new groovy.text.SimpleTemplateEngine()
def template = engine.createTemplate(text).make(binding)

println template

