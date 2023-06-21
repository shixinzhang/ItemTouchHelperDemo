package top.shixinzhang.itemtouchhelperdemo.test;

/**
 * Created by Lin Yaotian on 2018/2/8.
 */

public class ItemEntity {
    private String text;
    private boolean isChecked;
    private boolean isMoreActionShowed;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isMoreActionShowed() {
        return isMoreActionShowed;
    }

    public void setMoreActionShowed(boolean moreActionShowed) {
        isMoreActionShowed = moreActionShowed;
    }
}
