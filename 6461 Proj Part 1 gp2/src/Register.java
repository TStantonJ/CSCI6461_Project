public class Register {
    public int address;
    public int value;
    public Register(int address){
        this.address = address;
        this.value=0;
    }
    public Register(int address, int value){
        this.address = address;
        this.value=value;
    }
    public void setValue(int value){
        this.value=value;
    };
    public int getValue(){
        return value;
    };
}
