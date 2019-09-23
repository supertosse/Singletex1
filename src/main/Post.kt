package main

class Post(val amount: Float, val description: String, val isIncoming: Boolean){

    override fun toString():String{
       return "Amount: " + this.amount + " Description: " + this.description + " Incoming: " + isIncoming
    }

    companion object{
        private val patternSum = "\\s(\\d+\\.)*\\d+,\\d{2}\\s".toRegex()
        private val patternIsIncoming = "Fra: ".toRegex()

        fun createPost(post:String):Post{
            val resultSums = patternSum.find(post)
            var amount = 0f
            if(resultSums != null) amount = resultSums.value.replace(".", "").replace(",", ".").toFloat()

            var description = "Sample description"
            val removeFromDescription = BankStatementReader.patternIsPost.find(post)
            if(removeFromDescription != null) description = post.replace(removeFromDescription.value, "")

            val isIncoming = patternIsIncoming.containsMatchIn(post)

            return Post(amount, description, isIncoming)
        }
    }
}