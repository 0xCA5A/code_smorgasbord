#include <stdio.h>
#include "Crap.h"


//default constructor
Crap::Crap(void)
{
    printf("\t[%s:%s:%d] default constructor (object: %p)\n", __FILE__, __func__, __LINE__, this);
}

//specific constructor
Crap::Crap(int value)
{
    printf("\t[%s:%s:%d] specific constructor with int value (object: %p)\n", __FILE__, __func__, __LINE__, this);
    this->setValue(value);
}

//destructor
Crap::~Crap(void)
{
    printf("\t[%s:%s:%d] default destructor (object: %p)\n", __FILE__, __func__, __LINE__, this);
}

//copy constructor
Crap::Crap(const Crap& other)
{
    printf("\t[%s:%s:%d] copy constructor (object: %p)\n", __FILE__, __func__, __LINE__, this);
    other.getValue(m_value);
}

//assignment operator
Crap& Crap::operator = (const Crap& other)
{
    printf("\t[%s:%s:%d] assignment operator (object: %p)\n", __FILE__, __func__, __LINE__, this);
    int value;
    other.getValue(value);
    this->setValue(value);

    return *this;
}

//member value accessor method
bool Crap::setValue(int value)
{
    printf("\t[%s:%s:%d] set value to the member (object: %p)\n", __FILE__, __func__, __LINE__, this);
    m_value = value;

    return true;
}

//member value accessor method
bool Crap::getValue(int& value) const
{
    printf("\t[%s:%s:%d] get value from the member (object: %p)\n", __FILE__, __func__, __LINE__, this);
    value = m_value;

    return true;
}
