#ifndef INLINE_HEADER_STUFF_H
#define INLINE_HEADER_STUFF_H


inline void say_inline_header_hello(void)
{
    printf("hello from %s\n", __func__);
}


#endif
