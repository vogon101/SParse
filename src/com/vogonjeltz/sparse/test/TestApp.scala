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

      text = "freddie"

      println;println

      val parser = new TestParser(new ParsingLogger(2))
      println(parser.program)
      val ret = parser.parse(parser.program, text)
      println(ret)


    } catch {
      case e: TokenizerException => println(e.getMessage)
    }

  }
}

class TestParser(val Log: ParsingLogger) extends GrammarParser {

  val tokenizer = new SLangTokenizer(Log)

  import tokenizer._

  lazy val program: Parser[Program] = (line+) ^^ (X => new Program(X))

  lazy val line: Parser[Line] = element

  lazy val element: Parser[Element] = (
        functionDef
      | ident
    )

  val ident: Parser[Ident] = repsep(NAME, DOT) ^^ ((X: List[Token]) => {new Ident(X.map(_.text))})

  val nameList: Parser[List[Ident]] = repsep(NAME, COMMA) ^^ ((X: List[Token]) => X.map(Y => new Ident(List(Y.text))))

  val functionDef: Parser[FunctionDef] = ((nameList <~ F_ARROW) ~ line) ^^ (X => new FunctionDef(X._1, X._2))

}

class Program (lines: List[Line]) {

  override def toString: String = "Program( " + lines.zipWithIndex.map(X => X._1).mkString("\n") + " )"

}

abstract class Line

abstract class Element extends Line

class Ident(layers: List[String]) extends Element

class FunctionDef(names: List[Ident], code: Line) extends Element