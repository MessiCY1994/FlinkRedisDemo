package com.messiyang.flinkdemo.demo;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class FlinkRedisSocketDemo {
        public static void main(String[] args) throws Exception {
            Socket socket = new ServerSocket(9090)
                    .accept();

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            Scanner scanner = new Scanner(System.in);
            String line;
            while (! "q".equals(line = scanner.nextLine())){
                writer.println(line);
                writer.flush();
            }

            scanner.close();
            writer.close();
        }

}
