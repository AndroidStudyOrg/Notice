package org.shop.notice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.shop.notice.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        Thread{
            // Server 앱
            val port = 8080
            val server = ServerSocket(port)

            val socket = server.accept()
//        socket.getInputStream()     // Client로부터 들어오는 Stream == Client의 socket.outputStream
//        socket.getOutputStream()    // Client에게 데이터를 주는 Stream == Client의 socket.inputStream

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var input: String? = "-1"
            while (input != null && input != "") {
                input = reader.readLine()
            }
            Log.e("SERVER", "READ DATA $input")

            // Client에게 보내줌
            // header 부분
            printer.println("HTTP/1.1 200 OK")
            printer.println("Content-Type: text/html\r\n")

            // body 부분
            printer.println("<h1>Hello World</h1>")
            printer.println("\r\n")
            printer.flush()
            printer.close()

            reader.close()

            socket.close()
        }.start()
    }
}