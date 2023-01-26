import com.sun.xml.internal.fastinfoset.util.StringArray;

//Simple memory class
public class Memory {
    //setting memory to 2048 and exp to 4096
    public Object[][] memoryArray = new Object[2048][2];

    public Memory(){
        for (int i=0;i<2048;i++){
            memoryArray[i][0]  =i;
            memoryArray[i][1] = "0000,0000,0000,0000";

        }
    }

    @Override
    public String toString(){
        String output = new String("");
        for(int a = 0; a < this.memoryArray.length;a++){
            output = output + memoryArray[a][0]+":" + memoryArray[a][1] + "\n";
        }
        return output;

    }
    //show used memory as a string output,
    /*
    public StringArray showUsedMemory(){
        StringArray output = new StringArray();
        for (int i = 0 , j = 0; i < this.memoryArray.length; i++) {
            if (this.memoryArray[i][1] != "0000,0000,0000,0000") {
                output.add(this.memoryArray[i][1]);
                j++;
            }
            if(output.equals(" ")) {
                output = "Nothing in memory";
            }

        }
        return output;
    }
    */

    //for testing
    public static void main(String[] args){

        System.out.println(new Memory().toString());
        //System.out.println(new Memory().showUsedMemory());
    }
    public void putMem(int i ,String value){
        memoryArray[i][1]=value;
    }
}
