package com.vogonjeltz.sparse.lib.parse

import com.vogonjeltz.sparse.lib.token.{Token, TokenStream}

/**
  * SParseParser
  *
  * Created by fredd
  */
abstract class SParseParser[R] {

  def parse(tokenStream: TokenStream): (Option[R], Int)



}
