#ifndef INLINE_STUFF_MODULE_H
#define INLINE_STUFF_MODULE_H


inline void say_inline_hello(void);
inline __attribute__((always_inline)) void say_always_inline_hello(void);


#define SAY_MACRO_HELLO(void) printf("hello from macro\n")


#endif

