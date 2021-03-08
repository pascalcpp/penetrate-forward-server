package com.xpcf.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author XPCF
 * @version 1.0
 * @date 3/8/2021 1:12 PM
 */
public class Bootstrap {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1", 2200);
        InputStream inputStream = client.getInputStream();
        OutputStream outputStream = client.getOutputStream();
        byte[] bytes = new byte[1024];
        while (true) {
            inputStream.read(bytes);
            System.out.println("接收到数据");

            String response_head = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n\r\n";
            String responseString = "here is private client";

            responseString = response_head + responseString;

            outputStream.write(responseString.getBytes());
            System.out.println("success send data to forward server");
            outputStream.flush();
        }


    }
}