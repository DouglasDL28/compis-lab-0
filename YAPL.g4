grammar YAPL;


// Productions
program
    : class_gmr+
    ;
class_gmr
    : CLASS TYPE (INHERITS TYPE)? '{' (feature ';')* '}'
    ;

type: TYPE | SELF_TYPE;

id: ID | TYPE | OBJ_TYPE;

feature
    : (id '(' (formal (',' formal)*)? ')' ':' type '{' (expr)* '}')
    | (id ':' type (ASSIGN expr)?)
    ;
formal
    : id ':' type
    ;


string: '"' (~(EOF| '"') | '\\')*  '"';

expr
    : id ASSIGN expr
    | expr ('@' type)? '.' id '(' (expr (',' expr)*)? ')' // expr[@TYPE].ID([expr[, expr]*])
    | id '(' ( expr (',' expr)* )? ')' // ID([expr, [,expr]*])
    | IF expr THEN expr ELSE expr FI
    | WHILE expr LOOP expr POOL
    | '{' (expr ';')+ '}'
    | LET id ':' type (ASSIGN expr)? (',' id ':' type (ASSIGN expr)? )* IN expr
    | NEW type
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
    | id
    | INT
    | string
    | TRUE
    | FALSE
    | SELF
    ;


//Fragments
fragment LOWERCASE  : [a-z] ;
fragment UPPERCASE  : [A-Z] ;
fragment NUM_RANGE  : [0-9] ;
fragment C          : ('C'|'c') ;
fragment A         : ('A'|'a') ;
fragment L         : ('L'|'l') ;
fragment S          : ('S'|'s') ;
fragment E          : ('E'|'e') ;
fragment H          : ('H'|'h') ;
fragment F          : ('F'|'f') ;
fragment T          : ('T'|'t') ;
fragment Y          : ('Y'|'y') ;
fragment I          : ('I'|'i') ;
fragment P          : ('P'|'p') ;
fragment O          : ('O'|'o') ;
fragment V         : ('V'|'v') ;
fragment N          : ('N'|'n') ;
fragment W          : ('W'|'w') ;
fragment D          : ('D'|'d') ;
fragment R          : ('R'|'r') ;
fragment U          : ('U'|'u') ;



// Keywords
SELF
    : 'self'
    ;
SELF_TYPE
    : 'SELF_TYPE'
    ;
IF
    : I F
    ;
NEW
    : N E W
    ;
ISVOID
    : I S V O I D
    ;
LET
    : L E T
    ;
IN
    : I N
    ;
WHILE
    : W H I L E
    ;
LOOP
    : L O O P
    ;
POOL
    : P O O L
    ;
ELSE
    : E L S E
    ;
FI
    : F I
    ;
THEN
    : T H E N
    ;
INHERITS
    : I N H E R I T S
    ;
NOT
    : N O T
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

// Lexer rules




CLASS : C L A S S ;

TYPE
    : UPPERCASE (UPPERCASE | LOWERCASE | '_' | NUM_RANGE)*
    ;
OBJ_TYPE
    : LOWERCASE (UPPERCASE | LOWERCASE | '_' | NUM_RANGE )*;

ID
: (LOWERCASE | UPPERCASE | '_')(LOWERCASE | UPPERCASE | '_' | NUM_RANGE)*
;


INT
    : NUM_RANGE+
    ;

COMMENT
    : (('(*' .*? '*)')) -> skip
    ;

LINE_COMMENT : ('--' ~('\n')*) -> skip;

WS
    : ( '\t'| '\r' | '\n' | '\f' | ' ' ) -> skip
    ;



//OPAR : '(';
//CPAR : ')';


