package com.vogonjeltz.sparse.lib.token

/**
  * Created by Freddie on 31/08/2016.
  */
case class Token(text: String, typ: TokenDef) {

  override def toString = s"Token('$text' | $typ)"

}
