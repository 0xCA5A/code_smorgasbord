#include <stdlib.h>

#include <iostream>

#include "ListElement.hpp"


template<typename T>
LinkedList<T>::LinkedList(void)
    : m_rootListElement(0, &m_rootListElement, &m_rootListElement)
{
}

template<typename T>
LinkedList<T>::~LinkedList(void)
{
}

template<typename T>
void LinkedList<T>::addObjectHead(const T object)
{
    ListElement<T>* p_newListElement = new ListElement<T>(object, m_rootListElement.getPreviousElement(), &m_rootListElement);

    m_rootListElement.getPreviousElement()->setNextElement(p_newListElement);
    m_rootListElement.setPreviousElement(p_newListElement);
}

/*
template<typename T>
void LinkedList<T>::removeElement(ListElement<T> listElement)
{

}
*/

template<typename T>
void LinkedList<T>::printList(void)
{
    std::cout << "hello from the list" << std::endl;

    ListElement<T>* p_iterator = m_rootListElement.getNextElement();
    while (p_iterator != &m_rootListElement) {
        p_iterator->printElementInformation();
        p_iterator = p_iterator->getNextElement();
    }

}

template<typename T>
T LinkedList<T>::getObjectHeadCopyByValue(void)
{
    return m_rootListElement.getPreviousElement()->getObjectByValue();
}

template<typename T>
T LinkedList<T>::popObjectHead(void)
{
    ListElement<T>* p_listElementToPop = m_rootListElement.getPreviousElement();
    if (p_listElementToPop == &m_rootListElement) {
        std::cout << "[!] no element in list, nothing to pop..." << std::endl;
        return 0;
    }

    T object = p_listElementToPop->getObjectByValue();
    m_rootListElement.setPreviousElement(p_listElementToPop->getPreviousElement());
    p_listElementToPop->getPreviousElement()->setNextElement(&m_rootListElement);
    delete p_listElementToPop;

    return object;
}

template<typename T>
void LinkedList<T>::flattenAllElements(void)
{
    std::cout << "[i] flatten all list elements..." << std::endl;

    ListElement<T>* p_iterator = m_rootListElement.getNextElement();
    while (p_iterator != &m_rootListElement) {
        const ListElement<T>* p_elementToFlatten = p_iterator;
        p_iterator = p_iterator->getNextElement();
        delete p_elementToFlatten;
    }

    m_rootListElement.setNextElement(&m_rootListElement);
    m_rootListElement.setPreviousElement(&m_rootListElement);
}

template<typename T>
uint32_t LinkedList<T>::getNumberOfElements(void)
{
    uint32_t elementCounter = 0;
    ListElement<T>* p_iterator = m_rootListElement.getNextElement();
    while (p_iterator != &m_rootListElement) {
        p_iterator = p_iterator->getNextElement();
        elementCounter++;
    }
    return elementCounter;
}
