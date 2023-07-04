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

    public Message postMessage(Message message) {
        if (message.getMessage_text() != "" && message.getMessage_text().length() < 255) {
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
}
