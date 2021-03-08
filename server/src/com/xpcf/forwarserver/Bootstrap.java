package com.xpcf.forwarserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author XPCF
 * @version 1.0
 * @date 3/8/2021 1:10 PM
 */
public class Bootstrap {
    public static void main(String[] args) throws IOException {
        ServerSocket receiveServer = new ServerSocket(4396);
        ServerSocket forwardServer = new ServerSocket(2200);
        Socket privateClient = forwardServer.accept();
        System.out.println("与 private client 建立 tcp connection");
        OutputStream privateOut = privateClient.getOutputStream();
        InputStream privateInput = privateClient.getInputStream();
        while (true) {
            Socket userClient = receiveServer.accept();
            System.out.println("接受到user的请求");

            byte[] bytes = new byte[1024];
            InputStream userInput = userClient.getInputStream();
            OutputStream userOut = userClient.getOutputStream();

            // TODO 数据接收
            int length = userInput.read(bytes);
            System.out.println("success read user data");

            // forward tcp data

            privateOut.write(bytes, 0, length);
            System.out.println("success send data to private");
            privateOut.flush();

            // receive private data
            length = privateInput.read(bytes);
            System.out.println("success receive data from private");

            // send data to user
            userOut.write(bytes, 0, length);
            System.out.println("success send data to user");
            userOut.flush();
            userClient.close();
        }
    }
}