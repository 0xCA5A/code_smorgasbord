#include <stdio.h>
#include <stdint.h>

#include "print.h"
#include "test.h"
#include "defines.h"

static int32_t(*handler_lut[LUT_SIZE]) (uint8_t c);

static int32_t upper_alpha_ascii_char_handler(uint8_t c)
{
	p_info("handling upper case alphanumeric character %c", c);
}

static int32_t lower_alpha_ascii_char_handler(uint8_t c)
{
	p_info("handling lower case alphanumeric character %c", c);
}

static int32_t num_ascii_char_handler(uint8_t c)
{
	p_info("handling numerical character %c", c);
}

static int32_t default_handler(uint8_t c)
{
	p_info("default handler called for value 0x%X", c);
}

int32_t trigger_call(uint8_t c)
{
	return (*handler_lut[c]) (c);
}

void init_handler_lut()
{
	p_debug("sizeof handler_lut: %d", LUT_SIZE);
	for (uint32_t i = 0; i < LUT_SIZE; i++) {

		/* dec 48 to 57, ascii nummers */
		if (i >= 48 && i <= 57) {
			p_debug("installing nummerical char handler for 0x%x",
				i);
			handler_lut[i] = &num_ascii_char_handler;
			continue;
		}
		/* dec 65 to 90, ascii upper cases */
		if (i >= 65 && i <= 90) {
			p_debug
			    ("installing upper alpha ascii char handler for 0x%x",
			     i);
			handler_lut[i] = &upper_alpha_ascii_char_handler;
			continue;
		}
		/* 97 122 // ascii lower case */
		if (i >= 97 && i <= 122) {
			p_debug
			    ("installing lower alpha ascii char handler for 0x%x",
			     i);
			handler_lut[i] = &lower_alpha_ascii_char_handler;
			continue;
		}
		/* default case */
		p_debug("installing default handler for 0x%x", i);
		handler_lut[i] = &default_handler;
	}
}

int main(void)
{
	p_info("entering main");

	p_info("lut size:\t\t\t%d entries", LUT_SIZE);
	p_info("size of void*:\t\t%d byte", sizeof(void *));
	p_info("size of handler_lut:\t%d byte", sizeof(handler_lut));

	init_handler_lut();
	run_some_char_test();
	run_full_char_test();
	run_ascii_str_test("hello world!");

	p_info("exit gracefully");
	return 0;
}
