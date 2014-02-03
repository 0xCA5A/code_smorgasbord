
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

#include "UnixDomainSocketServer.h"

UnixDomainSocketServer::UnixDomainSocketServer(const char* socketFilePath)
    : m_pSocketName(socketFilePath) {}

UnixDomainSocketServer::~UnixDomainSocketServer() {
}

void UnixDomainSocketServer::openSocket() {
    struct sockaddr_un server_addr;

    // setup socket address structure
    bzero(&server_addr,sizeof(server_addr));
    server_addr.sun_family = AF_UNIX;
    strncpy(server_addr.sun_path,m_pSocketName ,sizeof(server_addr.sun_path) - 1);

    // create socket
    serverUDSFileDescriptor = socket(PF_UNIX, SOCK_DGRAM, 0);
    if (!serverUDSFileDescriptor) {
        perror("clientSocket");
        exit(-1);
    }

    // connect to server
    if (connect(serverUDSFileDescriptor,(const struct sockaddr *)&server_addr,sizeof(server_addr)) < 0) {
        perror("connect");
        exit(-1);
    }
}

void UnixDomainSocketServer::closeSocket() {
}

void UnixDomainSocketServer::receiveMessage() {
}