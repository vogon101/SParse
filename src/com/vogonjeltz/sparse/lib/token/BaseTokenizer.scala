package com.vogonjeltz.sparse.lib.token

/**
  * Created by Freddie on 31/08/2016.
  */
class BaseTokenizer extends Tokenizer {

  val L_BRACK  = "L_BRACK" ~  "("
  val R_BRACK  = "R_BRACK" ~  ")"

  val L_C_BRACK = "L_C_BRACK" ~ "{"
  val R_C_BRACK = "R_C_BRACK" ~ "}"

  val NAME = "NAME" $ "[A-Za-z_]+[A-Za-z0-9_\\-]*"

  val SEMI_COLON = "SEMI_COLON" ~ ";"

  val SYMB = "SYMBOL" $ "[=!£\\$%\\^\\&\\*\\-\\+\\~#@?<>]+"

  val DOT = "DOT" ~ "."

  val STRING = "STR" $ "\"[^\"^\n^\r]*\""

  val FLOAT_LITERAL = "FLOAT" $ "([+-]?(\\d+\\.)?\\d+[fid]?)"

  val INLINE_COMMENT = "INLINE_COMMENT" $ "//.*[\n\r]+"

  val NEW_LINE = "NL" ~ "\n"

}
