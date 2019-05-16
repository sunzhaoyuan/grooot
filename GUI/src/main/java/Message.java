public class Message {

    public String text;
    public String chatRoom;
    public String sender;
    public Long timeStamp;

    public Message(){

    }

    public Message(String text, String chatRoom, String sender){
        this.text = text;
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.timeStamp = System.currentTimeMillis();
    }

    @Override
    public String toString(){
        return "Text: " + this.text + "\n ChatRoom: " + this.chatRoom + "\n Sender: " + this.sender;
    }

}
