#include "UnixDomainSocketClient.h"

int main(int argc, char **argv) {


    const char* socketFilePath = "/tmp/mySocket";
    UnixDomainSocketClient client(socketFilePath);
    client.openSocket();

    client.sendMessage();

    client.closeSocket();

}