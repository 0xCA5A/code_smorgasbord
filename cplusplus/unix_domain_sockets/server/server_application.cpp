#include "UnixDomainSocketServer.h"

int main(int argc, char **argv) {


    const char* socketFilePath = "/tmp/mySocket";
    UnixDomainSocketServer server(socketFilePath);
    server.openSocket();

    server.receiveMessage();

    server.closeSocket();

}