package com.vogonjeltz.sparse.test

import com.vogonjeltz.sparse.lib.ParsingLogger
import com.vogonjeltz.sparse.lib.token.Tokenizer

/**
  * Created by Freddie on 24/10/2016.
  */
class SLangTokenizer(parsingLogger: ParsingLogger) extends Tokenizer(parsingLogger){

  val LBRACK  = "LBRACK" ~  "("
  val RBRACK  = "RBRACK" ~  ")"
  val NAME    = "NAME"   $
    "(([a-zA-Z_\\+\\-\\*^%#~@\\?\\/!\\$\\%£<>=]+[a-zA-Z0-9_\\+\\-\\*^%#~@\\?\\/!\\$\\%£<>=]*))(\\.([a-zA-Z_\\+\\-\\*^%#~@\\?\\/!\\$\\%£<>=]+[a-zA-Z0-9_\\+\\-\\*^%#~@\\?\\/!\\$\\%£<>=]*))*"
  //TODO: Fix situations like my.name==his.name which would be read as one identifier

}
