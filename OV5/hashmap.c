#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <stdbool.h>
#include <string.h>

#define SIZE 10000000
int arr[SIZE];
//long singleCollisions;
//long doubleCollisions;
long Collisions;
struct DataItem {
        int data;
        int key;
};
int mod;
int hashTable[12000017]; //Tabellstørrelse endret til primtall 
			  
int prime = 5;
int htSize = sizeof(hashTable) / sizeof(hashTable[0]);

bool isprime(int p){
        int k;
        for(k=2; k <= p / 2; ++k){
                if(p%k == 0)
                       return false;
        }
        return true;

}

bool isEven(int e){
	if(e%2 == 0){
		return true;
		}
	else{
		return false;
	}
}

int hash1(int h1){
        return h1%(htSize);
}

int hash2(int h1){
        return (2*h1+1)%(htSize);
}
int primeHash(int p){
	return prime-(p%prime);
}
int probe(int index1, int index2,int x){
        return (index1 + index2*x)%(htSize);
}


int insert(int num){
	//Endret fra forrige innlevering
        int h1 = hash1(num);
        if(hashTable[h1] == 0){
                hashTable[h1] = num;
        }
        else{
                int h2 = primeHash(num);
                int n = probe(h1,h2,1);


                while(true){
			// (h1+h1)%htSize endret til (n+h2)%htSize så det ikke blir lineær probing men
			// at dobbel hashing fungerer korrekt 
                        n = ((n+h2)%htSize);

                        if(hashTable[n]==0){
                                hashTable[n]=num;
                                break;
                        }
  	
                        Collisions++;

                }
        }
}

int main()
{
        int i;       
        for(i=0;i<SIZE;i++){
                arr[i] = rand()%htSize;
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
     //   printf("Single collisions: %li\n", singleCollisions);
     //   printf("Double collisions: %li\n", doubleCollisions);
	printf("Total collisions: %li\n", Collisions);	
	printf("Lastfaktor: %f\n", lastfaktor);
	printf("Excecution time: %f seconds\n", time_taken);
}