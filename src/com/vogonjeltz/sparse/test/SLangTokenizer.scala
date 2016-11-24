package com.vogonjeltz.sparse.test

import com.vogonjeltz.sparse.lib.ParsingLogger
import com.vogonjeltz.sparse.lib.token.Tokenizer

/**
  * Created by Freddie on 24/10/2016.
  */
class SLangTokenizer(parsingLogger: ParsingLogger) extends Tokenizer(parsingLogger){

  override val PARSE_NEWLINE_AS_TOKEN = true

  val L_BRACK  = "L_BRACK" ~ "("
  val R_BRACK  = "R_BRACK" ~  ")"

  val L_C_BRACK = "L_C_BRACK" ~ "{"
  val R_C_BRACK = "R_C_BRACK" ~ "}"

  val NAME = "NAME" $ "[A-Za-z_]+[A-Za-z0-9_\\-]*"

  val F_ARROW = "F_ARROW" ~ "=>"

  val COMMA = "COMMA" ~ ","

  val SEMI_COLON = "SEMI_COLON" ~ ";"

  val SYMB = "SYMBOL" $ "[=!£\\$%\\^\\&\\*\\-\\+\\~#@?<>]+"

  val DOT = "DOT" ~ "."

  val STRING = "STR" $ "\""+"""([^"\x00-\x1F\x7F\\]|\\[\\'"bfnrt]|\\u[a-fA-F0-9]{4})*"""+"\""

  val FLOAT_LITERAL = "FLOAT" $ "([+-]?(\\d+\\.)?\\d+[fid]?)"

  val INLINE_COMMENT = "INLINE_COMMENT" $ "//[^\n^\r]*"

  val NL = "NEW_LINE" ~ "\n"


}
