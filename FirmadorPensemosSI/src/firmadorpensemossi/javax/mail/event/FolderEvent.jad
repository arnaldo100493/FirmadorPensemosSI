// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FolderEvent.java

package javax.mail.event;

import javax.mail.Folder;

// Referenced classes of package javax.mail.event:
//            MailEvent, FolderListener

public class FolderEvent extends MailEvent
{

    public FolderEvent(Object source, Folder folder, int type)
    {
        this(source, folder, folder, type);
    }

    public FolderEvent(Object source, Folder oldFolder, Folder newFolder, int type)
    {
        super(source);
        folder = oldFolder;
        this.newFolder = newFolder;
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public Folder getFolder()
    {
        return folder;
    }

    public Folder getNewFolder()
    {
        return newFolder;
    }

    public void dispatch(Object listener)
    {
        if(type == 1)
            ((FolderListener)listener).folderCreated(this);
        else
        if(type == 2)
            ((FolderListener)listener).folderDeleted(this);
        else
        if(type == 3)
            ((FolderListener)listener).folderRenamed(this);
    }

    public static final int CREATED = 1;
    public static final int DELETED = 2;
    public static final int RENAMED = 3;
    protected int type;
    protected transient Folder folder;
    protected transient Folder newFolder;
    private static final long serialVersionUID = 0x493fb076540416e3L;
}
