
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>


#include "UnixDomainSocketClient.h"
#include "PrintMacros.h"


UnixDomainSocketClient::UnixDomainSocketClient(const char* socketFilePath)
    : m_pSocketName(socketFilePath) {

    PRINT_FUNCTION_GREETER_MACRO
}

UnixDomainSocketClient::~UnixDomainSocketClient() {
    PRINT_FUNCTION_GREETER_MACRO
}

void UnixDomainSocketClient::openSocket() {
    PRINT_FUNCTION_GREETER_MACRO

    struct sockaddr_un clientAddr;

    // setup socket address structure
    bzero(&clientAddr,sizeof(clientAddr));
    clientAddr.sun_family = AF_UNIX;
    strncpy(clientAddr.sun_path, m_pSocketName, sizeof(clientAddr.sun_path) - 1);

    // create socket
    m_clientUDSFileDescriptor = socket(PF_UNIX, SOCK_DGRAM, 0);
    if (!m_clientUDSFileDescriptor) {
        perror("clientSocket");
        exit(-1);
    }

    // prepare message
    const uint32_t nrOfPrintedCharacters = snprintf(m_sendMessageBuffer, m_sendMessageBufferSizeInByte - 1, "hello from process %d", getpid());

    std::cout << "[i] send message to socket, " << nrOfPrintedCharacters << " byte..." << std::endl;
    const ssize_t nrOfSentBytes = sendto(m_clientUDSFileDescriptor, m_sendMessageBuffer, nrOfPrintedCharacters + 1, 0, (struct sockaddr *) &clientAddr, sizeof(struct sockaddr_un));
    if (nrOfSentBytes < 0) {
        perror("sending datagram message");
    }
    std::cout << "[i] " << nrOfSentBytes << " byte sent to socket: " << std::string(m_sendMessageBuffer) << std::endl;

}


void UnixDomainSocketClient::closeSocket() {
    PRINT_FUNCTION_GREETER_MACRO
}

void UnixDomainSocketClient::sendMessage() {
    PRINT_FUNCTION_GREETER_MACRO
}