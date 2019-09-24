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

    fun getControlSum():Float{
        return getSumIncomingPosts() - getSumOutgoingPosts() - balanceIn + balanceOut
    }

    fun isCorrectControlSum():Boolean{
        return getControlSum() < 1 && getControlSum() > -1
    }

    fun getGetPostsOfType(type:Post.PostType):ArrayList<Post>{
        return ArrayList(posts.filter { it.type == type })
    }
}