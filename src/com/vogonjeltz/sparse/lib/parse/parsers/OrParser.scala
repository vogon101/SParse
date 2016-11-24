package com.vogonjeltz.sparse.lib.parse.parsers

import com.vogonjeltz.sparse.lib.parse.SParseParser
import com.vogonjeltz.sparse.lib.token.TokenStream

/**
  * Created by Freddie on 29/10/2016.
  */
class OrParser[+I1](p1: SParseParser[I1], p2: SParseParser[I1]) extends SParseParser[I1] {

  override def parse(tokenStream: TokenStream): (Option[I1], Int) = {

    val p1ret = p1.parse(tokenStream)

    if (p1ret._1.isDefined) p1ret
    else p2.parse(tokenStream)

  }

  //override def toString: String = s"OR ( $p1, $p2 )"

}
