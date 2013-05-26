#include <stdio.h>
#include <CUnit/Basic.h>

#include "hexutils.h"



/* The suite initialization function.
 * Opens the temporary file used by the tests.
 * Returns zero on success, non-zero otherwise.
 */
int init_suite_hexutils(void)
{
    return 0;
}


/* The suite cleanup function.
 * Closes the temporary file used by the tests.
 * Returns zero on success, non-zero otherwise.
 */
int clean_suite_hexutils(void)
{
    return 0;
}


/* Simple test of fprintf().
 * Writes test data to the temporary file and checks
 * whether the expected number of bytes were written.
 */
void test__hexchar_to_bin(void)
{
    CU_ASSERT(hexchar_to_bin("00") == 0x00);
    CU_ASSERT(hexchar_to_bin("01") == 0x01);
    CU_ASSERT(hexchar_to_bin("02") == 0x02);
    CU_ASSERT(hexchar_to_bin("03") == 0x03);
    CU_ASSERT(hexchar_to_bin("04") == 0x04);
    CU_ASSERT(hexchar_to_bin("05") == 0x05);
    CU_ASSERT(hexchar_to_bin("06") == 0x06);
    CU_ASSERT(hexchar_to_bin("07") == 0x07);
    CU_ASSERT(hexchar_to_bin("08") == 0x08);
    CU_ASSERT(hexchar_to_bin("09") == 0x09);
    CU_ASSERT(hexchar_to_bin("0A") == 0x0A);
    CU_ASSERT(hexchar_to_bin("0B") == 0x0B);
    CU_ASSERT(hexchar_to_bin("0C") == 0x0C);
    CU_ASSERT(hexchar_to_bin("0D") == 0x0D);
    CU_ASSERT(hexchar_to_bin("0E") == 0x0E);
    CU_ASSERT(hexchar_to_bin("0F") == 0x0F);

    CU_ASSERT(hexchar_to_bin("00") == 0x00);
    CU_ASSERT(hexchar_to_bin("10") == 0x10);
    CU_ASSERT(hexchar_to_bin("20") == 0x20);
    CU_ASSERT(hexchar_to_bin("30") == 0x30);
    CU_ASSERT(hexchar_to_bin("40") == 0x40);
    CU_ASSERT(hexchar_to_bin("50") == 0x50);
    CU_ASSERT(hexchar_to_bin("60") == 0x60);
    CU_ASSERT(hexchar_to_bin("70") == 0x70);
    CU_ASSERT(hexchar_to_bin("80") == 0x80);
    CU_ASSERT(hexchar_to_bin("90") == 0x90);
    CU_ASSERT(hexchar_to_bin("A0") == 0xA0);
    CU_ASSERT(hexchar_to_bin("B0") == 0xB0);
    CU_ASSERT(hexchar_to_bin("C0") == 0xC0);
    CU_ASSERT(hexchar_to_bin("D0") == 0xD0);
    CU_ASSERT(hexchar_to_bin("E0") == 0xE0);
    CU_ASSERT(hexchar_to_bin("F0") == 0xF0);

    CU_ASSERT(hexchar_to_bin("0F") == 0x0F);
    CU_ASSERT(hexchar_to_bin("1E") == 0x1E);
    CU_ASSERT(hexchar_to_bin("2D") == 0x2D);
    CU_ASSERT(hexchar_to_bin("3C") == 0x3C);
    CU_ASSERT(hexchar_to_bin("4B") == 0x4B);
    CU_ASSERT(hexchar_to_bin("5A") == 0x5A);
    CU_ASSERT(hexchar_to_bin("69") == 0x69);
    CU_ASSERT(hexchar_to_bin("78") == 0x78);
    CU_ASSERT(hexchar_to_bin("87") == 0x87);
    CU_ASSERT(hexchar_to_bin("96") == 0x96);
    CU_ASSERT(hexchar_to_bin("A5") == 0xA5);
    CU_ASSERT(hexchar_to_bin("B4") == 0xB4);
    CU_ASSERT(hexchar_to_bin("C3") == 0xC3);
    CU_ASSERT(hexchar_to_bin("D2") == 0xD2);
    CU_ASSERT(hexchar_to_bin("E1") == 0xE1);
    CU_ASSERT(hexchar_to_bin("F0") == 0xF0);
}


/* Simple test of fread().
 * Reads the data previously written by testFPRINTF()
 * and checks whether the expected characters are present.
 * Must be run after testFPRINTF().
 */
void test__convert_hex_string_to_bin(void)
{
    //int convert_hex_string_to_bin(const char *hex_string, int32_t* integer_value);

    int32_t integer_value;

    //hex identifier in sting
    if (convert_hex_string_to_bin("0x0", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000000);
    if (convert_hex_string_to_bin("0X0", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000000);

    //valid hex values...
    if (convert_hex_string_to_bin("0x0", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000000);
    if (convert_hex_string_to_bin("0x01", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000001);
    if (convert_hex_string_to_bin("0x001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000001);
    if (convert_hex_string_to_bin("0x0001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000001);
    if (convert_hex_string_to_bin("0x00001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000001);
    if (convert_hex_string_to_bin("0x000001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000001);
    if (convert_hex_string_to_bin("0x0000001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000001);
    if (convert_hex_string_to_bin("0x00000001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000001);

    if (convert_hex_string_to_bin("0x1", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x0000001);
    if (convert_hex_string_to_bin("0x11", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000011);
    if (convert_hex_string_to_bin("0x101", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00000101);
    if (convert_hex_string_to_bin("0x1001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00001001);
    if (convert_hex_string_to_bin("0x10001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00010001);
    if (convert_hex_string_to_bin("0x100001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x00100001);
    if (convert_hex_string_to_bin("0x1000001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x01000001);
    if (convert_hex_string_to_bin("0x10000001", &integer_value) == 0)
        CU_ASSERT(integer_value == 0x10000001);

    if (convert_hex_string_to_bin("0xA", &integer_value) == 0)
        CU_ASSERT(integer_value == 0xA);
    if (convert_hex_string_to_bin("0xAA", &integer_value) == 0)
        CU_ASSERT(integer_value == 0xAA);
    if (convert_hex_string_to_bin("0xAAA", &integer_value) == 0)
        CU_ASSERT(integer_value == 0xAAA);
    if (convert_hex_string_to_bin("0xAAAA", &integer_value) == 0)
        CU_ASSERT(integer_value == 0xAAAA);
    if (convert_hex_string_to_bin("0xAAAAA", &integer_value) == 0)
        CU_ASSERT(integer_value == 0xAAAAA);
    if (convert_hex_string_to_bin("0xAAAAAA", &integer_value) == 0)
        CU_ASSERT(integer_value == 0xAAAAAA);
    if (convert_hex_string_to_bin("0xAAAAAAA", &integer_value) == 0)
        CU_ASSERT(integer_value == 0xAAAAAAA);
    if (convert_hex_string_to_bin("0xAAAAAAAA", &integer_value) == 0)
        CU_ASSERT(integer_value == 0xAAAAAAAA);

    //to many chars (size != 32bit)
    if (convert_hex_string_to_bin("0xAAAAAAAAB", &integer_value) == 0)
        CU_ASSERT(integer_value < 0);

    //not enough chars (size != 32bit)
    if (convert_hex_string_to_bin("0x", &integer_value) == 0)
        CU_ASSERT(integer_value < 0);

    //missing or incorrect hex identifier in string
    if (convert_hex_string_to_bin("AAAAAAAA", &integer_value) == 0)
        CU_ASSERT(integer_value < 0);
    //missing or incorrect hex identifier in string
    if (convert_hex_string_to_bin("xAAAAAAAA", &integer_value) == 0)
        CU_ASSERT(integer_value < 0);
    //missing or incorrect hex identifier in string
    if (convert_hex_string_to_bin("0zAAAAAAAA", &integer_value) == 0)
        CU_ASSERT(integer_value < 0);
    //missing or incorrect hex identifier in string
    if (convert_hex_string_to_bin("AAAAAAAAh", &integer_value) == 0)
        CU_ASSERT(integer_value < 0);
}


/* The main() function for setting up and running the tests.
 * Returns a CUE_SUCCESS on successful running, another
 * CUnit error code on failure.
 */
int main()
{
    CU_pSuite pSuite = NULL;

    //initialize the CUnit test registry
    if (CUE_SUCCESS != CU_initialize_registry())
        return CU_get_error();

    //add a suite to the registry
    pSuite = CU_add_suite("Suite_hexutils", init_suite_hexutils, clean_suite_hexutils);
    if (NULL == pSuite) {
        CU_cleanup_registry();
        return CU_get_error();
    }

    //add the tests to the suite
    //NOTE: order is important for if test steps depend on each other
    if ((NULL == CU_add_test(pSuite, "test of hexchar_to_bin()", test__hexchar_to_bin)) ||
        (NULL == CU_add_test(pSuite, "test of convert_hex_string_to_bin()", test__convert_hex_string_to_bin)))
    {
        CU_cleanup_registry();
        return CU_get_error();
    }

    //run all tests using the CUnit Basic interface
    CU_basic_set_mode(CU_BRM_VERBOSE);
    CU_basic_run_tests();
    CU_cleanup_registry();

    return CU_get_error();
}
