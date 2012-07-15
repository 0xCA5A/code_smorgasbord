#! /usr/bin/env python


import sys
import module_one



if __name__ == "__main__":
    print "[i] main entered..."

    my_class_obj = module_one.MyClass()
    my_class_obj.global_hello()

    my_class_obj = module_one.MyClass2000()
    my_class_obj.global_hello()
    my_class_obj.global_hello_2000()

    my_class_obj = module_one.MyClass3000()
    my_class_obj.global_hello()
    my_class_obj.global_hello_3000()

    print "[i] main done..."