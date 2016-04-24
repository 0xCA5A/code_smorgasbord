#!/usr/bin/python3

import time


def timing(func):
    def wrap(*args):
        ts_before = time.time()
        for i in range(1000):
            ret = func(i % 10)
        ts_after = time.time()
        print('function took %0.3fms (%fus)' % ((ts_after - ts_before) * 1000.0, (ts_after - ts_before)))
        return ret
    return wrap


@timing
def function_return(condition):
    if condition == 0:
        return "0"
    if condition == 1:
        return "1"
    if condition == 2:
        return "2"
    if condition == 3:
        return "3"
    if condition == 4:
        return "4"
    if condition == 5:
        return "5"
    if condition == 6:
        return "6"
    if condition == 7:
        return "7"
    if condition == 8:
        return "8"
    if condition == 9:
        return "9"
    print("condition unhandled")


@timing
def function_elif(condition):
    ret = None
    if condition == 0:
        ret = "0"
    elif condition == 1:
        ret = "1"
    elif condition == 2:
        ret = "2"
    elif condition == 3:
        ret = "3"
    elif condition == 4:
        ret = "4"
    elif condition == 5:
        ret = "5"
    elif condition == 6:
        ret = "6"
    elif condition == 7:
        ret = "7"
    elif condition == 8:
        ret = "8"
    elif condition == 9:
        ret = "9"
    else:
        print("condition unhandled")
    return ret


@timing
def function_if(condition):
    ret = None
    if condition == 0:
        ret = "0"
    if condition == 1:
        ret = "1"
    if condition == 2:
        ret = "2"
    if condition == 3:
        ret = "3"
    if condition == 4:
        ret = "4"
    if condition == 5:
        ret = "5"
    if condition == 6:
        ret = "6"
    if condition == 7:
        ret = "7"
    if condition == 8:
        ret = "8"
    if condition == 9:
        ret = "9"
    if not ret:
        print("condition unhandled")
    return ret


HASH_MAP = {0: "0",
            1: "1",
            2: "2",
            3: "3",
            4: "4",
            5: "5",
            6: "6",
            7: "7",
            8: "8",
            9: "9"}


@timing
def function_hashmap_exception(condition):
    try:
        return HASH_MAP[condition]
    except Exception:
        pass


@timing
def function_hashmap(condition):
    return HASH_MAP[condition]


if __name__ == "__main__":

    print("\nfunction_if")
    print(function_if())

    print("\nfunction_elif")
    print(function_elif())

    print("\nfunction_return")
    print(function_return())

    print("\nfunction_hashmap_exception")
    print(function_hashmap_exception())

    print("\nfunction_hashmap")
    print(function_hashmap())
