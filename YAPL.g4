// Define grammar
grammar YAPL;

// Tokens
ID
    : [a-zA-Z_] [a-zA-Z_0-9]*
    ;
INT
    : [0-9]+
    ;
STRING
    : '"' (~["\r\n] | '""')* '"'
    ;
WS
    : [ \t\r\n] -> skip
    ;
COMMENT
    : '#' ~[\r\n]* -> skip
    ;

// Keywords
SELF
    : 'self'
    ;
TYPE
    : 'int'
    | 'char'
    | 'string'
    | 'bool'
    | 'SELF_TYPE'
    | 'void'
    ;
CLASS
    : 'class';
IF
    : 'if';
NEW
    : 'new';
ISVOID
    : 'isvoid';
LET
    : 'let';
WHILE
    : 'while';
LOOP
    : 'loop'
    ;
POOL
    : 'pool'
    ;
ELSE
    : 'else'
    ;
FI
    : 'fi'
    ;
THEN
    : 'then'
    ;
INHERITS
    : 'inherits'
    ;
NOT
    : 'not'
    ;
TRUE
    : 'true'
    ;
FALSE
    : 'false'
    ;
ASSIGN
    : '<-'
    ;


// Productions
program
    : (class)+
    ;
class
    : CLASS TYPE (INHERITS TYPE)? '{' (feature ';')* '}'
    ;
feature
    : ID '(' (formal (',' formal)*)? ')' ':' TYPE '{' (expr)* '}'
    | ID ':' TYPE (ASSIGN expr)?
    ;
formal
    : ID ':' TYPE
    ;
expr
    : ID ASSIGN expr
    | expr ('@' TYPE)? '.' ID '(' (expr (',' expr)*)? ')' // expr[@TYPE].ID([expr[, expr]*])
    | ID '(' ( expr (',' expr)* )? ')' // ID([expr, [,expr]*])
    | IF expr THEN expr ELSE expr FI
    | WHILE expr LOOP expr POOL
    | '{' (expr ';')+ '}'
    | LET ID ':' TYPE (ASSIGN expr)? (',' ID ':' TYPE (ASSIGN expr)? )* IN expr
    | NEW TYPE
    | ISVOID expr
    | expr '+' expr
    | expr '-' expr
    | expr '*' expr
    | expr '/' expr
    | '~' expr
    | expr '<' expr
    | expr '<=' expr
    | expr '=' expr
    | NOT expr
    | '(' expr ')'
    | ID
    | INT
    | STRING
    | TRUE
    | FALSE
    ;



//OPAR : '(';
//CPAR : ')';


