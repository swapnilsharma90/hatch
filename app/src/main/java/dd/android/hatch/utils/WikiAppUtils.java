package dd.android.hatch.utils;

import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dd.android.hatch.entities.model.Page;
import rx.Subscription;
import rx.internal.util.SynchronizedSubscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by swapsharma on 4/7/16.
 */
public final class WikiAppUtils {

    public WikiAppUtils() {
    }
    /*****************************************/
    /* text util       */

    /*****************************************/
    // set some logic here if required
    public static boolean isValidInput(String userInput) {
        return false;
    }
    /*****************************************/
    /* RX java util       */

    /*****************************************/
    public static void unSubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnSubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }

    public static Subscription getNewSubIfUnsubscribed(Subscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new SynchronizedSubscription(subscription);
        }

        return subscription;
    }

    /*****************************************/
    /* collection util       */

    /*****************************************/

    public static List<Page> getObjectsWithImagesAsList(Map<String, Page> pageMap) {
        List<Page> pageList = new ArrayList<>();

        for (Page page : getPagesAsList(pageMap)) {
            if (page != null && page.thumbnail != null) {
                String source = page.thumbnail.source;
                if (!TextUtils.isEmpty(source)) {
                    pageList.add(page);
                }
            }
        }
        return pageList;
    }

    private static List<Page> getPagesAsList(Map<String, Page> pageMap) {
        if (pageMap != null && pageMap.size() > 0) {
            return new ArrayList<>(pageMap.values());
        }
        return null;
    }
    /*****************************************/
    /* animations       */

    /*****************************************/

    public static ScaleAnimation getScaleAnimation(long duration) {
        final ScaleAnimation scaleAnim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(duration);
        return scaleAnim;
    }
}
