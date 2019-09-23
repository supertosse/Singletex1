package main

import java.io.File

fun main(args : Array<String>){
    var posts:ArrayList<Post> = BankStatementReader.read(File("files/KTOUTS0301-9.pdf"))
    posts.forEach{
        println(it.toString())
    }
}
