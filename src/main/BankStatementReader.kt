package main

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

class BankStatementReader{

    companion object{
        val patternIsPost = "\\s\\d{4}\\s(\\d+\\.)*\\d+,\\d{2}\\s\\d{4}".toRegex()
        fun read(file: File):ArrayList<Post>{
            val posts = ArrayList<Post>()

            try {
                val doc = PDDocument.load(file)
                val strip = PDFTextStripper()
                val text = strip.getText(doc)
                doc.close()
                val lines = text.lines()

                for (line in lines){
                    if(patternIsPost.containsMatchIn(line)){
                        posts.add(Post.createPost(line))
                    }
                }
            }catch (e:Exception){
                println(e)
            }

            return posts
        }
    }
}
