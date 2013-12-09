#ifndef LISTELEMENT_HPP
#define LISTELEMENT_HPP


// #include "MemoryFlipperAlgorithm.hpp"


template<typename T>
class ListElement
{
public:
    ListElement(T object, ListElement<T>* p_previousListElement, ListElement<T>* p_nextListElement);
    ~ListElement();
    inline ListElement<T>* getNextElement(void);
    inline ListElement<T>* getPreviousElement(void);
    inline void setNextElement(ListElement<T>* element);
    inline void setPreviousElement(ListElement<T>* element);
    void printElementInformation(void);
    T getObjectByValue(void);

private:
    ListElement(const ListElement&);
    ListElement& operator=(const ListElement&);

private:
    T m_object;
    ListElement<T>* m_p_nextElement;
    ListElement<T>* m_p_previousElement;

};


#include "ListElement.tpp"


#endif
