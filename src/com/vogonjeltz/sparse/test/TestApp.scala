package com.vogonjeltz.sparse.test

import com.vogonjeltz.sparse.lib.ParsingLogger
import com.vogonjeltz.sparse.lib.exception.TokenizerException
import com.vogonjeltz.sparse.lib.token._

/**
  * Created by Freddie on 31/08/2016.
  */
object TestApp {

  def main(args: Array[String]) {

    val a = new SLangTokenizer(new ParsingLogger(1))

    try {
      val tokens = a.tokenize(
        """
          |//this is an inline comment
          |function_a = () => {
          | println("Hello world")
          |}
          |
          |function_a(2)
          |//This is another inline comment
          |print(2)
          |//thanks
        """.stripMargin)
      println();println()
      tokens foreach println
      println;println
      tokens.map(_.displayText).foreach(print)

    } catch {
      case e: TokenizerException => println(e.getMessage)
    }

  }
}