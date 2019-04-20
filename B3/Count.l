%{
	unsigned charCount=0,lineCount=0,wordCount=0;
%}

word [^ \t\n]+
eol  \n

%%
{word}	{wordCount++; charCount+=yyleng;}
{eol}	{lineCount++;}
.	{charCount++;}
%%

int main()
{
	extern FILE *yyin;
	yyin=fopen("input.txt","r");
	yylex();
	printf("%d %d %d",lineCount,wordCount,charCount);
}
int yywrap()
{
	return 1;
}
