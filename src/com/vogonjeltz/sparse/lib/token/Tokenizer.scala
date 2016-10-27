package com.vogonjeltz.sparse.lib.token

import com.vogonjeltz.sparse.lib.{ParsingLogger, SParseUtils}
import com.vogonjeltz.sparse.lib.exception.TokenizerException

import scala.collection.mutable.ListBuffer

/**
  * A class that represents a grammar of tokens.
  * @param Log - The logger object through which all output will be processed
  */
abstract class Tokenizer (val Log: ParsingLogger = new ParsingLogger()){

  //Used for creating token definitions
  implicit protected val tokenizer: Tokenizer = this

  /**
    * If true the tokenizer will parse newline characters as separate tokens
    * This requires there to be a SimpleTokenDef with the pattern "\n"
    */
  val PARSE_NEWLINE_AS_TOKEN = false

  implicit class TokenName (n: String) {
    def ~ (p: String) = SimpleTokenDef(n)(p)

    def $ (p: String) = RegexTokenDef(n)(p)
  }

  private var _tokenDefs: List[TokenDef] = List()

  /**
    * Add a tokenDefinition to the internal list
    * @param tokenDef - The tokenDef to add
    */
  def addTokenDef(tokenDef: TokenDef) =
    _tokenDefs = tokenDef :: _tokenDefs

  /**
    * Returns the internal list of TokenDefs in order that they were added (reversed)
    * Assumed
    * @return
    */
  def tokenDefs:List[TokenDef] = _tokenDefs.reverse

  def tokenize(source: String): List[Token] ={

    val tokens = ListBuffer[Token]()

    var workingTokenSource = ""


    var lineCount = 1

    //TODO: Check that this ugly hack works
    //TODO: Remove this awful hack
    var workingSource = source + " "

    var workingTokenDefs = tokenDefs

    def consume (): Unit ={
      workingSource = workingSource.substring(1)
    }

    def reset (): Unit ={
      workingTokenSource = ""
      workingTokenDefs = tokenDefs
    }

    def addToken(tokenDef: TokenDef, source: String, line: Int): Unit = {
      tokens.append(tokenDef.fromText(source, line))
      reset()
    }

    //TODO: Work out how to add newlines as a token
    //TODO: Figure out how to not include the "\n" at the end of an InlineComment token
    while (workingSource.length > 0) {

      val c = workingSource.head

      if(c == '\n') {
        lineCount += 1
      }

      if (c.isWhitespace && workingTokenSource == "" && (if (PARSE_NEWLINE_AS_TOKEN) c !='\n' else true)) {
        Log.n("Consumed whitespace with empty token")
        consume()
      }
      else {

        val proposedTokenSource = workingTokenSource + c

        val proposedTokenDefs = workingTokenDefs.filter(
          _ couldMatch proposedTokenSource
        )
        Log.n(proposedTokenDefs.toString())

        if (proposedTokenDefs.isEmpty || workingSource.length < 2){
          Log.n("Empty PTD")
          //Look back for token
          Log.n(workingTokenDefs.toString())
          Log.n(workingTokenSource)
          if (workingTokenDefs.length == 1 && workingTokenDefs.head.matches(workingTokenSource)) {
            addToken(workingTokenDefs.head, workingTokenSource, lineCount)
          } else {
            val fullMatches = workingTokenDefs.filter( _ matches workingTokenSource)

            if (fullMatches.isEmpty) {
              throw new TokenizerException(s"No tokens matched string ${SParseUtils.sanitiseString(workingSource)} and ${SParseUtils.sanitiseString(c.toString)} would have emptied list of tokendefs near line $lineCount")
            } else if (fullMatches.length == 1) {
              addToken(fullMatches.head, workingTokenSource, lineCount)
            } else {
              //TODO: Allow this method to be configured
              val simpleTokenFullMatces = fullMatches.filter(_ match {
                case _: SimpleTokenDef => true
                case _ => false
              })
              if (simpleTokenFullMatces.length == 1) {
                addToken(simpleTokenFullMatces.head, workingTokenSource, lineCount)
              } else {
                throw new TokenizerException(s"Ambiguous number of tokens matched ${SParseUtils.sanitiseString(workingSource)} and ${SParseUtils.sanitiseString(c.toString)} would have emptied list of tokendefs near line $lineCount")
              }
            }
          }
        } else{
          workingTokenDefs = proposedTokenDefs
          workingTokenSource = proposedTokenSource
          consume()
        }
        Log.n(tokens.toList.toString())
      }

    }

    tokens.toList

  }

}




