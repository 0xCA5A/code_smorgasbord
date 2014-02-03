#pragma once

#include "UnCopyable.h"
#include <inttypes.h>

class UnixDomainSocketServer : private UnCopyable {

public:
    UnixDomainSocketServer(const char* socketFilePath);
    ~UnixDomainSocketServer();

    bool createSocket();
    bool closeSocket();
    void receiveMessage(uint32_t receiveTimeoutInUS);

private:
    const char* m_pSocketName;
    int m_serverUDSFileDescriptor;
    static const uint32_t m_receiveMessageBufferSizeInByte = 1024;
    char m_receiveMessageBuffer[m_receiveMessageBufferSizeInByte];
};