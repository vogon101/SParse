package com.vogonjeltz.sparse.lib.parse.parsers

import com.vogonjeltz.sparse.lib.parse.SParseParser
import com.vogonjeltz.sparse.lib.token.TokenStream

import scala.collection.mutable.ListBuffer

/**
  * RepSepParser
  *
  * Created by fredd
  */
class RepSepParser[I1, I2](val mainParser: SParseParser[I1], val sepParser: SParseParser[I2], val min: Int = 0) extends SParseParser[List[I1]] {

  override def parse(tokenStream: TokenStream): (Option[List[I1]], Int) = {

    val workingTokens = tokenStream.split()

    val result = ListBuffer[I1]()
    var lastRet = mainParser.parse(workingTokens)

    while (lastRet._1.isDefined) {

      result.append(lastRet._1.get)
      workingTokens.consume(lastRet._2)

      val sepRet = sepParser.parse(workingTokens)
      if (sepRet._1.isEmpty) {
        if (result.length > min) return (Some(result.toList), workingTokens.consumed)
      }
      else {
        workingTokens.consume(sepRet._2)
      }

      lastRet = mainParser.parse(workingTokens)

    }

    if (result.length > min) (Some(result.toList), workingTokens.consumed)
    else (None, 0)

  }

  //override def toString: String = "RepSep( " + mainParser + ", " + sepParser + " )"

}
