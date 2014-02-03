#pragma once

#include "UnCopyable.h"
#include <inttypes.h>


class UnixDomainSocketClient : private UnCopyable {

public:
    UnixDomainSocketClient(const char* socketFilePath);
    ~UnixDomainSocketClient();

    void openSocket();
    void closeSocket();
    void sendMessage();

private:
    const char* m_pSocketName;
    int m_clientUDSFileDescriptor;
    static const uint32_t m_sendMessageBufferSizeInByte = 1024;
    char m_sendMessageBuffer[m_sendMessageBufferSizeInByte];
};