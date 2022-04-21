import java.net.Socket
import kotlin.concurrent.thread
import java.io.File

fun main(){
    var stored_ips : MutableList<String> = mutableListOf()
    var stored_ports : MutableList<Int> = mutableListOf()
    var stored_names : MutableList<String> = mutableListOf()
    var ip = ""
    var port = 0
    print("Load or enter New? ")
    val ans_1 = readLine()
    if (ans_1 == "l"){
        val loaded = File("adresses.txt").readLines()
        for (i in loaded){
            var temporary = i.split(":")
            stored_names += temporary[0]
            stored_ips += temporary[1]
            stored_ports += temporary[2].toInt()
        }
        while (true){
            print("Where to connect? ")
            val join = readLine()
            if (join in stored_names){
                val index = stored_names.indexOf(join)
                ip = stored_ips[index]
                port = stored_ports[index]
                println(ip)
                println(port)
                break
            }else{
                stored_names.forEach(){
                    println(it)
                }
            }
        }
    }else if (ans_1 == "n"){
        println("ip: ")
        ip = readLine()!!
        println("port: ")
        port = readLine()!!.toInt()
        print("Do you want to save? ")
        val new = readLine()
        if (new == "y"){
            print("how to save? ")
            val ans_2 = readLine()
            val saving = ans_2 + ":" + ip + ":" + port.toString()
            var new : MutableList<String> = File("adresses.txt").readLines().toMutableList()
            new += saving
            val file = File("adresses.txt")
            var writing = ""
            for (i in new){
                writing += (i + "\n")
            }
            file.writeText(writing)
            println("Saved")
        }else if(new == "n"){

        }

    }
    
    
    val client = Socket(ip, port)
    print("Name: ")
    var name : String? = readLine()
    client.getOutputStream().write(("!n"+name).toByteArray() + 10)
    var l = false
    thread{
        while(true){
            var input = readLine()!!
            if (input == "!leave"){
                var leave2 = "--$name left--"
                client.getOutputStream().write(leave2.toByteArray() + 10)
                var leave : ByteArray = byteArrayOf(33, 108)
                client.getOutputStream().write(leave)
                l = true
                break
            }else if(input == "!save"){
                var save :ByteArray = byteArrayOf(33, 115)
                client.getOutputStream().write(save)
            }else if(input == "!load"){
                var load :ByteArray = byteArrayOf(33, 118)
                client.getOutputStream().write(load)
            }else if (input == "!whoami"){
                println("Your name is $name")
            }else if (input == "!online"){
                var online : ByteArray = byteArrayOf(33, 111)
                client.getOutputStream().write(online)
            }else{
                var input2 = (name+": "+input).toByteArray()
                client.getOutputStream().write(input2 + 10)
            }
        }
    }
    thread{
        
        while(true){
            if (l == true){
                break
            }
            var message = client.getInputStream().read()

            if(message != 10){
                print(message.toChar())
            }else{
                print("\n")
            }
            //println(message)
        }
    }
}
