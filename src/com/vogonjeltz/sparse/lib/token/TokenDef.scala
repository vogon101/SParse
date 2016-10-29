package com.vogonjeltz.sparse.lib.token

import java.util.regex.{Matcher, Pattern}

import com.vogonjeltz.sparse.lib.parse.SParseParser

/**
  * Created by Freddie on 31/08/2016.
  */

abstract class TokenDef extends SParseParser[Token]{

  val name: String
  val pattern: String
  val tokenizer: Tokenizer

  tokenizer.addTokenDef(this)

  def couldMatch(partial: String): Boolean

  def matches(partial: String) : Boolean

  def fromText (text: String, lineNum: Int): Token = Token(text, this, lineNum)

  def parse(tokenStream: TokenStream): (Option[Token], Int) = {
    if (tokenStream.get().typ == this) (Some(tokenStream.get(0)), 1)
    else (None, 0)
  }

  override def toString: String = s"TokenDef($name)"

}


case class SimpleTokenDef(name: String)(val pattern: String)(implicit val tokenizer: Tokenizer)
  extends TokenDef {

  def couldMatch(partial:String) = {
    //println(s"Simple token matching $pattern to partial $partial")
    if (partial.length > pattern.length) false
    else pattern.substring(0, partial.length) == partial
  }

  def matches(partial: String) = partial == pattern

}

case class RegexTokenDef(name : String)(val pattern: String)(implicit val tokenizer: Tokenizer)
  extends TokenDef {

  val compiled = Pattern.compile(pattern)

  def couldMatch(partial:String): Boolean = {

    val matcher = compiled.matcher(partial)

    matcher.matches() || matcher.hitEnd()

  }

  def matches(partial:String): Boolean = {

    val matcher = compiled.matcher(partial)

    matcher.matches()

  }

}