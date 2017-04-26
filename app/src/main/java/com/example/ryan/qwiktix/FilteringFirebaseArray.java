package com.example.ryan.qwiktix;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.Query;

        import java.util.ArrayList;

/**
 * This class implements an array-like collection on top of a Firebase location.
 */
class FilteringFirebaseArray implements ChildEventListener {
    public interface OnChangedListener {
        enum EventType { Added, Changed, Removed, Moved }
        void onChanged(EventType type, int index, int oldIndex);
    }

    public interface SnapshotFilter {
        boolean isValid(DataSnapshot shot);
    }

    private class SnapshotWithFlag {
        boolean isValid;
        DataSnapshot snapshot;

        SnapshotWithFlag(DataSnapshot snap, boolean valid) {
            isValid = valid;
            snapshot = snap;
        }
    }

    private Query mQuery;
    private OnChangedListener mListener;
    private ArrayList<SnapshotWithFlag> mSnapshots;
    private SnapshotFilter mFilter;
    private int mValidCount = 0;

    public FilteringFirebaseArray(Query ref, SnapshotFilter filter) {
        mQuery = ref;
        mSnapshots = new ArrayList<>();
        mQuery.addChildEventListener(this);
        mFilter = filter;
    }

    public void cleanup() {
        mQuery.removeEventListener(this);
    }

    public int getCount() {
        return mValidCount;
    }

    public DataSnapshot getItem(int index) {
        int current = 0;
        for (SnapshotWithFlag snaphot : mSnapshots) {
            if (snaphot.isValid) {
                if (current == index) {
                    return snaphot.snapshot;
                }
                ++current;
            }
        }
        throw new IllegalArgumentException("Index out of bounds");
    }

    private int getValidIndex(int unfilteredIndex) {
        int validCount = 0;
        for (int i = 0; i < unfilteredIndex; ++i) {
            SnapshotWithFlag cur = mSnapshots.get(i);
            if (cur.isValid) {
                ++validCount;
            }
        }
        return validCount;
    }

    private int getIndexForKey(String key) {
        int index = 0;
        for (SnapshotWithFlag snapshot : mSnapshots) {
            if (snapshot.snapshot.getKey().equals(key)) {
                return index;
            } else {
                index++;
            }
        }
        throw new IllegalArgumentException("Key not found");
    }

    // Start of ChildEventListener methods
    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
        int index = 0;
        if (previousChildKey != null) {
            index = getIndexForKey(previousChildKey) + 1;
        }
        boolean isValid = mFilter.isValid(snapshot);
        mSnapshots.add(index, new SnapshotWithFlag(snapshot, isValid));
        if (isValid) {
            ++mValidCount;
            notifyChangedListeners(OnChangedListener.EventType.Added, getValidIndex(index));
        }
    }

    public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
        int index = getIndexForKey(snapshot.getKey());
        boolean isValid = mFilter.isValid(snapshot);
        boolean wasValid = mSnapshots.get(index).isValid;
        mSnapshots.set(index, new SnapshotWithFlag(snapshot, isValid));
        if (isValid && wasValid) {
            notifyChangedListeners(OnChangedListener.EventType.Changed, index);
        } else if (isValid) {
            ++mValidCount;
            notifyChangedListeners(OnChangedListener.EventType.Added, getValidIndex(index));
        } else if (wasValid) {
            --mValidCount;
            notifyChangedListeners(OnChangedListener.EventType.Removed, getValidIndex(index));
        }
    }

    public void onChildRemoved(DataSnapshot snapshot) {
        int index = getIndexForKey(snapshot.getKey());
        boolean wasValid = mSnapshots.get(index).isValid;
        int validIndex = getValidIndex(index);
        mSnapshots.remove(index);
        if (wasValid) {
            --mValidCount;
            notifyChangedListeners(OnChangedListener.EventType.Removed, validIndex);
        }
    }

    public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
        // hopefully we won't get changed data in onChildMoved, no need to check the validity again
        int oldIndex = getIndexForKey(snapshot.getKey());
        boolean isValid = mSnapshots.get(oldIndex).isValid;
        int oldValidIndex = getValidIndex(oldIndex);
        mSnapshots.remove(oldIndex);
        int newIndex = previousChildKey == null ? 0 : (getIndexForKey(previousChildKey) + 1);
        mSnapshots.add(newIndex, new SnapshotWithFlag(snapshot, isValid));
        int newValidIndex = getValidIndex(newIndex);
        if (isValid && (oldValidIndex != newValidIndex)) {
            notifyChangedListeners(OnChangedListener.EventType.Moved, newValidIndex, oldValidIndex);
        }
    }

    public void onCancelled(DatabaseError firebaseError) {
        // TODO: what do we do with this?
    }
    // End of ChildEventListener methods

    public void setOnChangedListener(OnChangedListener listener) {
        mListener = listener;
    }

    protected void notifyChangedListeners(OnChangedListener.EventType type, int index) {
        notifyChangedListeners(type, index, -1);
    }

    protected void notifyChangedListeners(OnChangedListener.EventType type, int index, int oldIndex) {
        if (mListener != null) {
            mListener.onChanged(type, index, oldIndex);
        }
    }
}