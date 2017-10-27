#include <stdarg.h>
#include <stdio.h>
#include "main.h"

void p_error(const char *format, ...)
{
	va_list args;
	fprintf(stderr, "[!] ");
	va_start(args, format);
	vfprintf(stderr, format, args);
	va_end(args);
	fprintf(stderr, "\n");
}

void p_info(const char *format, ...)
{
	va_list args;
	fprintf(stdout, "[i] ");
	va_start(args, format);
	vfprintf(stdout, format, args);
	va_end(args);
	fprintf(stdout, "\n");
}

void p_debug(const char *format, ...)
{
#ifdef DEBUG
	va_list args;
	fprintf(stdout, "[debug] ");
	va_start(args, format);
	vfprintf(stdout, format, args);
	va_end(args);
	fprintf(stdout, "\n");
#endif
}
