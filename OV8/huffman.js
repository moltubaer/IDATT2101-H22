fs = require('fs');

// Min heap
class Heap {

    constructor() {
        this.heapArr = [];
    }

    size() {
        return this.heapArr.length;
    }

    empty() {
        return (this.size() === 0);
    }

    push(value) {
        this.heapArr.push(value);
        this.upHeapify();
    }

    top() {
        return this.heapArr[0];
    }

    pop() {
        if (this.empty() == false) {
            let lastIndex = this.size() - 1;
            this.heapArr[0] = { ...this.heapArr[lastIndex] };
            this.heapArr.pop();
            this.downHeapify();
        }
    }

    downHeapify() {
        let curIndex = 0;
        while (curIndex < this.size()) {
            let curElement = this.heapArr[curIndex];
            let childIndex1 = (curIndex * 2) + 1;
            let childIndex2 = (curIndex * 2) + 2;
            let childElement1 = null;
            let childElement2 = null;
            let smallest = curIndex;
            if (childIndex1 < this.size() && this.heapArr[childIndex1][0] < this.heapArr[smallest][0]) {
                smallest = childIndex1;
                childElement1 = this.heapArr[childIndex1];
            }
            if (childIndex2 < this.size() && this.heapArr[childIndex2][0] < this.heapArr[smallest][0]) {
                smallest = childIndex2;
                childElement2 = this.heapArr[childIndex2];
            }
            if (smallest == childIndex1) {
                this.heapArr[childIndex1] = curElement;
                this.heapArr[curIndex] = childElement1;
                curIndex = childIndex1;
            }
            else if (smallest == childIndex2) {
                this.heapArr[childIndex2] = curElement;
                this.heapArr[curIndex] = childElement2;
                curIndex = childIndex2;
            }
            else break;
        }
    }

    
    upHeapify() {
        let curIndex = this.size() - 1;
        while (curIndex > 0) {
            let curElement = this.heapArr[curIndex];
            let parentIndex = Math.floor((curIndex - 1) / 2);
            let parentElement = this.heapArr[parentIndex];

            if (parentElement[0] < curElement[0]) {
                break;
            }

            else {
                this.heapArr[parentIndex] = curElement;
                this.heapArr[curIndex] = parentElement;
                curIndex = parentIndex;
            }
        }
    }
}

class dataObject{
    constructor(char, freq) {
        this.char = char;
        this.freq = freq;
    }
}

class Encoding {
    
    treeToString(node) {
        if (typeof (node[1]) === "string") {
            return "'" + node[1];
        }
        return '0' + this.treeToString(node[1][0]) + '1' + this.treeToString(node[1][1]);
    }


    stringToTree(treeString) {
        let node = [];
        if (treeString[this.index] === "'") {
            this.index++;
            node.push(treeString[this.index]);
            this.index++;
            return node;
        }
        this.index++;
        node.push(this.stringToTree(treeString));
        this.index++;
        node.push(this.stringToTree(treeString));
        return node;
    }



    getCodes(node, curCode) {
        if (typeof (node[1]) === "string") {
            this.codes[node[1]] = curCode;
            return;
        }

        this.getCodes(node[1][0], curCode + '0');
        this.getCodes(node[1][1], curCode + '1');
    }

    encode(data) {
        this.heap = new Heap();
        let frequenceMap = new Map();
        let freqArray = [];
        //Setting frequency of each character
        for (let i = 0; i < data.length; i++) {
            if (freqArray.some((obj) => obj.char == data[i])) {
                const index = freqArray.findIndex((dataObject) => dataObject.char==data[i])
                freqArray[index].freq++;
                let x = frequenceMap.get(data[i]);
                frequenceMap.set(data[i], x + 1);
            }
            else {
                frequenceMap.set(data[i], 1);
               let obj = new dataObject(data[i],1);
               freqArray.push(obj);  
            }
        }
        for (let dataObject of freqArray) {
            let c = dataObject.char;
            let f = parseInt(dataObject.freq);   
            this.heap.push([f, c])
        }
        // Make the actual huffman tree
        while (this.heap.size() >= 2) {
            let node1 = this.heap.top();
            this.heap.pop();
            let node2 = this.heap.top();
            this.heap.pop();
            let newNode = [(node1[0] + node2[0]), [node1, node2]];
            this.heap.push(newNode);
        }
        let huffmanTree = this.heap.top();
        this.heap.pop();
        this.codes = {};
        this.getCodes(huffmanTree, "");

        // Binary string
        let binaryString = "";
        for (let i = 0; i < data.length; i++) {
            binaryString += this.codes[data[i]];
           
        }

        // Has to come in chunks of 8
        let padding = (8 - (binaryString.length % 8)) % 8;
        for (let i = 0; i < padding; i++) {
            binaryString += '0';
        }

        // Converting each 8 bits to corresponding character
        let encodedData = "";
        for (let i = 0; i < binaryString.length;) {
            let curNum = 0;
            for (let j = 0; j < 8; j++, i++) {
                curNum *= 2;
                curNum += (binaryString[i] - '0');
            }
            encodedData += String.fromCharCode(curNum);
        }

        let treeString = this.treeToString(huffmanTree);

        let finalString = (treeString.length).toString() + '#' + padding.toString() + '#' + treeString + encodedData;
        return (finalString);
    }


    decode(data) {

        // Get treeStringLength (the numbers before first #)
        let counter = 0;
        let treeStringLength = "";
        while (counter < data.length && data[counter] != '#') {
            treeStringLength += data[counter];
            counter++;
        }
        treeStringLength = Number(treeStringLength);

        // Remove treeStringLength from file
        data = data.slice(counter + 1);

        // Get padding the same way
        counter = 0;
        let padding = "";
        while (data[counter] != '#') {
            padding += data[counter];
            counter++;
        }
        padding = Number(padding);

        // Remove padding from file
        data = data.slice(counter + 1);

        // Get tree string
        let temp = "";
        counter = 0;
        for (let i = 0; i < treeStringLength; i++) {
            temp += data[i];
            counter++;
        }
        let treeString = temp;

        // Get encoded data
        data = data.slice(counter);
        temp = "";
        for (let i = 0; i < data.length; i++) {
            temp += data[i];
        }
        let encodedData = temp;


        this.index = 0;
        let huffmanTree = this.stringToTree(treeString);


        // Get binaryString from encodedData
        let binaryStr = "";
        for (let i = 0; i < encodedData.length; i++) {
            let curNum = encodedData.charCodeAt(i);
            let curBinary = "";
            for (let j = 7; j >= 0; j--) {
                let x = curNum >> j;
                curBinary = curBinary + (x & 1);
            }
            binaryStr += curBinary;
        }

        // Remove Padding
        binaryStr = binaryStr.slice(0, binaryStr.length - padding);

        // Decode binaryString using huffmanTree
        let decodedData = "";
        let node = huffmanTree;
        for (let i = 0; i < binaryStr.length; i++) {
            if (binaryStr[i] === '0') {
                node = node[0];
            }
            else {
                node = node[1];
            }
            if (typeof (node[0]) === "string") {
                decodedData += node[0];
                node = huffmanTree;
            }
        }
        return decodedData;
    }


    compress(file, path){
        let input = this;
        fs.readFile(file, "utf8", function (error, data) {
            if (error) {
                console.error(error)
            }
            const encodedInput = input.encode(data)
            fs.writeFile(path, encodedInput, err => {
                if (err) {
                    console.error(err)
                    return
                }
            })
        });
    }

    decompress(file, path){
        let input = this;
        fs.readFile(file, "utf8", function (error, data) {
            if (error) {
                console.error(error)
            }
            const decodedInput = input.decode(data)
            fs.writeFile(path, decodedInput, err => {
                if (err) {
                    console.error(err)
                    return
                }
            })
        });
    }
}

coder = new Encoding();

// Type path of file you want to compress/decompress
// Works with both .txt and .lyx files

const args = process.argv.slice(2);
const file = args.toString();
//let shortName = file.slice(0, 3);
const filetype = file.split('.')[file.split('.').length - 1 ];
const comp_path = "./compressed."+filetype;
const decomp_path = "./decompressed."+filetype;


// The '/' might be have to be changed to '\\' if using a full path in Windows 
if(file.includes("compressed")){
    coder.decompress(file,decomp_path);
}
else{
    coder.compress(file,comp_path);
}