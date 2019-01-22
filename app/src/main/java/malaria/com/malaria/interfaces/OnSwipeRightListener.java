package malaria.com.malaria.interfaces;

import malaria.com.malaria.constants.SwipeDirection;

public interface OnSwipeRightListener {
    void swipeRight();
    void setAllowedSwipeDirection(SwipeDirection direction);
}
