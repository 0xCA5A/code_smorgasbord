
#include <stdio.h>


int hello(int value)
{
	printf("[%s] got value %d", __func__, value);

}


int main(void)
{
	int i;
	int k;

	printf("[%s] hello\n", __func__);

	return 0;

}


