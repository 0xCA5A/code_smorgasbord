

//source:
//http://www.cplusplus.com/reference/cassert/assert/



//define this before assert.h include to disable the assert macro
#define NDEBUG



#include <iostream>
#include <assert.h>


using namespace std;
 


class Crap
{

	private:
		int nothing;

	public:
		Crap()
		{
			nothing = 0;
		}

		void addNumber(int* number)
		{
			cout << " > before assert" << endl;
			assert(number != NULL);
			cout << " > after assert" << endl;
			nothing = nothing + *number;
		}

		int getNothing()
		{
			return nothing;
		}	

};


int main()
{
	Crap crap;
	crap.addNumber(NULL);
	cout << "Hello World!: " << crap.getNothing() << endl;
}
