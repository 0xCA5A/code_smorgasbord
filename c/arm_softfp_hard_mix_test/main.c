
#include <stdio.h>
#include "crap.h"
#include "megacrap.h"

int main(void)
{
	int ret_val_int;
	float ret_val_float;

	printf("hello\n");

	ret_val_int = crap(7);
	printf("crap: %d\n", ret_val_int);

	ret_val_float = megacrap(7);
	printf("megacrap: %f\n", ret_val_float);


	return 0;
}



