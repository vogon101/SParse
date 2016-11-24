package com.vogonjeltz.sparse.lib.parse

import com.vogonjeltz.sparse.lib.parse.parsers._
import com.vogonjeltz.sparse.lib.token.{Token, TokenStream, Tokenizer}

/**
  * GrammarParser
  *
  * Created by fredd
  */
abstract class GrammarParser {

  val tokenizer: Tokenizer

  type Parser[+R] = SParseParser[R]

  //FIXME: Stackoverflow on recursive parser definitions
  //IDEA: Maybe have an empty parser and then build up from there?
  //IDEA: Perhaps move combinator methods to SParseParser

  implicit class ParserPartial[+I1](val p1: SParseParser[I1]) {

    def ~ [I2](p2: => SParseParser[I2]): Parser[(I1, I2)] = new AndParser[I1,I2](p1, p2)

    def | [T >: I1](p2: SParseParser[T]): Parser[T] = new OrParser[T](p1, p2)

    def ^^ [R](transform: (I1) => R): Parser[R] = new TransformParser[I1, I1, R](p1)(transform)

    def + (min: Int) :Parser[List[I1]] = new RepeatParser[I1](p1, min)

    def + :Parser[List[I1]] = new RepeatParser[I1](p1)

    def ~> [I2](p2: SParseParser[I2]):Parser[I2] = new DropLeftParser[I1, I2](p1, p2)

    def <~ [I2](p2: SParseParser[I2]):Parser[I1] = new DropRightParser[I1, I2](p1, p2)

  }

  def rep[A](sParseParser: SParseParser[A], min: Int = 0) = {
    new RepeatParser[A](sParseParser, min)
  }

  def repsep[A, B](mainParser: SParseParser[A], sepParser: SParseParser[B], min: Int = 0): SParseParser[List[A]] =
    new RepSepParser[A, B](mainParser, sepParser, min)

  def parse[A](sParseParser: SParseParser[A], source: String): Option[A] =
    parse(sParseParser, tokenizer.tokenize(source))


  def parse[A](sParseParser: SParseParser[A], tokens: List[Token]):Option[A] = {
    val stream = new TokenStream(tokens)
    val retVal = sParseParser.parse(stream)
    retVal._1
  }

}
