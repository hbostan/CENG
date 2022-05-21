#ifndef __DNSManager_h__
#define __DNSManager_h__


// YOU ARE EXPECTED TO IMPLEMENT THE CLASS BELOW AS WELL AS
// ANY OTHER NECESSARY CLASS (SUCH AS THE HASH TABLE CLASS) AND 
// AUXILIARY STRUCTURES THAT YOU NEED.

#include "HashTable.h"


class DNSManager
{
    public:
        // DO NOT MODIFY ANYTHING IN THE PUBLIC PART. WHEN TESTING YOUR
        // CODES, WE WILL OVERWRITE THE PUBLIC PART WITH WHAT IS GIVEN
        // IN THIS FILE. ANY MODIFICATIONS THAT YOU MAKE WILL BE LOST.

        DNSManager();

        void registerDNS(const std::string& dnsIP);
        
        void deleteDNS(const std::string& dnsIP);

        void registerURL(const std::string& url,
                         const std::string& ip,
                         const std::vector<std::string>& dnsChain);

        void deleteURL(const std::string& url);

        std::string access(const std::string& url,
                           int& accessCount,
                           int& hopCount);

        void mergeDNS(const std::string& dnsIP1,
						const std::string& dnsIP2);
					

    private:
		HashTable<HashTable<std::string>* >* dnsTable;
		string masterDNS;
        // YOU MAY FREELY MODIFY THE PRIVATE PART OF THIS CLASS
};

DNSManager::DNSManager()
{
	dnsTable= new HashTable<HashTable<std::string>* >;
}

void DNSManager::registerDNS(const std::string& dnsIP)
{
	if(dnsTable->getElementCount()==0) masterDNS=dnsIP;
	dnsTable->addKey(dnsIP, new HashTable<std::string>);
}
        
void DNSManager::deleteDNS(const std::string& dnsIP)
{
	dnsTable->deleteKey(dnsIP);
}

void DNSManager::registerURL(const std::string& url, const std::string& ip, const std::vector<std::string>& dnsChain)
{
	int vecSize=dnsChain.size();
	int i;
	for(i=0;i<vecSize-1;i++)
	{
		HashTable<string>* current=dnsTable->findKey(dnsChain[i])->value;
		current->addKey(url,dnsChain[i+1]);
	}
	HashTable<string>* current=dnsTable->findKey(dnsChain[i])->value;
	current->addKey(url,ip);
}

void DNSManager::deleteURL(const std::string& url)
{
	Node<HashTable<string>*>* currentDNS=dnsTable->findKey(masterDNS);
	Node<string>* node = currentDNS->value->findKey(url);
	currentDNS->value->deleteKey(url);
	while(currentDNS=dnsTable->findKey(node->value))
	{
		node=currentDNS->value->findKey(url);
  		currentDNS->value->deleteKey(url);
	}
	 
}
std::string DNSManager::access(const std::string& url, int& accessCount, int& hopCount)
{
	hopCount=0;
	Node<string>* node=NULL;
	Node<HashTable<string>*>* currentDNS=dnsTable->findKey(masterDNS);
	if(currentDNS && !currentDNS->isDeleted)
	{
		node= currentDNS->value->findKey(url);	
	}
	while(node)
	{
		hopCount++;
		currentDNS = dnsTable->findKey(node->value);
		if(!currentDNS && node->isDeleted==0)
		{
			accessCount=++(node->accessCount);
			return node->value;
		}
		if(currentDNS && !currentDNS->isDeleted) node=currentDNS->value->findKey(url);
		else node=NULL;
	}
	return "not found";
}

void DNSManager::mergeDNS(const std::string& dnsIP1, const std::string& dnsIP2)
{
	Node<HashTable<string>*>* currentDNS=dnsTable->findKey(dnsIP1);
	Node<HashTable<string>*>* mergeDNS=dnsTable->findKey(dnsIP2);

	int mergeTableSize= mergeDNS->value->getTableSize();
	Node<string>* mergeTable= mergeDNS->value->getTable();
	for(int i=0;i< mergeTableSize;i++)
	{
		if(mergeTable[i].isFull && !mergeTable[i].isDeleted)
		{
			currentDNS->value->addKey(mergeTable[i].key,mergeTable[i].value);
		}
	}

	deleteDNS(dnsIP2);
}






#endif // __DNSManager_h__
