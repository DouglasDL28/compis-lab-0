grammar YAPL;


// Productions
program
    : (classDef)+ EOF
    ;

classDef
    : CLASS class_id=TYPE (INHERITS parent=TYPE)? '{' ((funcDef | varDef) ';')* '}' ';'
    ;

//feature
//    : (ID '(' (formal (',' formal)*)? ')' ':' TYPE '{' (expr)* '}')                   # funcDef
//    | (ID ':' TYPE (ASSIGN expr)?)                                                    # attrDef
//    ;

funcDef
    : (ID '(' (formal (',' formal)*)? ')' ':' (TYPE | VOID) '{' (expr)* '}')
    ;

varDef
    : ID ':' TYPE (ASSIGN expr)?
    ;

formal
    : ID ':' TYPE
    ;

expr
    :  expr ('@' TYPE)? '.' ID '(' (expr (',' expr)*)? ')'                              # funcCall
    | ID '(' ( expr (',' expr)* )? ')'                                                  # declaration
    | IF expr THEN expr ELSE expr FI                                                    # ifElse
    | WHILE expr LOOP expr POOL                                                         # while
    | '{' (expr ';')+ '}'                                                               # brackets
    | LET  varDef (',' varDef)* IN expr                                                 # let
//    | LET  ID ':' TYPE (ASSIGN expr)? (',' ID ':' TYPE (ASSIGN expr)? )* IN expr        # let
    | NEW TYPE                                                                          # new
    | '~' expr                                                                          # not
    | ISVOID expr                                                                       # isvoid
    | expr1=expr op=('*'|'/') expr2=expr                                                            # MulDiv
    | expr1=expr op=('+'|'-') expr2=expr                                                            # AddSub
    | expr1=expr op=('<'|'<='|'=') expr2=expr                                                       # Comp
    | NOT expr                                                                          # not
    | ID ASSIGN expr                                                                    # assign
    | '(' expr ')'                                                                      # parens
    | ID                                                                                # id
    | INT                                                                               # integer
    | '"' (~(EOF| '"') | '\\')*  '"'                                                    # string
    | TRUE                                                                              # true
    | FALSE                                                                             # false
    | SELF                                                                              # self
//    | VOID                                                                              # void
    ;


//Fragments
fragment LOWERCASE : [a-z];
fragment UPPERCASE : [A-Z];
fragment NUM_RANGE : [0-9];
fragment C : ('C'|'c');
fragment A : ('A'|'a');
fragment L : ('L'|'l');
fragment S : ('S'|'s');
fragment E : ('E'|'e');
fragment H : ('H'|'h');
fragment F : ('F'|'f');
fragment T : ('T'|'t');
fragment Y : ('Y'|'y');
fragment I : ('I'|'i');
fragment P : ('P'|'p');
fragment O : ('O'|'o');
fragment V : ('V'|'v');
fragment N : ('N'|'n');
fragment W : ('W'|'w');
fragment D : ('D'|'d');
fragment R : ('R'|'r');
fragment U : ('U'|'u');

MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
LT: '<';
LTE: '<=';
EQ: '=';

// Keywords
SELF
    : 'self'
    ;

VOID
    : V O I D
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
    | 'SELF_TYPE'
    ;

ID
    : LOWERCASE (UPPERCASE | LOWERCASE | '_' | NUM_RANGE )*;


INT
    : NUM_RANGE+
    ;

COMMENT : (('(*' .*? '*)')) -> skip;

LINE_COMMENT : ('--' ~('\n')*) -> skip;

WS : ( '\t'| '\r' | '\n' | '\f' | ' ' ) -> skip;



//OPAR : '(';
//CPAR : ')';


