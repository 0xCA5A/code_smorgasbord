
#include <iostream>
#include <memory>


struct MyInt
{
    MyInt(int i)
        : i_(i)
    {
        std::cout << "[C] constructor " << i_ << std::endl;
    }

    ~MyInt()
    {
        std::cout << "[D] destruct " << i_ << std::endl;
    }

    int i_;

};


int main()
{

    std::unique_ptr<MyInt> uniquePtr1 {new MyInt(1998)};

    std::cout << "uniquePtr1.get(): " << uniquePtr1.get() << std::endl;

    std::unique_ptr<MyInt> uniquePtr2 {move(uniquePtr1)};
    std::cout << "uniquePtr1.get(): " << uniquePtr1.get() << std::endl;
    std::cout << "uniquePtr2.get(): " << uniquePtr2.get() << std::endl;

    {
        std::unique_ptr<MyInt> localPtr {new MyInt(2003)};
    }

    uniquePtr2.reset(new MyInt(2011));
    MyInt* myInt = uniquePtr2.release();
    delete myInt;

    std::unique_ptr<MyInt> uniquePtr3 {new MyInt(2017)};
    std::unique_ptr<MyInt> uniquePtr4 {new MyInt(2022)};


    std::cout << "uniquePtr3.get(): " << uniquePtr3.get() <<  std::endl;
    std::cout << "uniquePtr4.get(): " << uniquePtr4.get() <<  std::endl;

    swap(uniquePtr3, uniquePtr4);

    std::cout << "uniquePtr3.get(): " << uniquePtr3.get() <<  std::endl;
    std::cout << "uniquePtr4.get(): " << uniquePtr4.get() <<  std::endl;

    
}


