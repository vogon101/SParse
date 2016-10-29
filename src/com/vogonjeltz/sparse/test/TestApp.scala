package com.vogonjeltz.sparse.test

import com.vogonjeltz.sparse.lib.ParsingLogger
import com.vogonjeltz.sparse.lib.exception.TokenizerException
import com.vogonjeltz.sparse.lib.parse.GrammarParser
import com.vogonjeltz.sparse.lib.token._

/**
  * Created by Freddie on 31/08/2016.
  */
object TestApp {

  def main(args: Array[String]) {

    val a = new SLangTokenizer(new ParsingLogger(1))

    try {

      var text = """
                   |//this is an inline comment
                   |function_a = () => {
                   | println("Hello world")
                   |}
                   |
                   |function_a(2)
                   |//This is another inline comment
                   |print(2)
                   |//thanks
                 """.stripMargin

      text = "() =>"

      println;println

      val parser = new TestParser(new ParsingLogger(2))
      println(parser.functionLiteral)
      val ret = parser.parse(parser.functionLiteral, text)
      println(ret)


    } catch {
      case e: TokenizerException => println(e.getMessage)
    }

  }
}

class TestParser(val Log: ParsingLogger) extends GrammarParser {

  val tokenizer = new SLangTokenizer(Log)

  import tokenizer._

  val functionLiteral = L_BRACK ~ R_BRACK ~ F_ARROW

}