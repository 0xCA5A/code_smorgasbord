#include "UnixDomainSocketClient.h"

int main(int argc, char **argv) {

    UnixDomainSocketClient client = UnixDomainSocketClient();
    client.openSocket();

    client.run();

    client.closeSocket();

}