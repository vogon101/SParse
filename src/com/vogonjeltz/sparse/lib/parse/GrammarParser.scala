package com.vogonjeltz.sparse.lib.parse

import com.vogonjeltz.sparse.lib.parse.parsers.AndParser
import com.vogonjeltz.sparse.lib.token.{Token, TokenStream, Tokenizer}

/**
  * GrammarParser
  *
  * Created by fredd
  */
abstract class GrammarParser {

  val tokenizer: Tokenizer

  implicit class ParserPartial[I1](val p1: SParseParser[I1]) {

    def ~ [I2](p2: SParseParser[I2]) = new AndParser[I1,I2](p1, p2)

  }

  def parse[A](sParseParser: SParseParser[A], source: String): Option[A] =
    parse(sParseParser, tokenizer.tokenize(source))


  def parse[A](sParseParser: SParseParser[A], tokens: List[Token]):Option[A] = {
    val stream = new TokenStream(tokens)
    val retVal = sParseParser.parse(stream)
    retVal._1
  }

}
