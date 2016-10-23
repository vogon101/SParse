package com.vogonjeltz.sparse.lib

/**
  * Created by fredd on 23/10/2016.
  */
class ParsingLogger(val logLevel:Int = 1) {

  def v(string: String) = {
    if (logLevel > 3)
      println(s"[VERBOSE] [PARSING] $string")
  }

  def n(string: String) = {
    if (logLevel > 2)
    println(s"[NOTE] [PARSING]  $string")
  }

  def w(string: String) = {
    if (logLevel > 1)
      println(s"[WARN] [PARSING] $string")
  }

  def e(string: String) = {
    if (logLevel > 0)
      println(s"[ERROR] [PARSING] $string")
  }

}
