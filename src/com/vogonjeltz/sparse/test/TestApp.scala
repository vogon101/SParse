package com.vogonjeltz.sparse.test

import com.vogonjeltz.sparse.lib.ParsingLogger
import com.vogonjeltz.sparse.lib.token._

/**
  * Created by Freddie on 31/08/2016.
  */
object TestApp {

  def main(args: Array[String]) {
    val a = new SLangTokenizer(new ParsingLogger(4))
    println()
    val tokens = a.tokenize("my.variable==my.other.variable")
    println();println()
    println(tokens)
  }

}

class TestTokenizer(parsingLogger: ParsingLogger) extends Tokenizer(parsingLogger){

  val myString = "myString" ~ "MY"

  val myOtherString = "myOtherString" ~ "MY OTHER"

  val regex = "regex" $ "[A-Z]+"

}
