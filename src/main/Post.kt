package main

class Post(val amount: Float, val description: String, val isIncoming: Boolean, val type:PostType){

    enum class PostType {HOUSING, GROCERIES, TRANSPORTATION, CHARITY, OTHER}

    override fun toString():String{
       return "Amount: " + this.amount + " Description: " + this.description + " Incoming: " + this.isIncoming + " Type: " + this.type
    }

    companion object{
        private val patternSum = "\\s(\\d+\\.)*\\d+,\\d{2}\\s".toRegex()
        private val patternIsIncoming = "Fra: ".toRegex()

        fun createPost(post:String):Post{
            val resultSums = patternSum.find(post)
            var amount = 0f
            if(resultSums != null) amount = resultSums.value.replace(".", "").replace(",", ".").toFloat()

            var description = ""
            val removeFromDescription = BankStatementReader.patternIsPost.find(post)
            if(removeFromDescription != null) description = post.replace(removeFromDescription.value, "")

            val isIncoming = patternIsIncoming.containsMatchIn(post)

            val type = getPostType(description)

            return Post(amount, description, isIncoming, type)
        }

        private fun getPostType(description: String):PostType{
            val patternGroceries = "(rema|kiwi|coop|meny|joker|)+".toRegex(RegexOption.IGNORE_CASE)
            val patternTransportation = "(ruter|vy app|norway buss|nettbuss|nsb)+".toRegex(RegexOption.IGNORE_CASE)
            val patternCharity = "(redd barna|hope for justice)+".toRegex(RegexOption.IGNORE_CASE)
            val patternHousing = "(grebstad kjell|gjensidige)+".toRegex(RegexOption.IGNORE_CASE)

            if(patternHousing.containsMatchIn(description)){
                return PostType.HOUSING
            }
            if(patternGroceries.containsMatchIn(description)){
                return PostType.GROCERIES
            }
            if(patternTransportation.containsMatchIn(description)){
                return PostType.TRANSPORTATION
            }
            if(patternCharity.containsMatchIn(description)){
                return PostType.CHARITY
            }
            return PostType.OTHER

        }
    }
}