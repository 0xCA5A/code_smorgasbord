#include <stdio.h>

#define ARRAY_SIZE (128 / sizeof(float))
#define NR_OF_RUNS 100000


void print_array(float array[])
{
	printf("* ARRAY @ %p:\n", array);

	int i;
	for (i = 0; i < ARRAY_SIZE; i++)
	{
		printf("index: %d, value: %f\n", i, array[i]);
	}

}

float do_work()
{
	printf("hello from %s!\n", __func__);
	printf(" * sizeof(float): %d\n", sizeof(float));
	printf(" * array size: %d\n", ARRAY_SIZE);

	float test_number_array[ARRAY_SIZE];
	float result = 0;

	int i;
	for (i = 0; i < ARRAY_SIZE; i++)
	{
		test_number_array[i] = (float) 1 / (i + 0.00001);
	}
//	print_array(test_number_array);

	for (i = 0; i < ARRAY_SIZE; i++)
	{
		test_number_array[i] = test_number_array[i] * test_number_array[(ARRAY_SIZE - 1) - i];
	}
//	print_array(test_number_array);

	for (i = 0; i < ARRAY_SIZE; i++)
	{
		test_number_array[i] = test_number_array[i] + 0.81;
	}
//	print_array(test_number_array);

	for (i = 0; i < ARRAY_SIZE; i++)
	{
		result = result + test_number_array[i];
	}
//	print_array(test_number_array);

	return result;

}


int main()
{
	float last_result;

	printf("hello from %s!\n", __func__);
	int i;
	for (i = 0; i < NR_OF_RUNS; i++)
	{
		last_result = do_work();
	}


	printf(" * last result: %f\n", last_result);
	return 0;
}
