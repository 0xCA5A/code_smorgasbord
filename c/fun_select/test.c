#include <stdint.h>
#include <string.h>

#include "defines.h"
#include "main.h"

void run_some_char_test(void)
{
	trigger_call('a');
	trigger_call('B');
	trigger_call('0');
	trigger_call('/');
}

void run_full_char_test(void)
{
	for (uint32_t i = 0; i < LUT_SIZE; i++) {
		const uint8_t value = (uint8_t) i;
		trigger_call(value);
	}
}

void run_ascii_str_test(const char *str)
{
	char test_string[] =
	    "hello world! i am a test sting with several letters.";

	for (uint32_t i = 0; i < strlen(test_string); i++) {
		trigger_call(test_string[i]);
	}

}
