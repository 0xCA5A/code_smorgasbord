


template<typename T>
ListElement<T>::ListElement(T object, ListElement<T>* p_previousListElement, ListElement<T>* p_nextListElement)
    : m_object(object)
    , m_p_nextElement(p_nextListElement)
    , m_p_previousElement(p_previousListElement)
{
}

template<typename T>
ListElement<T>::~ListElement(void)
{
}

template<typename T>
inline ListElement<T>* ListElement<T>::getNextElement(void)
{
    return m_p_nextElement;
}

template<typename T>
inline ListElement<T>* ListElement<T>::getPreviousElement(void)
{
    return m_p_previousElement;
}

template<typename T>
inline void ListElement<T>::setNextElement(ListElement<T>* p_element)
{
    m_p_nextElement = p_element;
}

template<typename T>
inline void ListElement<T>::setPreviousElement(ListElement<T>* p_element)
{
    m_p_previousElement = p_element;
}

template<typename T>
void ListElement<T>::printElementInformation(void)
{
    std::cout << "[element address: " << this << "] object: " << m_object << ", next @ " << m_p_nextElement << ", previous @ " << m_p_previousElement << std::endl;
}

template<typename T>
T ListElement<T>::getObjectByValue(void)
{
    return m_object;
}
