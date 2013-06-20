#ifndef _CRAP_H_
#define _CRAP_H_


class Crap
{
    public:
        //default constructor
        Crap(void);
        //specific constructor
        Crap(int value);
        //copy constructor
        Crap(const Crap& other);
        //default destructor
        ~Crap(void);
        //assignment operator
        Crap& operator = (const Crap& other);
        //member accessor stuff
        bool setValue(int value);
        bool getValue(int& value) const;

    private:

    private:
        int m_value;
};


#endif // file guard
