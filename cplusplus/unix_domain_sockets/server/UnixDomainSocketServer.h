#pragma once

#include "UnCopyable.h"


class UnixDomainSocketServer : private UnCopyable {

public:
    UnixDomainSocketServer(const char* socketFilePath);
    ~UnixDomainSocketServer();

    void openSocket();
    void closeSocket();
    void receiveMessage();

private:
    const char* m_pSocketName;
    int serverUDSFileDescriptor;
};