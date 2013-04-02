#ifndef __DEBUG_H
#define __DEBUG_H

// source: http://stackoverflow.com/questions/1941307/c-debug-print-macros
#ifdef DEBUG
    #define DEBUG_PRINT(...) fprintf( stderr, __VA_ARGS__ ); while( 0 )
#else
    #define DEBUG_PRINT(...) do{ } while ( 0 )
#endif

#endif
