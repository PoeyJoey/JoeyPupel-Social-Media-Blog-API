package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::postCreateAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);

        return app;
    }

    /**
     * 1
     */
    private void postCreateAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if (addedAccount != null) {
            ctx.json(addedAccount);
        } else {
            ctx.status(400);
        }
    }

    /**
     * 2
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if (loginAccount != null) {
            ctx.json(loginAccount);
        } else {
            ctx.status(401);
        }
    }

    /**
     * 3
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        if (accountService.checkAccountExists(message.getPosted_by()) != null) {
            Message addedMessage = messageService.postMessage(message);
            if (addedMessage != null) {
                ctx.json(addedMessage);
            } else {
                ctx.status(400);
            }
        } else {
            ctx.status(400);
        }
    }

    /**
     * 4
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * 5
     */
    private void getMessageByIDHandler(Context ctx) {
        Message message = messageService.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        if (message != null){
            ctx.json(message);
        }
    }

    /**
     * 6
     */
    private void deleteMessageByIDHandler(Context ctx) {
        Message deletedMessage = messageService.deleteMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        }
        
    }

    /**
     * 7
     */
    private void patchMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String newMessageText = mapper.readTree(ctx.body()).get("message_text").asText();
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(messageID, newMessageText);
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * 8
     */
    private void getAllMessagesByUserHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessagesByUser(Integer.parseInt(ctx.pathParam("account_id")));
        ctx.json(messages);
    }


}