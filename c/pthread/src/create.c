#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>


#define NR_OF_THREADS   10


static inline void inline_hang_on(long value)
{
    for (long i = 0; i < value; i++)
    {
        ;;;
    }
}


static inline void inline_hang_on_random()
{
    srand(time(NULL));
    unsigned int random_value = rand();

//     printf("[%s] value: %d\n", __func__, random_value);

    random_value = random_value % 3200000;
    inline_hang_on(random_value);
}


void* thread_function(void* argument)
{
    int argument_value = *(int*)argument;

    for (int i = 0; i < 5; i++)
    {
        printf("[%s] thread id: %d (loop index: %d)\n", __func__, argument_value, i);
        *(int*)argument = *(int*)argument * argument_value;
        inline_hang_on_random();
    }

    return argument;
}


int main(void)
{
    pthread_t thread_handler[NR_OF_THREADS];
    void* exit_status[NR_OF_THREADS];
    int thread_values[NR_OF_THREADS];

    printf("[%s] hello world!\n", __func__);

    //create thread with default arguments, call thread_function with value as argument
    for (int thread_index = 0; thread_index < NR_OF_THREADS; thread_index++)
    {
        thread_values[thread_index] = thread_index;
        printf("[%s] create thread id %d\n", __func__, thread_index);
        pthread_create(&thread_handler[thread_index], NULL, thread_function, &thread_values[thread_index]);
    }

    //wait until thread returns
    for (int thread_index = 0; thread_index < NR_OF_THREADS; thread_index++)
    {
        pthread_join(thread_handler[thread_index], &exit_status[thread_index]);
        printf("[%s] thread id %d returned with exit status %d\n", __func__, thread_index, *(int*)exit_status[thread_index]);
//         free(&exit_status[thread_index]);
        fflush(NULL);
    }

    //only the main thread is running now
    printf("[%s] return\n", __func__);
    sleep(1);
    return 0;
}