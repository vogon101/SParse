package com.vogonjeltz.sparse.lib.token

import com.vogonjeltz.sparse.lib.exception.TokenizerException

import scala.collection.mutable.ListBuffer

/**
  * Created by Freddie on 31/08/2016.
  */
trait Tokenizer {

  implicit val tokenizer: Tokenizer = this

  def T (n: String)(p:String) = SimpleTokenDef(n)(p)
  def RT(n: String)(p:String) = RegexTokenDef(n)(p)

  implicit class TokenName (n: String) {
    def ~ (p: String) = SimpleTokenDef(n)(p)

    def ? (p: String) = RegexTokenDef(n)(p)
  }

  private var _tokenDefs: List[TokenDef] = List()

  def addTokenDef(tokenDef: TokenDef) =
    _tokenDefs = tokenDef :: _tokenDefs

  def tokenDefs:List[TokenDef] = _tokenDefs.reverse

  def tokenize(source : String): List[Token] ={

    val tokens = ListBuffer[Token]()

    //Stores the tokens that haven't yet been ruled out
    var workingTokens = tokenDefs

    //Stores the working token string
    val tokenBuilder = new StringBuilder()

    //Loop through the string with an index
    for ((c, i) <- source.zipWithIndex) {

      tokenBuilder += c

      println(workingTokens)
      println(c)
      println(tokenBuilder)
      println(tokens)
      println()

      //TODO: Deal with whitespace
      //TODO: Read an actual book to figure out what I am doing here!

      //Rule out tokens that could never match
      workingTokens = workingTokens.filter(
        _ checkMatch tokenBuilder.toString
      )

      //If there are no tokens left, throw an exception
      if (workingTokens.isEmpty) {
        throw new TokenizerException(s"No token found for character $c")
      }
      //If there is only one token left, check if that is a complete match
      //If so, we can reset the loop and store the token
      else if (workingTokens.length == 1) {
        if (workingTokens.head.isFinishedMatch(tokenBuilder.toString())) {
          tokens += new Token(tokenBuilder.toString(), workingTokens.head)
          workingTokens = tokenDefs
          tokenBuilder.clear()
        }
      }



    }

    tokens.toList

  }

}




