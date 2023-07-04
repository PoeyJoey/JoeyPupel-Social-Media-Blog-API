package Service;

import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Boolean checkMessageText(String messageText) {
        if (messageText != "" && messageText.length() < 255) {
            return true;
        } else {
            return false;
        }
    }

    public Message postMessage(Message message) {
        if (checkMessageText(message.getMessage_text())) {
            return messageDAO.postMessage(message);
        } else {
            return null;
        }
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int messageID) {
        return messageDAO.getMessageByID(messageID);
    }

    public Message deleteMessageByID(int messageID) {
        Message returnMessage = messageDAO.getMessageByID(messageID);
        if(returnMessage != null) {
            messageDAO.deleteMessageByID(messageID);
        }
        return returnMessage;
    }

    public Message updateMessage(int messageID, String newMessageText) {
        if (checkMessageText(newMessageText)) {
            System.out.println("Message was not empty or wrong!");
            Message message = getMessageByID(messageID);
            if (message != null) {
                messageDAO.updateMessage(messageID, newMessageText);
                return getMessageByID(messageID);
            }
        }
        

        return null;
    }
}
