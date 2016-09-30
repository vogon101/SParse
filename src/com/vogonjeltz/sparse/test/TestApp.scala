package com.vogonjeltz.sparse.test

import com.vogonjeltz.sparse.lib.token._

/**
  * Created by Freddie on 31/08/2016.
  */
object TestApp {

  def main(args: Array[String]) {
    val a = new TestTokenizer()
    println()
    val tokens = a.tokenize("(name)")
    println();println()
    println(tokens)
  }

}

class TestTokenizer extends BaseTokenizer{

  val TESTTOKEN = "TEST" ~ "name"

  println(tokenDefs)

}
