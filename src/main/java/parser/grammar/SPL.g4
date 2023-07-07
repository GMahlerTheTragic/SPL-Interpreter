grammar SPL;

program : declaration* EOF;

declaration
    : varDecl 
    | statement
    ;

varDecl
    : 'var' IDENTIFIER ( '=' expression )? ';'
    ;

statement 
    : exprStmt 
    | ifStmt 
    | printStmt 
    | whileStmt 
    | block
    ;

exprStmt 
    : expression ';'
    ;

ifStmt 
    : 'if' '(' expression ')' statement ( 'else' statement )?
    ;

printStmt 
    : 'print' expression ';'
    ;

whileStmt 
    : 'while' '(' expression ')' statement
    ;

block 
    : '{' declaration* '}'
    ;

expression 
    : assignment
    ;

assignment 
    : IDENTIFIER '=' assignment 
    | logic_or
    ;

logic_or 
    : logic_and ( 'or' logic_and )*
    ;

logic_and 
    : equality ( 'and' equality )*
    ;

equality 
    : comparison ( ( '!=' | '==' ) comparison )*
    ;

comparison 
    : term(('>'|'>='|'<'|'<=')term)*
    ;

term 
    : factor(('-'|'+')factor)*
    ;

factor 
    : unary(('/'|'*')unary)*
    ;

unary 
    : ( '!' | '-' ) unary 
    | primary
    ;

primary 
    : 'true' 
    | 'false' 
    | NUMBER 
    | STRING 
    | '(' expression ')'
    | IDENTIFIER
    ;

NUMBER : [0-9]+ ('.' [0-9]+)?;

STRING : '"' (EscapeSequence | ~["\\])* '"';
EscapeSequence : '\\' [btnfr"'\\];
IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]*;
WS     : [ \t\r\n]+ -> skip;
