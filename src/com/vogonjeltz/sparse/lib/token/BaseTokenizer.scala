package com.vogonjeltz.sparse.lib.token

/**
  * Created by Freddie on 31/08/2016.
  */
class BaseTokenizer extends Tokenizer {

  val LBRACK  = "LBRACK" ~  "("
  val RBRACK  = "RBRACK" ~  ")"
  val EQ      = "EQ"     ~  "="
  val EQEQ    = "EQEQ"   ~  "=="
  val NAME    = "NAME"   ? "([a-zA-Z_\\+\\-\\*^%#~@\\?\\/]+[a-zA-Z0-9_\\+\\-\\*^%#~@\\?\\/]*)"
  val DOT     = "DOT"    ~  "."

}
