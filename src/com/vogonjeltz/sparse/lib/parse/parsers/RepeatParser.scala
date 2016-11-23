package com.vogonjeltz.sparse.lib.parse.parsers

import com.vogonjeltz.sparse.lib.parse.SParseParser
import com.vogonjeltz.sparse.lib.token.TokenStream

import scala.collection.mutable.ListBuffer

/**
  * Created by Freddie on 23/11/2016.
  */
class RepeatParser[I1](val p1: SParseParser[I1], val min: Int = 0) extends SParseParser[List[I1]]{

  override def parse(tokenStream: TokenStream): (Option[List[I1]], Int) = {

    val workingTokens = tokenStream.split()

    val result = ListBuffer[I1]()
    var lastRet = p1.parse(workingTokens)

    while (lastRet._1.isDefined) {
      result.append(lastRet._1.get)
      workingTokens.consume(lastRet._2)
      lastRet = p1.parse(workingTokens)
    }

    if (result.length > min) (Some(result.toList), workingTokens.consumed)
    else (None, 0)

  }

  override def toString: String = s"Repeat( $p1, $min )"

}
