package org.shop.notice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.shop.notice.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url("http://192.168.219.102:8080")
            .build()

        val callback = object : Callback {
            // 요청 자체가 실패하거나 통신과정에서 오류가 났을 때
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Client", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.e("Client", "${response.body?.string()}")
                }
            }
        }

        client.newCall(request).enqueue(callback)

//        Thread {
//            try {
//                // 소켓 서버가 아닌 그냥 소켓 생성
//                val socket = Socket("192.168.219.102", 8080)
//                val printer = PrintWriter(socket.getOutputStream())
//                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//
//                printer.println("GET / HTTP/1.1")
//                printer.println("Host: 192.168.219.102:8080")
//                printer.println("User-Agent: android")
//                printer.println("\r\n")
//                printer.flush()
//
//                var input: String? = "-1"
//                while (input != null) {
//                    input = reader.readLine()
//                    Log.e("Client", input)
//                }
//                reader.close()
//                printer.close()
//                socket.close()
//            } catch (e: Exception) {
//                Log.e("Client", e.toString())
//            }
//        }.start()
    }
}