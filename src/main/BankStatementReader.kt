package main

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

class BankStatementReader{

    companion object{
        val patternIsPost = "\\s\\d{4}\\s(\\d+\\.)*\\d+,\\d{2}\\s\\d{4}".toRegex()
        val patternIsNotPost = "overfør\\w*side".toRegex()
        val patternIsBalanceIn = "Saldo fr".toRegex()
        val patternIsBalanceOut = "Saldo i".toRegex()
        private var patternAmount = "\\s(\\d+\\.)?\\d+,\\d{2}".toRegex()
        fun read(file: File):BankStatement{
            val posts = ArrayList<Post>()
            var balanceIn = -1f
            var balanceOut = -1f

            try {
                val doc = PDDocument.load(file)
                val strip = PDFTextStripper()
                val text = strip.getText(doc)
                doc.close()
                val lines = text.lines()

                for (line in lines){
                    if(patternIsNotPost.containsMatchIn(line)){
                       continue;
                    }
                    if(patternIsBalanceIn.containsMatchIn(line)){
                        val bIn = patternAmount.find(line)
                        if(bIn != null){ balanceIn = bIn.value.replace(".", "").replace(",", ".").toFloat() }
                    }
                    else if(patternIsBalanceOut.containsMatchIn(line)){
                        val bOut = patternAmount.find(line)
                        if(bOut != null){ balanceOut = bOut.value.replace(".", "").replace(",", ".").toFloat() }
                    }
                    else if(patternIsPost.containsMatchIn(line)){
                        posts.add(Post.createPost(line))
                    }
                }
            }catch (e:Exception){
                println(e)
            }

            return BankStatement(posts, balanceIn, balanceOut)
        }
    }
}
