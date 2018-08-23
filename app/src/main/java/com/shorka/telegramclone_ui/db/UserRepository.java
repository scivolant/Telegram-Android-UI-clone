package com.shorka.telegramclone_ui.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.shorka.telegramclone_ui.entities.MessagePreview;

import java.util.List;


/**
 * Created by Kyrylo Avramenko on 8/1/2018.
 */
public class UserRepository {

    private static final String TAG = "UserRepository";

    private final UserDao userDao;
    private final MessageDao messageDao;
    private List<User> allUsers;
    private User currUser;
    private final AppDatabase appDB;
    private List<MessagePreview> cachedMessagePreviews;

    public UserRepository(Application application) {
        Log.d(TAG, "UserRepository: constructor");
        appDB = AppDatabase.getInstance(application, true);
        userDao = appDB.userDao();
        messageDao = appDB.messageDao();
    }

    //<editor-fold desc="User related methods">
    public LiveData<User> getCurrLiveUser() {
        return userDao.getById(1);
    }

    public LiveData<List<Message>> getAllLiveMessages(){
        return appDB.messageDao().getAll();
    }

    public LiveData<List<User>> getAllLiveUsers() {
        return userDao.getAll();
    }

    public void updateUser(User user){
        new updateUserAsyncTask(userDao).execute(user);
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }
    //TODO: optimize for search

    public User getCachedUserById(long id) {

        for (User user : allUsers) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        Log.d(TAG, "setCurrUser: ");
        this.currUser = currUser;
    }
    //</editor-fold>


    //<editor-fold desc="Messages related methods">
    public LiveData<List<Message>> getRecentMessageByChat(){
        return messageDao.getMostRecentDateAndGrouById();
    }

    public List<MessagePreview> getCachedMessagePreviews() {
        return cachedMessagePreviews;
    }

    public void setCachedMessagePreviews(List<MessagePreview> cachedMessagePreviews) {
        this.cachedMessagePreviews = cachedMessagePreviews;
    }

    public LiveData<List<Message>> getMessagesyRecipientId(long id){
        return messageDao.getByRecipientId(id);
    }

    public void insertMessage(Message message){
        new insertMessageAsyncTask(messageDao).execute(message);
    }

    //</editor-fold>

    private static class insertMessageAsyncTask extends AsyncTask<Message, Void, Void> {

        private MessageDao messageDao;

        insertMessageAsyncTask(MessageDao dao) {
            messageDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            messageDao.insert(params[0]);
            return null;
        }
    }

    private static class updateUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao dao;

        updateUserAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            dao.update(params[0]);
            return null;
        }
    }
}
