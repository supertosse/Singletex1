package main

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection



class BankStatement(var posts: ArrayList<Post>, val balanceIn:Float, val balanceOut:Float){

    fun getOutgoingPosts():ArrayList<Post>{
        return ArrayList(posts.filter { !it.isIncoming })
    }

    fun getIncomingPosts():ArrayList<Post>{
        return ArrayList(posts.filter { it.isIncoming })
    }

    fun getSumOutgoingPosts():Float{
       return getOutgoingPosts().map { it.amount }.reduce{acc, n -> acc + n}
    }

    fun getSumIncomingPosts():Float{
        return getIncomingPosts().map { it.amount }.reduce{acc, n -> acc + n}
    }

    fun getControlSum():Float{
        return balanceIn + getSumIncomingPosts() - getSumOutgoingPosts() - balanceOut
    }

    fun isCorrectControlSum():Boolean{
        return getControlSum() < 1 && getControlSum() > -1
    }

    fun getPostsOfType(type:Post.PostType):ArrayList<Post>{
        return ArrayList(posts.filter { it.type == type })
    }

    override fun toString(): String {
        if(!isCorrectControlSum()){
            return "WARNING: control sum is incorrect"
        }

        val housing = getPostsOfType(Post.PostType.HOUSING)
        val groceries = getPostsOfType(Post.PostType.GROCERIES)
        val charity = getPostsOfType(Post.PostType.CHARITY)
        val transportation = getPostsOfType(Post.PostType.TRANSPORTATION)
        val other = getPostsOfType(Post.PostType.OTHER)

        var n = arrayOf(groceries.size, other.size).max()
        if(n == null){n = 0}

        var heading = "\nHousing\tGroceries\tTransportation\tCharity\t\tOther\n"
        var string =""
        for(i in 0 until n){
           string += p(housing, i) +
                   p(groceries, i) +
                   p(transportation, i) +
                   p(charity, i) +
                   "\t"+
                   p(other, i) + p2(other, i) + "\n"
        }

        val selection = StringSelection(string)
        val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
        clipboard.setContents(selection, selection)

        return heading + string
    }

    private fun p(list:ArrayList<Post>, i:Int):String{
        if(list.size > i){
            return list[i].amount.toInt().toString() + "\t"
        }
        return "\t"
    }
    private fun p2(list:ArrayList<Post>, i:Int):String{
        if(list.size > i){
            return list[i].description
        }
        return "\t"
    }
}