package com.vogonjeltz.sparse.lib.parse.parsers

import com.vogonjeltz.sparse.lib.parse.SParseParser
import com.vogonjeltz.sparse.lib.token.TokenStream

/**
  * SequentialDropParser
  *
  * Created by fredd
  */
abstract class SequentialDropParser[+R, +I2] extends SParseParser[R]{

}

class DropRightParser[+R, +I2](p1: SParseParser[R], p2: SParseParser[I2]) extends SequentialDropParser[R, I2] {

  def parse(tokenStream: TokenStream): (Option[R], Int) = {

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
        (Some(p1Ret._1.get), workingTokens.consumed)
      } else {
        (None, 0)
      }
    } else {
      (None, 0)
    }

  }

}

class DropLeftParser[+I1, +R](p1: SParseParser[I1], p2: SParseParser[R]) extends SequentialDropParser[R, I1] {

  def parse(tokenStream: TokenStream): (Option[R], Int) = {

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
        (Some(p2Ret._1.get), workingTokens.consumed)
      } else {
        (None, 0)
      }
    } else {
      (None, 0)
    }

  }

}
