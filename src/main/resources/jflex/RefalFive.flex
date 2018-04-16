package RefalInterpritator;

%%

%class RefalFiveLexer
%unicode
%line
%column
%cup
%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, LexerToken value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
    private LexerToken createToken(String val) {
        LexerToken tk = new LexerToken(val);
        return tk;
    }    
%}
// Макро
CRLF= \n|\r|\r\n
WHITE_SPACE=[\ \t\f]
FIRST_NAME_CHAR=[A-Z]
NAME_CHAR=[a-zA-Z_\-0-9]
VARIABLE_TYPE = "s"|"t"|"e"

MULTILINE_COMMENT=(("/*"|"/**")[^"*"]{COMMENT_TAIL})|"/*"
COMMENT_TAIL=([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?
END_OF_LINE_COMMENT="/""/"[^\r\n]*

STRING_LITERAL=\'([^\\\'\r\n]|{ESCAPE_SEQUENCE})*\'
ESCAPE_SEQUENCE=\\[^\r\n]

DIGIT=[0-9]
DECIMAL_INTEGER_LITERAL={DIGIT}+
INTEGER_LITERAL={DECIMAL_INTEGER_LITERAL}
EXTERNAL="$EXTERNAL"|"$EXTERN"|"$EXTRN"

%state YYINITIAL

%%	
/*лексика*/

<YYINITIAL> {
    {EXTERNAL}       { return symbol(sym.EXTERN); }
    "$ENTRY"        { return symbol(sym.ENTRY); }

    "," { return symbol(sym.COMMA); }
    ";" { return symbol(sym.SEMICOLON); }
    ":" { return symbol(sym.COLON); }
    "{" { return symbol(sym.LBRACE); }
    "}" { return symbol(sym.RBRACE); }
    "(" { return symbol(sym.LPAREN); }
    ")" { return symbol(sym.RPAREN); }
    "<" { return symbol(sym.LCHEVRON); }
    ">" { return symbol(sym.RCHEVRON); }
    "=" { return symbol(sym.EQUAL); }

    {VARIABLE_TYPE}"."{NAME_CHAR}+      { return symbol(sym.VARIABLE, createToken(yytext())); }
    {FIRST_NAME_CHAR}{NAME_CHAR}*       { return symbol(sym.NAME, createToken(yytext())); }

    {STRING_LITERAL}       { return symbol(sym.QUOTEDSTRING, createToken(yytext())); }
    {INTEGER_LITERAL}      { return symbol(sym.INTEGER_LITERAL, createToken(yytext())); }

    ({CRLF}|{WHITE_SPACE}|{END_OF_LINE_COMMENT}|{MULTILINE_COMMENT})+ {}


    .                       { yybegin(YYINITIAL); }
}