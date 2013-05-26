#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <string.h>


#define NR_OF_VALUES   64

pthread_cond_t g_is_zero;
pthread_mutex_t g_mutex;
unsigned long long g_shared_data = 2312331;


void* thread_function(void* argument)
{
    printf("[%s] before decrement loop\n", __func__);
    while (g_shared_data > 0)
    {
        pthread_mutex_lock(&g_mutex);
//         printf("[%s] decrement g_shared_data...\n", __func__);
        g_shared_data--;
        pthread_mutex_unlock(&g_mutex);
    }

    //signal the condition
    printf("[%s] before decrement loop\n", __func__);
    pthread_cond_signal(&g_is_zero);

    return NULL;
}


int main(void)
{
    pthread_t thread_handler;
    void* exit_status;

    printf("[%s] hello world!\n", __func__);

    //init the data area with 0
//     memset(g_shared_data, 0, NR_OF_VALUES);

    //init the mutex
    pthread_mutex_init(&g_mutex, NULL);
    pthread_cond_init(&g_is_zero, NULL);

    pthread_create(&thread_handler, NULL, thread_function, NULL);

    //wait for the shared data to reach zero
    //while loop prevents the unwanted exit from pthread_cond_wait function
    //the surrounding pthread_mutex_lock / pthread_mutex_unlock is to prevent from race conditions
    //the pthread_cond_wait function unlocks g_mutex while waiting for g_is_zero
    printf("[%s] before pthread_mutex_lock...\n", __func__);
    pthread_mutex_lock(&g_mutex);
    while (g_shared_data != 0)
    {
        printf("[%s] before pthread_cond_wait...\n", __func__);
        pthread_cond_wait(&g_is_zero, &g_mutex);
    }
    printf("[%s] before pthread_mutex_unlock...\n", __func__);
    pthread_mutex_unlock(&g_mutex);

    printf("[%s] before pthread_join...\n", __func__);
    pthread_join(thread_handler, &exit_status);

    pthread_mutex_destroy(&g_mutex);
    pthread_cond_destroy(&g_is_zero);

    return 0;
}