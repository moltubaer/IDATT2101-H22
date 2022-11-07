import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class LempelZiv {
    static byte[] buffer;
    static int position = 0;
    static byte[] bytes;
    static int endPosition = 0;

    public static void main(String [] args) {
        String inputString = args[1];
        String outputString = args[2];

        try {
            if (args[0].equals("c")) {
                File outputFile = new File(outputString);
                LempelZiv.compress(args[1], outputFile);
            } else if (args[0].equals("d")) {
                File inputFile = new File(inputString);
                LempelZiv.decompress(inputFile, outputString);
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static void compress(String file, File outputFile) throws IOException {
        DataInputStream input = new DataInputStream(new FileInputStream(file));
        DataOutputStream output = new DataOutputStream(new FileOutputStream(outputFile));
        bytes = input.readAllBytes();
        input.close();
        
        buffer = new byte[32*32*32];
        position = 0;
        endPosition = 0;
        int last = 0;

        for(int i = 0; i < bytes.length; i++){
            int posBuff = checkInBuffer(bytes[i], endPosition);
            if(posBuff == -1){
                addToBuffer(bytes[i]);
                position++;
            } else {
                int max = buildWord(posBuff, i);
                int maxIndex = posBuff;

                while(posBuff != -1) {
                    posBuff = checkInBuffer(bytes[i], posBuff-1);
                    if(posBuff == -1) break;
                    if(buildWord(posBuff, i) > max){
                        max = buildWord(posBuff, i);
                        maxIndex = posBuff;
                    }
                }

                if(max > 6) {
                    output.writeShort((short)(position-last));
                    byte[] temp = new byte[position-last];
                    int count = 0;
                    for(int j = last; j < position; j++) {
                        temp[count++] = (byte)bytes[j];
                    }
                    output.write(temp);
                    output.writeShort((short)(-(i-maxIndex)));
                    output.writeShort((short)max);
                    for(int j = 0; j<max; j++){
                        addToBuffer(bytes[i++]);
                        position++;   
                    }
                    last = i;
                    i--;
                } else {
                    addToBuffer(bytes[i]);
                    position++;
                }
            }
        }
        output.writeShort((short)(position-last));
        for(int i = last; i<position; i++) output.writeByte(bytes[i]);
        output.close();

    }
    
    public static void decompress(File path, String outputFile) throws IOException {
        DataOutputStream output = new DataOutputStream(new FileOutputStream(outputFile));
        DataInputStream input = new DataInputStream(new FileInputStream(path));
        position = 0;
        endPosition = 0;
        buffer = new byte[1024*32];
        short start = input.readShort();
        bytes = new byte[start];
        input.readFully(bytes);
        output.write(bytes);
        for(int j = 0; j<start; j++){
            addToBuffer(bytes[j]);
            position++;   
        }
        
        while(input.available() > 0){
            short back = input.readShort();
            short copyAmount = input.readShort();
            int end = endPosition;
            bytes = new byte[copyAmount];
            int i = 0;
            for(int tempIndex = end+back; tempIndex<(end+back)+copyAmount; tempIndex++){
                byte index = get(tempIndex);
                bytes[i++] = index;
                addToBuffer(index);
                position++;
            }
            output.write(bytes);
            start = input.readShort();
            bytes = new byte[start];
            input.readFully(bytes);
            for(int j = 0; j<start; j++){
                addToBuffer(bytes[j]);
                position++;
            }
            output.write(bytes);
        }
        
        input.close();
        output.close();
    }

    public static void addToBuffer(byte b) {
        if(position >= buffer.length){
            endPosition = 0;
        }
        buffer[endPosition] = b;
        endPosition = (position+1)%buffer.length;
    }

    public static byte get(int index) {
        if(index >= buffer.length){
            int i = index%buffer.length;
            return buffer[i];
        } else if (index < 0){
            int i = buffer.length+(index%buffer.length);
            if(i == buffer.length) i = 0;
            return buffer[i];
        } else {
            return buffer[index];
        }
    }

    private static int checkInBuffer(byte b, int pos) {
        for(int i = pos; i>=0; i--){
            if(buffer[i] == b) return i;
        }
        return -1;
    }

    private static int buildWord(int posBuff, int i) {
        byte byte1 = bytes[i];
        byte buff1 = get(posBuff);
        int count = 0;

        while(buff1 == byte1 && i != bytes.length-1){
            count++;
            byte1 = bytes[++i];
            buff1 = get(++posBuff);
        }
        return count;
    }
}