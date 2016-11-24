package com.vogonjeltz.sparse.lib.parse.parsers

import com.vogonjeltz.sparse.lib.parse.SParseParser
import com.vogonjeltz.sparse.lib.token.TokenStream

/**
  * Created by Freddie on 23/11/2016.
  */
class TransformParser[I1,T >: I1, +R](val p1: SParseParser[I1])(val transform : (T) => R) extends SParseParser[R]{

  override def parse(tokenStream: TokenStream): (Option[R], Int) = {

    val workingTokens = tokenStream.split()
    val pRet = p1.parse(workingTokens)

    (pRet._1.map(transform), pRet._2)

  }

  //override def toString: String = s"Transform( $p1 )"

}
