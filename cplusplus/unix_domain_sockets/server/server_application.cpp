#include "UnixDomainSocketServer.h"
#include "PrintMacros.h"

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>


bool g_applicationRunFlag;

void signalCallbackHandler(int signum) {
    g_applicationRunFlag = false;
}

int main(int argc, char **argv) {

    PRINT_FUNCTION_GREETER_MACRO

    // register signal
    signal(SIGINT, signalCallbackHandler);

    const char* socketFilePath = "/tmp/mySocket";
    UnixDomainSocketServer server(socketFilePath);
    server.createSocket();

    g_applicationRunFlag = true;
    uint32_t receiveTimeoutInUS = 1000000;

    while (g_applicationRunFlag) {
        server.receiveMessage(receiveTimeoutInUS);
    }

    server.closeSocket();

    return 0;
}