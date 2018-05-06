package VerificatorInterpritator;

import java_cup.runtime.Symbol;

%%

%class VerificatorLexer
%public
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
DIGIT=[0-9]
DECIMAL_INTEGER_LITERAL={DIGIT}+
INTEGER_LITERAL={DECIMAL_INTEGER_LITERAL}
STRING_LITERAL=\'([^\\\'\r\n]|{ESCAPE_SEQUENCE})*\'
ESCAPE_SEQUENCE=\\[^\r\n]
%state YYINITIAL

%%
/*лексика*/

<YYINITIAL> {
    "(" { return symbol(sym.LPAREN); }
    ")" { return symbol(sym.RPAREN); }
    "<" { return symbol(sym.LCHEVRON); }
    ">" { return symbol(sym.RCHEVRON); }
    "::=" { return symbol(sym.VAREQUAL); }
    "==" { return symbol(sym.EQUAL); }
    ";" { return symbol(sym.SEMICOLON); }
    "*" {return symbol(sym.MANY);}
    "+" {return symbol(sym.ATLEAST);}
    "|" {return symbol(sym.OR);}

    {VARIABLE_TYPE}"."{FIRST_NAME_CHAR}{NAME_CHAR}*      { return symbol(sym.VARIABLE, createToken(yytext())); }
    {VARIABLE_TYPE}"."{NAME_CHAR}+            { return symbol(sym.METAVARIABLE, createToken(yytext())); }
    {FIRST_NAME_CHAR}{NAME_CHAR}*       { return symbol(sym.NAME, createToken(yytext())); }

    {INTEGER_LITERAL}      { return symbol(sym.INTEGER_LITERAL, createToken(yytext())); }
    {STRING_LITERAL}       { return symbol(sym.QUOTEDSTRING, createToken(yytext())); }

    ({CRLF}|{WHITE_SPACE})+ {}


    .                       { yybegin(YYINITIAL); }
}