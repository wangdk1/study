package com.example.netty.file.constant;

/**
 * 编解码常量
 */
public class CodecType {


    public static final int FILE = FileType.FLAG;
    public static final int FILE_NORMAL = FileType.NORMAL;
    public static final int FILE_ERR = FileType.ERR;


    public static final int TEXT = TextType.FLAG;



    private static class FileType {
        //
        public static final int FLAG = 0x11ff;
        //
        public static final int NORMAL = 0x1;
        //
        public static final int ERR = 0x2;

    }

    private static class TextType {
        //
        public static final int FLAG = 0xdadd;

    }


    /*
        传输协议设置：
        传输类型：
            文件：int               byte                 long，        short：       文件名字节数组
                 文件类型标识符     服务器返回情况         为文件长度    文件名称长度


            文本：int               short：       文件名字节数组
                 文件类型标识符     文件名称长度


     */
}
