

//source
//http://www.cplusplus.com/reference/cstring/strerror/
//http://www.cplusplus.com/reference/cstdio/perror/


#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <stdlib.h>

#include <iostream>



#define die(str, args...) do { \
        perror(str); \
        exit(EXIT_FAILURE); \
    } while(0)



int main ()
{
    FILE * pFile;
    pFile = fopen ("unexist.ent","r");
    if (pFile == NULL)
    {
        std::cout << "[strerror] error opening file unexist.ent: "  << strerror(errno) << std::endl; 
        perror("[perror] the following error occurred");

        die("[die] the following error occurred");

    }

    
    
    std::cout << " > crap after print error message..." << std::endl;

    return 0;
}