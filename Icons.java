import javax.swing.ImageIcon;
import javax.swing.JButton;

class Icons extends JButton {
    ImageIcon icon1;
    ImageIcon icon2;
    private boolean hidden, noIcon;

    public Icons(ImageIcon icon1, ImageIcon icon2) {
        this.icon1 = icon1;
        this.icon2 = icon2;
        setSize(100, 100);
        setFocusable(false);
    }
    public synchronized void showIcons() {
        setIcon(icon1);
        hidden = false;
    }
    public synchronized void hideIcons() {
        setIcon(icon2);
        hidden = true;
    }
    public synchronized void setNoIcon() {
        setIcon(null);
        noIcon = true;
    }
    public ImageIcon getImage() {
        return icon1;
    }
    public synchronized boolean isNoIcon() {
        return noIcon;
    }
}