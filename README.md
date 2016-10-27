# SParse
### (Pronounced sparse, as in thinly dispersed or scattered)
A simple parsing library for Scala, SParse is designed to parse custom domain specific and general languages. So far
the library only supports tokenizers but I intend to add parsers and AST transformations.

##Tokenizers
Tokenizers take in source text and transform it into a list of Tokens, splitting it up into a form that is easier
to parse. In SParse a tokenizer is defined by its TokenDefs, defined in the class body. To create one extend the
base class Tokenizer.

SParse uses a custom DSL for defining tokens. This is done by creating members of your tokenizer as vals (the should
never be reassigned). For example `val Text_Token = "Text_Token" ~ "TEXT"`. The TokenDefs are constructed in the
form `val <name> = "<name>" <type> <pattern>`. The two types are `SimpleTokenDef` which simply matches a string
exactly and `RegexTokenDef` which matches a regular expression. SimpleTokenDefs are defined with the `~` operator
RegexTokenDefs with `$`.

The name in the string is useful for debugging by providing a readable name for the user. It is not used in the 
tokenizing or parsing.

```scala
class MyTokenizer extends Tokenizer {

    //Defines a token that will match only "literal text"
    val token1 = "TOKEN_1" ~ "literal text"
        
    //Defines a token that will match any series of upper or lower case letters
    val token2 = "TOKEN_2" $ "[a-zA-Z]+"

}

object TestApp extends App {

    //Returns List(Token(Token_1 -> `literal text` | 1))
    (new MyTokenizer()).tokenize("literal text")
    
    //Returns List(Token(Token_2 -> `Hello` | 1) Token(Token_2 -> `world` | 1))
    (new MyTokenizer()).tokenize("Hello world")

}
```

The class Tokenizer takes a ParsingLogger as an argument. This allows you to control the amount of output that the
Tokenizer has. This can be created with `new ParsingLogger(int)` where the int is the level of output to be printed.
0 will put nothing onto STDOUT and 4 will output everything.

Your tokenizer can `override` the boolean property `PARSE_NEWLINE_AS_TOKEN` which when true will allow you to parse
every "\n" character as a separate token. This requires a SimpleTokenDef with the pattern "\n"

If the tokenizer finds an ambiguous case where there is one SimpleTokenDef and one or more RegexTokenDef left in 
consideration then it will prefer the SimpleTokenDef.