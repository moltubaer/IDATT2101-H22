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
int hashTable[12000000]; //størrelse på hashtable kan justeres basert på hva som ønskes av lastfaktor mot performance
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

int main()
{
        int i;       	
	int range = SIZE*2;
        for(i=0;i<SIZE;i++){
                arr[i] = rand()%range;		
        }
	clock_t t;
	t = clock();
        for(i=0;i<SIZE;i++){
	        insert(arr[i]);
        }
	t = clock() - t;
	double time_taken = ((double)t)/CLOCKS_PER_SEC;
	float lastfaktor;
        lastfaktor = SIZE / (float)htSize;
        printf("Single collisions: %d\n", singleCollisions);
        printf("Double collisions: %d\n", doubleCollisions);
	printf("Total collisions: %d\n", singleCollisions+doubleCollisions);	
	printf("Lastfaktor: %f\n", lastfaktor);
	printf("Excecution time: %f seconds\n", time_taken);

	//Har snakket med helge angående kravet om sammenligning med ferdiglaget hashmap men siden dette ikke er tilgjengelig i C vurderte vi at dette kravet kunne droppes
}