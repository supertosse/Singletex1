package main

import java.io.File

fun main(args : Array<String>){
    var bankStatement:BankStatement = BankStatementReader.read(File("files/KTOUTS0301-9.pdf"))
    bankStatement.posts.forEach{
        println(it.toString())
    }
    println("Outgoing: " + bankStatement.getSumOutgoingPosts())
    println("Incoming: " + bankStatement.getSumIncomingPosts())
    println("BalanceOut: " + bankStatement.balanceOut)
    println("Balance in: " + bankStatement.balanceIn)
    println("Control sum: " + bankStatement.getContolSum())
}
