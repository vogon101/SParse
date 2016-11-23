package com.vogonjeltz.sparse.lib.parse.parsers

import com.vogonjeltz.sparse.lib.parse.SParseParser
import com.vogonjeltz.sparse.lib.token.{Token, TokenStream}

/**
  * AndParser
  *
  * Created by fredd
  */
class AndParser[I1, I2](p1: SParseParser[I1], p2: SParseParser[I2]) extends SParseParser[(I1, I2)] {

  def parse(tokenStream: TokenStream): (Option[(I1, I2)], Int) = {

    //println(s"Parsing $p1 AND $p2")

    val workingTokens = tokenStream.split()
    val p1Ret = p1.parse(workingTokens)

    if (p1Ret._1.isDefined) {
      //println(s"p1 ($p1) matched ($p1Ret)")
      workingTokens.consume(p1Ret._2)

      val p2Ret = p2.parse(workingTokens)
      if (p2Ret._1.isDefined) {
        //println(s"p2 ($p2) matched")
        workingTokens.consume(p2Ret._2)
        (Some((p1Ret._1.get, p2Ret._1.get)), workingTokens.consumed)
      } else {
        (None, 0)
      }
    } else {
      (None, 0)
    }

  }

  override def toString: String = s"And( $p1, $p2 )"

}
