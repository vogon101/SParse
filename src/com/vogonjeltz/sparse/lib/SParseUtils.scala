package com.vogonjeltz.sparse.lib

/**
  * Created by fredd on 27/10/2016.
  */
object SParseUtils {

  def sanitiseString(text: String) = text.replaceAll("\n", "<nl>").replaceAll("\r", "<cr>")

}
