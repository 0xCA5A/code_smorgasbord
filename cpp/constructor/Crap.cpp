#include <stdio.h>
#include "Crap.h"


Crap::Crap(void)
{
    printf("\t[i] %s: default constructor\n", __func__);
}

Crap::Crap(int value)
{
    printf("\t[i] %s: constructor with int value\n", __func__);
    m_value = value;
}

Crap::~Crap(void)
{
    printf("\t[i] %s: default destructor\n", __func__);
}

Crap::Crap(const Crap& other)
{
    other.getValue(m_value);
    printf("\t[i] %s: copy constructor\n", __func__);
}

Crap& Crap::operator = (const Crap& other)
{
    printf("\t[i] %s: assignment operator\n", __func__);
    int value;
    other.getValue(value);
    this->setValue(value);
}

bool Crap::setValue(int value)
{
    printf("\t[i] %s: set value to the member\n", __func__);
    m_value = value;

    return true;
}

bool Crap::getValue(int& value) const
{
    printf("\t[i] %s: get value from the member\n", __func__);
    value = m_value;

    return true;
}
