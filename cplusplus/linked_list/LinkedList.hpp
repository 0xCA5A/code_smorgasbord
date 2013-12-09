#ifndef LINKEDLIST_HPP
#define LINKEDLIST_HPP


#include "ListElement.hpp"
// #include "MemoryFlipperAlgorithm.hpp"


template<typename T>
class LinkedList
{
public:
    LinkedList(void);
    ~LinkedList(void);
    void addObjectHead(T object);
//     void removeObject(ListElement<T> listElement);
    void printList(void);
    T getObjectHeadCopyByValue(void);
    T popObjectHead(void);
    void flattenAllElements(void);
    uint32_t getNumberOfElements(void);

private:
    LinkedList(const LinkedList&);
    LinkedList& operator=(const LinkedList&);

private:
    ListElement<T> m_rootListElement;

};


#include "LinkedList.tpp"


#endif
