package main

class BankStatement(var posts: ArrayList<Post>, val balanceIn:Float, val balanceOut:Float){

    fun getOutgoingPosts():ArrayList<Post>{
        return ArrayList(posts.filter { it.isIncoming })
    }

    fun getIncomingPosts():ArrayList<Post>{
        return ArrayList(posts.filter { !it.isIncoming })
    }

    fun getSumOutgoingPosts():Float{
       return getOutgoingPosts().map { it.amount }.reduce{acc, n -> acc + n}
    }

    fun getSumIncomingPosts():Float{
        return getIncomingPosts().map { it.amount }.reduce{acc, n -> acc + n}
    }

    fun getContolSum():Float{
        return getSumIncomingPosts() - getSumOutgoingPosts() - balanceIn + balanceOut
    }
}