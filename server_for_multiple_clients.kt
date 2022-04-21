import java.net.ServerSocket
import kotlin.concurrent.thread
import java.net.Socket
import java.io.File

var clients : MutableList<Socket> = mutableListOf()
var names : MutableList<String> = mutableListOf()

var mentes1 : MutableList<String> = mutableListOf()
var mentes2 : MutableList<String> = mutableListOf()
var mentes3 = ""
val database = File("database.txt")

fun main(){
    val server = ServerSocket(12345)
    
    var i = 0
    
    var szo = ""
    var command = ""
    var alany = ""

    thread{
        while (true){
            command = readLine()!!
            if (":" in command){
                command = command.split(":")[0]
                alany = command.split(":")[1]
            }
            if (command == "!save"){
                save()
            }//else if(command == "!ban"){
            //    val index = names.index(alany)
            //    clients.remove(clients[index])
//
            //}
        }
    }


    while(true){
        println("-- --$i")
        
        val client = server.accept()
        clients.add(client)
        println("--")
        
        thread(name = "client"){
            var o = false
            var id = i-1
            var name = ""
            var l = false
    
            var m = false
            var v = false
            var n = false
            var f = false
            var ff = false
            var fff = false
            var namesetting = false
            println("new thread with id = $id")
            while(true){
                
                var input = client.getInputStream().read()
                //println(input)
                
                if (namesetting == true){
                    if (input != 10){
                       name += input.toChar()
                    }else{
                        names += name
                        println("name = $name")
                        namesetting = false
                    }
                    
                }

                if (n == true){
                    if (input == 110){
                        namesetting = true
                    }
                    n = false

                }

                if (l == true){
                    if (input == 108){
                        //var a = clients[id]
                        client.close()
                        clients.remove(client)
                        names.remove(name)
                        break
                    }else{
                        l = false
                        println("l= false")
                    }
                }
                if(m == true){
                    if (input == 115){
                        save()
                    }else{
                        m = false
                        println("m = false")
                    }
                }

                if (o == true){
                    if (input == 111){
                        println("online")
                        for (name in names){
                            client.getOutputStream().write(name.toByteArray() + 10)
                        }
                        o = false
                    }
                }
                
                if (v == true){
                    if (input == 118){
                        println("load")
                        val data : List<String> = database.readLines()
                        client.getOutputStream().write(byteArrayOf(45, 45, 108, 111, 97, 100, 101, 100, 45, 45, 10))
                        
                        for (i in data){
                            client.getOutputStream().write((i + "\n").toByteArray())
                        }
                    }else{
                        v = false
                        println("v = false")
                    }
                }

                
                if(input == 33){
                    l = true
                    m = true
                    v = true
                    o = true
                    n = true
                    f = true
                    ff = true 
                    fff = true
                    println("! received")
                }
                
                if(fff!=true){
                    print(input.toChar())
                    for (client in clients){
                        client.getOutputStream().write(input)
                    }
                }

                if (ff != true){
                    fff = false
                }

                if (f == true){
                    f=false
                    ff = false
                }

                if (input != 10){
                    szo += input.toChar()
                }else{
                    szo += "\n"
                    mentes1 += szo
                    szo = ""
                }
            }
        }
        i++
        println("--")
    }
}

fun save(){
    val data : List<String>  = database.readLines()
    println("data = ")
    for (i in data){
        println(i)
    }
    for (i in data){
        mentes2 += (i+"\n")
    }
    for (i in mentes1){
        mentes2 += i
    }
    for (i in mentes2){
        mentes3 += i
    }
    database.writeText(mentes3)
    println(mentes3)
    mentes1 = mutableListOf()
    mentes2 = mutableListOf()
    mentes3 = ""
}
