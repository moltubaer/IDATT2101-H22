#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <stdbool.h>
#include <string.h>

#define SIZE 10000000

int arr[SIZE];
int singleCollisions;
int doubleCollisions;
struct DataItem {
        int data;
        int key;
};
int mod;
int hashTable[13500000];
int test = 1;

int htSize = sizeof(hashTable) / sizeof(hashTable[0]);

bool isprime(int p){
        int k;
        for(k=2; k <= p / 2; ++k){
                if(p%k == 0)
                       return false;
        }
        return true;

}

int hash1(int h1){
        return h1%(htSize);
}

int hash2(int h1){
        return (2*h1+1)%(htSize);
}

int probe(int index1, int index2,int x){
        return (index1 + index2*x)%(htSize);
}


int insert(int num){

        int h1 = hash1(num);
        if(hashTable[h1] == 0){
                hashTable[h1] = num;

                singleCollisions++;
        }
        else{
                int h2 = hash2(num);
                int n = probe(h1,h2,1);


                while(true){
                        n = (mod+(h1+h2)%htSize);

                        if(hashTable[n]==0){
                                hashTable[n]=num;
                                mod = 0;
                                break;
                        }

                        mod++;
                        doubleCollisions++;

                }
        }
}
/*
int print(){
        int p = 0;
        for(p = 0; p<SIZE;p++){
                if(hashTable[p] != 0)
                        printf
*/
int main()
{

        int i;       
        for(i=0;i<SIZE;i++){
                arr[i] = rand()%SIZE;
        //      printf("%d\n", arr[i]);

        }

        for(i=0;i<SIZE;i++){
        insert(arr[i]);
        }

/*
        insert(1);
        insert(1);
        insert(1);
        insert(2);
        insert(2);
        insert(2);
*/





        /*
        printf("Single hash: %d\n", hash1(10));
        printf("Double hash: %d\n", hash2(10));
        printf("Single + double hash: %d\n", (hash1(10)+(hash2(10))));
        printf("Is 10 prime? %d\n", isprime(10));
        printf("Is 11 prime? %d\n", isprime(11));
        if(isprime(11)==true)
                printf("true\n");

        */
        printf("size %d\n", htSize);







        printf("single: %d\n", singleCollisions);
        printf("double: %d\n", doubleCollisions);

}
