package com.vogonjeltz.sparse.lib.token

import com.vogonjeltz.sparse.lib.SParseUtils

/**
  * Created by Freddie on 31/08/2016.
  */
case class Token(text: String, typ: TokenDef, lineNum: Int = -1) {

  def displayText = SParseUtils.sanitiseString(text)

  override def toString = s"Token(${typ.name} -> `$displayText` ${if (lineNum != -1) s"| $lineNum"}) "

}
