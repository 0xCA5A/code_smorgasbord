
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

#include <time.h>


#include "UnixDomainSocketServer.h"
#include "PrintMacros.h"

UnixDomainSocketServer::UnixDomainSocketServer(const char* socketFilePath)
    : m_pSocketName(socketFilePath) {

    }

UnixDomainSocketServer::~UnixDomainSocketServer() {
}

bool UnixDomainSocketServer::createSocket() {
    PRINT_FUNCTION_GREETER_MACRO
    struct sockaddr_un serverAddr;

    // setup socket address structure
    bzero(&serverAddr,sizeof(serverAddr));
    serverAddr.sun_family = AF_UNIX;
    strncpy(serverAddr.sun_path, m_pSocketName, sizeof(serverAddr.sun_path) - 1);

    // create socket
    m_serverUDSFileDescriptor = socket(PF_UNIX, SOCK_DGRAM, 0);
    if (!m_serverUDSFileDescriptor) {
        perror("clientSocket");
        return false;
    }

    // bind the UNIX domain address to the created socket
    if (bind(m_serverUDSFileDescriptor, (struct sockaddr *) &serverAddr, sizeof(struct sockaddr_un))) {
        perror("binding name to datagram socket");
        return false;
    }

    return true;
}

bool UnixDomainSocketServer::closeSocket() {

    PRINT_FUNCTION_GREETER_MACRO
    if (close(m_serverUDSFileDescriptor)) {
        perror("closing datagram socket");
        return false;
    }
    if (unlink(m_pSocketName)) {
        perror("unlink datagram socket");
        return false;
    }

    return true;
}

void UnixDomainSocketServer::receiveMessage(uint32_t receiveTimeoutInUS) {
    PRINT_FUNCTION_GREETER_MACRO

    timeval timeout;
    fd_set readFileDescriptorSet;

    timeout.tv_sec = 0;
    timeout.tv_usec = receiveTimeoutInUS;

    FD_ZERO(&readFileDescriptorSet);
    FD_SET(m_serverUDSFileDescriptor, &readFileDescriptorSet);

    const int selectRetval = select(m_serverUDSFileDescriptor + 1, &readFileDescriptorSet, NULL, NULL, &timeout);

    // select, data on one of the sockets, selectRetval > 0
    if (selectRetval > 0) {
        std::cout << "[i] try to read from socket..." << std::endl;
        const uint32_t nrOfReadBytes = read(m_serverUDSFileDescriptor, m_receiveMessageBuffer, sizeof(m_receiveMessageBuffer));
        std::cout << "[i] " << nrOfReadBytes << " bytes read from socket: " << std::string(m_receiveMessageBuffer) << std::endl;
    }

    // select timeout, selectRetval == 0

    // select error, selectRetval < 0
//     if (selectRetval < 0) {
// //         std::cout << "[i] select error..." << std::endl;
//     }
}