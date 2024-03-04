package dev.mark.jewelsstorebackend.messages;

import java.util.HashMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {

    private HashMap<String, String> message;
  
    public HashMap<String, String> getMessage() {
      return message;
    }
  
    public void setMessage(HashMap<String, String> message) {
      this.message = message;
    }
  
    public HashMap<String, String> createMessage(String msg) {
      HashMap<String, String> newMessage = new HashMap<String, String>();
      newMessage.put("message", msg);
      this.message = newMessage;

      return newMessage;
    }
  
  }