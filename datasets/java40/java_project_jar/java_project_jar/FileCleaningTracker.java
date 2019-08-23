// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileCleaningTracker.java

package org.apache.commons.io;

import java.io.File;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.*;

// Referenced classes of package org.apache.commons.io:
//            FileDeleteStrategy

public class FileCleaningTracker
{
    private static final class Tracker extends PhantomReference
    {

        public String getPath()
        {
            return path;
        }

        public boolean delete()
        {
            return deleteStrategy.deleteQuietly(new File(path));
        }

        private final String path;
        private final FileDeleteStrategy deleteStrategy;

        Tracker(String path, FileDeleteStrategy deleteStrategy, Object marker, ReferenceQueue queue)
        {
            super(marker, queue);
            this.path = path;
            this.deleteStrategy = deleteStrategy != null ? deleteStrategy : FileDeleteStrategy.NORMAL;
        }
    }

    private final class Reaper extends Thread
    {

        public void run()
        {
            while(!exitWhenFinished || trackers.size() > 0) 
                try
                {
                    Tracker tracker = (Tracker)q.remove();
                    trackers.remove(tracker);
                    if(!tracker.delete())
                        deleteFailures.add(tracker.getPath());
                    tracker.clear();
                }
                catch(InterruptedException e) { }
        }

        final FileCleaningTracker this$0;

        Reaper()
        {
            this$0 = FileCleaningTracker.this;
            super("File Reaper");
            setPriority(10);
            setDaemon(true);
        }
    }


    public FileCleaningTracker()
    {
        q = new ReferenceQueue();
        exitWhenFinished = false;
    }

    public void track(File file, Object marker)
    {
        track(file, marker, (FileDeleteStrategy)null);
    }

    public void track(File file, Object marker, FileDeleteStrategy deleteStrategy)
    {
        if(file == null)
        {
            throw new NullPointerException("The file must not be null");
        } else
        {
            addTracker(file.getPath(), marker, deleteStrategy);
            return;
        }
    }

    public void track(String path, Object marker)
    {
        track(path, marker, (FileDeleteStrategy)null);
    }

    public void track(String path, Object marker, FileDeleteStrategy deleteStrategy)
    {
        if(path == null)
        {
            throw new NullPointerException("The path must not be null");
        } else
        {
            addTracker(path, marker, deleteStrategy);
            return;
        }
    }

    private synchronized void addTracker(String path, Object marker, FileDeleteStrategy deleteStrategy)
    {
        if(exitWhenFinished)
            throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
        if(reaper == null)
        {
            reaper = new Reaper();
            reaper.start();
        }
        trackers.add(new Tracker(path, deleteStrategy, marker, q));
    }

    public int getTrackCount()
    {
        return trackers.size();
    }

    public List getDeleteFailures()
    {
        return deleteFailures;
    }

    public synchronized void exitWhenFinished()
    {
        exitWhenFinished = true;
        if(reaper != null)
            synchronized(reaper)
            {
                reaper.interrupt();
            }
    }

    ReferenceQueue q;
    final Collection trackers = Collections.synchronizedSet(new HashSet());
    final List deleteFailures = Collections.synchronizedList(new ArrayList());
    volatile boolean exitWhenFinished;
    Thread reaper;
}
