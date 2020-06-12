package main

import (
	"fmt"
	"net"
)

func main() {
	var  tcpAddr *net.TCPAddr
	tcpAddr, _ = net.ResolveTCPAddr("tcp4", "127.0.0.1:8888")
	var listenter *net.TCPListener;
	listenter , err:= net.ListenTCP("tcp", tcpAddr)
	//net.TCPListener{}
	//listener, err:= net.Listen("tcp", ":8000")
	//var wg sync.WaitGroup
	if err != nil {
		fmt.Println("listen err:", err)
		return
	}
	defer listenter.Close()

	var conn net.Conn;
	for {
		conn, err = listenter.Accept()
		if err != nil{
			fmt.Println("connect err:",err)
		}
		go handConn(conn)
	}



}

func handConn(conn net.Conn) {
	//defer conn.Close()
	var bytes []byte = make([]byte,10)
	var  i int
	for  {

		length, err := conn.Read(bytes[:])
		if err != nil{
			fmt.Println("read err:",err)
			conn.Close()
			break
		}
		i++
		if length>0{
			fmt.Println(string(bytes[:]),"   i = ",i)
		}
	}
}
