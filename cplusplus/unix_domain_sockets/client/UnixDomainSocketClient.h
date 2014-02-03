#pragma once

#include <sys/un.h>
#include <inttypes.h>

#include "UnCopyable.h"


class UnixDomainSocketClient : private UnCopyable {
public:
    explicit UnixDomainSocketClient(const char* socketFilePath);
    ~UnixDomainSocketClient();

    void openSocket();
    void closeSocket();
    void sendMessage();

private:
    const char* m_pSocketName;
    struct sockaddr_un m_clientAddr;
    int m_clientUDSFileDescriptor;
    static const uint32_t m_sendMessageBufferSizeInByte = 1024;
    char m_sendMessageBuffer[m_sendMessageBufferSizeInByte];
};