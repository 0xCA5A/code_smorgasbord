#include <sys/types.h>
#include <sys/socket.h>

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

#include <string>

#include "UnixDomainSocketClient.h"
#include "PrintMacros.h"


UnixDomainSocketClient::UnixDomainSocketClient(const char* socketFilePath)
    : m_pSocketName(socketFilePath) {
    PRINT_FUNCTION_GREETER_MACRO
    bzero(&m_clientAddr, sizeof(m_clientAddr));
}

UnixDomainSocketClient::~UnixDomainSocketClient() {
    PRINT_FUNCTION_GREETER_MACRO
}

void UnixDomainSocketClient::openSocket() {
    PRINT_FUNCTION_GREETER_MACRO

    // setup socket address structure
    bzero(&m_clientAddr, sizeof(m_clientAddr));
    m_clientAddr.sun_family = AF_UNIX;
    strncpy(m_clientAddr.sun_path, m_pSocketName, sizeof(m_clientAddr.sun_path) - 1);

    // create socket
    m_clientUDSFileDescriptor = socket(PF_UNIX, SOCK_DGRAM, 0);
    if (!m_clientUDSFileDescriptor) {
        perror("client socket");
        exit(-1);
    }
}


void UnixDomainSocketClient::closeSocket() {
    PRINT_FUNCTION_GREETER_MACRO

    int returnValue = close(m_clientUDSFileDescriptor);
    if (returnValue) {
        perror("socket close");
        exit(-1);
    }
}

void UnixDomainSocketClient::sendMessage() {
    PRINT_FUNCTION_GREETER_MACRO

    // prepare message
    const uint32_t nrOfPrintedCharacters = snprintf(m_sendMessageBuffer, m_sendMessageBufferSizeInByte - 1, "hello from process %d", getpid());

    std::cout << "[i] send message to socket, " << nrOfPrintedCharacters << " byte..." << std::endl;
    const ssize_t nrOfSentBytes = sendto(m_clientUDSFileDescriptor, m_sendMessageBuffer, nrOfPrintedCharacters + 1, 0, (struct sockaddr *) &m_clientAddr, sizeof(struct sockaddr_un));
    if (nrOfSentBytes < 0) {
        perror("sending datagram message");
    }
    std::cout << "[i] " << nrOfSentBytes << " byte sent to socket: " << std::string(m_sendMessageBuffer) << std::endl;
}
