package com.vogonjeltz.sparse.lib.token

/**
  * TokenStream
  *
  * Created by fredd
  */
class TokenStream (val originalTokens: List[Token]){

  var workingTokens = originalTokens

  private var _consumed = 0

  def consumed = _consumed

  def get(offset: Int = 0) = workingTokens(offset)

  def getList(endOffset: Int = 0) = workingTokens.slice(0, endOffset+1)

  def length = workingTokens.length

  def consume(count: Int) = {
    _consumed += count
    workingTokens = workingTokens.slice(count, workingTokens.length)
  }

  def split() = new TokenStream(workingTokens)

}
