package com

import (
	"fmt"
	"os"

)

func Test_file1()  {

	var b = make([]byte,100)
	file, err := os.OpenFile("F:/ww/Test2.java", os.O_RDWR, 6)
	if err!= nil{
		fmt.Println(err)
	}
	count, _ := file.Read(b)
	if count>0 {
		fmt.Println(string(b))
	}


}