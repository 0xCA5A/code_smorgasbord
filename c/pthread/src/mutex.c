#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <string.h>


#define NR_OF_VALUES   64


pthread_mutex_t g_lock;
int g_shared_data[NR_OF_VALUES];

// static inline void inline_hang_on(long value)
// {
//     for (long i = 0; i < value; i++)
//     {
//         ;;;
//     }
// }


// static inline void inline_hang_on_random()
// {
//     srand(time(NULL));
//     unsigned int random_value = rand();
// 
// //     printf("[%s] value: %d\n", __func__, random_value);
// 
//     random_value = random_value % 3200000;
//     inline_hang_on(random_value);
// }


void* thread_function(void* argument)
{
    for (int i = 0; i < 1024 * 1024 * 77; i++)
    {
        pthread_mutex_lock(&g_lock);
        for (int j = 0; j < NR_OF_VALUES; j++)
        {
            g_shared_data[j]++;
        }
        pthread_mutex_unlock(&g_lock);
    }

    return NULL;
}


int main(void)
{
    pthread_t thread_handler;
    void* exit_status;

    printf("[%s] hello world!\n", __func__);

    //init the data area with 0
    memset(g_shared_data, 0, NR_OF_VALUES);

    //init the mutex
    pthread_mutex_init(&g_lock, NULL);

    pthread_create(&thread_handler, NULL, thread_function, NULL);

    //print the values. if we dont lock the ressource, the thread will modify the data while we print
    //to be faster we can lock, copy everything to local memory, free and print the local data
    for (int i = 0; i < 10; i++)
    {
        sleep(1);
        printf("[%s] shared integers values: \n", __func__);
        pthread_mutex_lock(&g_lock);
        for (int j = 0; j < NR_OF_VALUES; j++)
        {
            printf("%d ", g_shared_data[j]);
        }
        printf("\n");
        pthread_mutex_unlock(&g_lock);
    }
    printf("\n");

    pthread_join(thread_handler, &exit_status);

    pthread_mutex_destroy(&g_lock);


    return 0;
}