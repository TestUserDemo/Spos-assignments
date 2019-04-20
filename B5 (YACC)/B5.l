%{
#include "y.tab.h" //Contains Token Definiation
%}
%%
[\t ] ;	//IGNORE WHITE SPACES
a|an|the {printf("ARTICLE\t\t\t==>%s\n",yytext);return ARTICLE;}
am|is|are|have|has|can|will|shall|eat|sing|go|goes { printf("VERB\t\t\t==>%s\n",yytext);return VERB;}
very|simply|gently { printf("ADVERB\t\t\t==>%s\n",yytext);return(ADVERB); }
and|or|also|so|but|if|then {printf("CONJUNCTION\t\t==>%s\n",yytext);return (CONJUNCTION);}
fast|good|honest {printf("ADJECTIVE\t\t\t==>%s\n",yytext);return (ADJECTIVE);}
I|he|she|we|they|you|this {printf("PRONOUN\t\t\t==>%s\n",yytext);return (PRONOUN);}
in|on|to {printf("PREPOSITION\t\t\t==>%s\n",yytext);return (PREPOSITION);}
[a-zA-Z]+ {printf("NOUN\t\t\t==>%s\n",yytext);return (NOUN);}
. ; //IGNORE ANYTHING ELSE
%%
int yywrap()
{
return 1;
}
