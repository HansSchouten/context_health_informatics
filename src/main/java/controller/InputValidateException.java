package controller;

import controller.MainApp.NotificationStyle;

/**
 * An exception for when the input is not valid.
 */
public class InputValidateException extends Exception {

    /**
     * This variable stores the message of the exception.
     */
    private String message;

    /**
     * This variable stores the style of the notifications.
     */
    private NotificationStyle style;

    /**
     * Constructor.
     * @param m The message to be shown
     * @param s the type of the message
     */
    public InputValidateException(String m, NotificationStyle s) {
        this.message = m;
        this.style = s;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Returns the notificationstyle.
     * @return style the style to be shown
     */
    public NotificationStyle getNotificationsStyle() {
        return style;
    }

}
