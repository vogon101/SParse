package com.vogonjeltz.sparse.lib.token

import java.util.regex.{Matcher, Pattern}

/**
  * Created by Freddie on 31/08/2016.
  */

abstract class TokenDef {

  val name: String
  val pattern: String
  val tokenizer: Tokenizer

  tokenizer.addTokenDef(this)

  def checkMatch(partial: String): Boolean

  def isFinishedMatch(partial: String) : Boolean

  def fromText (text: String): Token = new Token(text, this)

}


case class SimpleTokenDef(name: String)(val pattern: String)(implicit val tokenizer: Tokenizer)
  extends TokenDef {

  def checkMatch(partial:String) = {
    //println(s"Simple token matching $pattern to partial $partial")
    if (partial.length > pattern.length) false
    else pattern.substring(0, partial.length) == partial
  }

  def isFinishedMatch(partial: String) = partial == pattern

}

case class RegexTokenDef(name : String)(val pattern: String)(implicit val tokenizer: Tokenizer)
  extends TokenDef {

  val compiled = Pattern.compile(pattern)

  def checkMatch(partial:String): Boolean = {

    val matcher = compiled.matcher(partial)

    matcher.matches() || matcher.hitEnd()

  }

  def isFinishedMatch(partial:String): Boolean = {

    val matcher = compiled.matcher(partial)

    matcher.matches()

  }

}