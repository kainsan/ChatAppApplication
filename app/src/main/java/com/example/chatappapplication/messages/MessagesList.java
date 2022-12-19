package com.example.chatappapplication.messages;

public class MessagesList {

    private String name, mobile, lastMessage, profilePic;

    private  int unseenMessages;



    public MessagesList(String name, String mobile, String lastMessage, String profilePic, int unseenMessages) {
        this.name = name;
        this.mobile = mobile;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public void setUnseenMessages(int unseenMessages) {
        this.unseenMessages = unseenMessages;
    }

    public void add(MessagesList messagesList) {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
