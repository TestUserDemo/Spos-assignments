%{

FILE *yyin;

%}

delim [ \t\n]
ws {delim}+
letter [A-Za-z]
digit [0-9]
datatypes "int"|"float"|"double"|"String"|"void"|"long"
keywords "class"|"try"|"catch"|"interface"|"goto"|"extends"|"implements"|"throws"|"throw"|"instanceOf"|"System"|"out"|"println"
arithop \+|"-"|"/"|"*"|"%"
unaryop "++"|"--"
relop "=="|"<"|">"|"<="|">="|"!="
logop "&&"|"||"|"!"
assignmentop "="
bitop "&"|"|"|"^"|"~"|"<<"|">>"
combop "+="|"-="|"*="|"/="|"%="|"<<="|">>="|"<<="
import "import"[^\n]*";"
id {letter}({letter}|{digit}|"_")*
number {digit}+({digit}+)?(e[+\-]?digit+)?
realno {digit}*.{digit}+
variable {datatypes}{delim}{id}({assignmentop}{id}|{number})?(","{id}({assignmentop}{id}|{number}))*{semicolon}
semicolon ";"
conditional "if"|"else if"|"else"|"switch"|"case"
iterative "for"|"while"|"do"
access_specifier "public"|"private"|"protected"|"default"|""
function {datatypes}{delim}{id}"("({datatypes}{variable}({assignmentop}{number})?)*")"
string \".*\"

single_line_comment "//".*\n
multi_line_comment "/*"([^*]|\*+[^*/])*\*+"/"

%%

{ws}	{/*nothing to do*/}

{access_specifier}	{printf("\nAccess specifier==>%s",yytext);}
"."	{printf("\nDot operator==>%s",yytext);}
{single_line_comment}	{printf("\nSingle Line Comment==>%s",yytext);}
{multi_line_comment}	{printf("\nMulti Line Comment==>%s",yytext);}
{string}	{printf("\nstring==>%s",yytext);}
{keywords}	{printf("\nKeyword==>%s",yytext);}
{function}	{printf("\nFunction==>%s",yytext);}
{datatypes}	{printf("\nDatatype==>%s",yytext);}
{number}	{printf("\nNumber==>%s",yytext);}
{arithop}	{printf("\nArithmetic operator==>%s",yytext);}
{unaryop}	{printf("\nUnary operator==>%s",yytext);}
{relop}	{printf("\nRelational operator==>%s",yytext);}
{logop}	{printf("\nLogical operator==>%s",yytext);}
{assignmentop}	{printf("\nAssignment operator==>%s",yytext);}
{bitop}	{printf("\nBitwise operator==>%s",yytext);}
{combop}	{printf("\nCombinational operator==>%s",yytext);}

{import}	{printf("\nImport statement==>%s",yytext);}
{semicolon}	{printf("\nSemicolon==>%s",yytext);}
"{"	{printf("\nStart of block");}
"}"	{printf("\nEnd of block");}
"("	{printf("\nopening bracket==>%s",yytext);}
")"	{printf("\nClosing bracket==>%s",yytext);}

{id}	{printf("\nIdentifier==>%s",yytext);}

%%

int yywrap()
{
	return 1;
}
int main()
{
	yyin=fopen("Sample.java","r");
	yylex();
	printf("\n");
	return 0;
}



