package main

class Post(val amount: Float, val description: String){

    override fun toString():String{
       return "Amount: " + this.amount + " Description: " + this.description
    }

    companion object{
        private val patternSum = "\\s(\\d+\\.)*\\d+,\\d{2}\\s".toRegex()
        fun createPost(post:String):Post{
            val resultSums = patternSum.find(post)
            var amount = -1f
            if(resultSums != null) amount = resultSums.value.replace(".", "").replace(",", ".").toFloat()

            var description = "Sample description"
            val removeFromDescription = BankStatementReader.patternIsPost.find(post)
            if(removeFromDescription != null) description = post.replace(removeFromDescription.value, "")
            return Post(amount, description)
        }
    }
}