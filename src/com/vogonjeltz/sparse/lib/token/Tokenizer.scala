package com.vogonjeltz.sparse.lib.token

import com.vogonjeltz.sparse.lib.ParsingLogger
import com.vogonjeltz.sparse.lib.exception.TokenizerException

import scala.collection.mutable.ListBuffer

/**
  * Created by Freddie on 31/08/2016.
  */
abstract class Tokenizer (val Log: ParsingLogger = new ParsingLogger()){

  implicit val tokenizer: Tokenizer = this

  def T (n: String)(p:String) = SimpleTokenDef(n)(p)
  def RT(n: String)(p:String) = RegexTokenDef(n)(p)

  implicit class TokenName (n: String) {
    def ~ (p: String) = SimpleTokenDef(n)(p)

    def $ (p: String) = RegexTokenDef(n)(p)
  }
  private var _tokenDefs: List[TokenDef] = List()

  def addTokenDef(tokenDef: TokenDef) =
    _tokenDefs = tokenDef :: _tokenDefs

  def tokenDefs:List[TokenDef] = _tokenDefs.reverse


  //TODO: Convert to a more functional style
  //TODO: Count lines
  def tokenize(source : String): List[Token] = {

    Log.n("Tokenising started")
    Log.n(s"Tokens $tokenDefs")

    val tokens = ListBuffer[Token]()

    var tokenBuilder = ""

    var workingTokenDefs = tokenDefs

    var workingSource = source

    def consume(c : Char) = {
      Log.v(s"Consumed char $c")
      workingSource = workingSource.substring(1)
      tokenBuilder += c
    }


    def reset() = {
      tokenBuilder = ""
      workingTokenDefs = tokenDefs
    }

    def runChar(c : Char) = {
      val newToken = tokenBuilder + c

      val newTokenDefs = workingTokenDefs.filter(
        _ checkMatch newToken
      )

      Log.v(newTokenDefs.toString())
      Log.v(c.toString)

      if (newTokenDefs.isEmpty) {

        if (workingTokenDefs.length == 1) {
          tokens.append(
            workingTokenDefs.head.fromText(tokenBuilder)
          )
          Log.n(s"Found token $tokenBuilder (newTokenDefs was empty)")
          if (c.isWhitespace) consume(c)
          reset()
        }
        else {

          val simpleTokenDefs = workingTokenDefs.filter (
            _ match {
              case _: SimpleTokenDef => true
              case _ => false
            }
          )

          if (simpleTokenDefs.length == 1) {
            tokens.append(simpleTokenDefs.head.fromText(tokenBuilder))
            Log.n(s"Found token $tokenBuilder (chose simple tokendef over others)")
            if (c.isWhitespace) consume(c)
            reset()
          } else {
            Log.e("Ambiguous token for working string " + tokenBuilder)
            throw new TokenizerException("Ambiguous token for working string " + tokenBuilder)
          }


        }

      } else if (workingSource.length == 1) {
        Log.n("End of source token search")

        if (newTokenDefs.length == 1) {
          tokens.append(
            newTokenDefs.head.fromText(newToken)
          )
          Log.n(s"Found token $newToken")
          consume(c)
        }
        else {
          throw new TokenizerException("Ambiguous token for working string at end of file : " + tokenBuilder)
        }

      } else {
        consume(c)
        workingTokenDefs = newTokenDefs
      }
    }

    while (workingSource.length > 0) {

      val c = workingSource.head

      if (tokenBuilder == "" && c.isWhitespace){
        consume(c)
        reset()
      } else {
        runChar(c)
      }


    }

    tokens.toList

  }

}




