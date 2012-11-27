
#include <stdio.h>
#include "crap.h"
#include "megacrap.h"

int main(void)
{
	int ret_val;

	printf("hello\n");

	ret_val = crap(7);
	printf("crap: %d\n", ret_val);

	ret_val = megacrap(7);
	printf("megacrap: %d\n", ret_val);


	return 0;
}



