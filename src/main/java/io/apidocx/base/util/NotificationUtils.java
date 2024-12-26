package io.apidocx.base.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import io.apidocx.config.DefaultConstants;

import java.awt.*;

/**
 * 消息通知工具类.
 */
public final class NotificationUtils {

    public static final NotificationGroup DEFAULT_GROUP;

    static {
        DEFAULT_GROUP = NotificationGroup.findRegisteredGroup(DefaultConstants.NAME);
    }

    /**
     * 提示普通消息
     */
    public static void notifyInfo(String content) {
        Notification notification = DEFAULT_GROUP.createNotification(content, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    /**
     * 提示普通消息
     */
    public static void notifyInfo(String title, String content) {
        Notification notification = DEFAULT_GROUP.createNotification(title, content, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    /**
     * 提示警告消息
     */
    public static void notifyWarning(String content) {
        Notification notification = DEFAULT_GROUP.createNotification(content, NotificationType.WARNING);
        Notifications.Bus.notify(notification);
    }

    /**
     * 提示警告消息
     */
    public static void notifyWarning(String title, String content) {
        Notification notification = DEFAULT_GROUP.createNotification(title, content, NotificationType.WARNING);
        Notifications.Bus.notify(notification);
    }


    /**
     * 提示错误消息
     */
    public static void notifyError(String content) {
        Notification notification = DEFAULT_GROUP.createNotification(content, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }

    /**
     * 提示错误消息
     */
    public static void notifyError(String title, String content) {
        Notification notification = DEFAULT_GROUP.createNotification(title, content, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }

    /**
     * 通知
     */
    public static void notify(NotificationType type, String title, String content, AnAction... actions) {
        Notification notification = DEFAULT_GROUP.createNotification(title, content, type);
        if (actions != null) {
            for (AnAction action : actions) {
                notification.addAction(action);
            }
        }
        Notifications.Bus.notify(notification);
    }

    public static void showPopupBalloon(Editor editor, final String result) {
        ApplicationManager.getApplication().invokeLater(
                () -> {
                    JBPopupFactory factory = JBPopupFactory.getInstance();
                    Color color = new Color(86, 182, 194);
                    Color darkColor = new Color(50, 53, 59);
                    factory.createHtmlTextBalloonBuilder(result, null, new JBColor(color, darkColor), null)
                            .setFadeoutTime(3000)
                            .createBalloon()
                            .show(factory.guessBestPopupLocation(editor), Balloon.Position.above);
                }
        );
    }
}
