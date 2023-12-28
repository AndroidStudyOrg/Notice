package org.shop.notice

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    Thread {
        // Server 앱
        val port = 8080
        val server = ServerSocket(port)

        while (true) {
            val socket = server.accept()
//        socket.getInputStream()     // Client로부터 들어오는 Stream == Client의 socket.outputStream
//        socket.getOutputStream()    // Client에게 데이터를 주는 Stream == Client의 socket.inputStream

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var input: String? = "-1"
            while (input != null && input != "") {
                input = reader.readLine()
            }
            println("READ DATA $input")

            // Client에게 보내줌
            // header 부분
            printer.println("HTTP/1.1 200 OK")
            printer.println("Content-Type: text/html\r\n")

            // body 부분
            printer.println("{\"message\": \"Hello, World!\"}")
            printer.println("\r\n")
            printer.flush()
            printer.close()

            reader.close()

            socket.close()
        }
    }.start()
}