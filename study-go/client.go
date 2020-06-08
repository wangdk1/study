package main

import (
	"fmt"
	"net"
)

func main() {
	//var wg sync.WaitGroup
	conn, err := net.Dial("tcp", ":8888")
	if err!=nil {
		fmt.Println("conn err:",err)
	}
	for i:=0;i<100;i++{
		count, err2 := conn.Write([]byte("hello"))

		if err2!=nil {
			fmt.Println("write err:",err)
		}

		fmt.Printf("write count:%d ,%d \n",count ,i)
		//fmt.Println("write count:",count)
	}
	//wg.Wait()
}
