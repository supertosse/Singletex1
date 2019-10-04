package main

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
        return toString(false)
    }

    fun toString(excelMode:Boolean): String {
        if(!isCorrectControlSum()){
            return "WARNING: control sum is incorrect"
        }

        var t1 = "\t"
        var t2 = "\t\t"
        var t3 = "\t\t\t"
        var t4 = "\t\t\t\t"
        var t5 = "\t\t\t\t\t"
        var t0 = "\t"
        if(excelMode){
            t1 = "\t"
            t2 = "\t"
            t3 = "\t"
            t4 = "\t"
            t5 = "\t"
            t0 = ""
        }

        val groceries = getPostsOfType(Post.PostType.GROCERIES)
        val other = getPostsOfType(Post.PostType.OTHER)
        var gg = ArrayList<Post>()
        gg.addAll(other)
        gg.addAll(getPostsOfType(Post.PostType.CHARITY))
        val transportation = getPostsOfType(Post.PostType.TRANSPORTATION)
        val housing = getPostsOfType(Post.PostType.HOUSING)

        var n = arrayOf(groceries.size, gg.size).max()
        if(n == null){n = 0}

        var string = "\nHousing" + t2 + "Groceries" + t1 + "Transportation" + t1 + "Other\n"

        for(i in 0 until n){
           string += p(housing, i, t0) + t2 +
                   p(groceries, i, t0) + t3 +
                   p(transportation, i, t0) + t4 +
                   p(gg, i, t0)  + t2 + p2(gg, i, t0) + "\n"
        }

        return string
    }

    private fun p(list:ArrayList<Post>, i:Int, t1:String):String{
        if(list.size > i){
            return list[i].amount.toInt().toString()
        }
        return t1
    }
    private fun p2(list:ArrayList<Post>, i:Int, t1:String):String{
        if(list.size > i){
            return list[i].description
        }
        return t1
    }
}