#pragma once

#include <sys/un.h>
#include <stdio.h>


class UnixDomainSocketClient {

public:
    UnixDomainSocketClient();
    ~UnixDomainSocketClient();

    void openSocket();
    void closeSocket();
    void run();

private:
    const char* m_pSocketName;
    int clientSocketFileDescriptor; 
};