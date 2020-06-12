package main

import (
	"bufio"
	"fmt"
	"os"
	"reflect"
	//"study-go/com"
	"time"
)

func main() {
  //com.Test_file1()    interface{}
  var d Dog = Dog{Animal{"dog"}}
  var a  =  Animal{"animal"}

	/*result,ok := a.(IAnimal)
	fmt.Println(ok)
	fmt.Println(result)*/
/*	result,ok := d.(Animal)
	fmt.Println(ok)
	fmt.Println(result)
*/
	//fmt.Println(d.kind)
	result1,ok1 := (interface{}(d)).(Animal)
	fmt.Println(ok1)
	fmt.Println(result1)

	dogType := reflect.TypeOf(d)
	fmt.Println(dogType.Name(),dogType.Kind())

    a.Eat()
    d.Eat()
}

type IAnimal interface {
	Eat() 
}

func (a Animal) Eat()  {
	fmt.Println("动物：",a.kind)
}
func (a Dog) Eat()  {
	fmt.Println("狗：",a.kind)
}


type Book struct {
	title string
	author string
	book_id int
}

type Dog struct {
	 Animal
}

type Animal struct {
	kind string
}

func channelTest(){
	ch := make(chan int)
	go func() {
		time.Sleep(time.Second*1)
		ch <- 1
	}()

	i:= <-ch
	print(i)
}


func (book *Book) test2() {
	book.title="qqqqq"
}

func test(arr *[3]int) {
	arr[2] = 1111
	fmt.Println(arr[2])
}

func io()  {
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}

	var s1 string
	var a  int
	for{
		n, err := fmt.Scan(&s1,&a)
		if err!=nil{
			fmt.Println(err)
		}
		fmt.Println(n)
		fmt.Println(s1,a)
	}

}