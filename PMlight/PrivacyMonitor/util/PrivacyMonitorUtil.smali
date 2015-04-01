.class public LPrivacyMonitor/util/PrivacyMonitorUtil;
.super Ljava/lang/Object;
.source "PrivacyMonitorUtil.java"


# annotations
.annotation build Landroid/annotation/SuppressLint;
    value = {
        "SimpleDateFormat"
    }
.end annotation


# static fields
.field public static osdesc:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/io/OutputStream;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public static wrdesc:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/io/Writer;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .registers 1

    .prologue
    .line 16
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdesc:Ljava/util/HashMap;

    .line 17
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdesc:Ljava/util/HashMap;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .prologue
    .line 15
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static osdescget(Ljava/io/OutputStream;)Ljava/lang/String;
    .registers 2
    .parameter "os"

    .prologue
    .line 37
    sget-object v0, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdesc:Ljava/util/HashMap;

    invoke-virtual {v0, p0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    return-object v0
.end method

.method public static osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    .registers 3
    .parameter "os"
    .parameter "desc"

    .prologue
    .line 34
    sget-object v0, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdesc:Ljava/util/HashMap;

    invoke-virtual {v0, p0, p1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 35
    return-void
.end method

.method public static recordLog(Ljava/lang/String;)V
    .registers 12
    .parameter "log"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 19
    const-string v8, "privacylog"

    monitor-enter v8

    .line 20
    :try_start_3
    const-string v5, "privacymonitor_packagename"

    .line 21
    .local v5, packagename:Ljava/lang/String;
    new-instance v3, Ljava/io/File;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v9

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v9, "/privacymonitor/"

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-direct {v3, v7}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 22
    .local v3, logdir:Ljava/io/File;
    new-instance v4, Ljava/io/File;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v9, "/privacy.log"

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-direct {v4, v7}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 23
    .local v4, logfile:Ljava/io/File;
    invoke-virtual {v3}, Ljava/io/File;->exists()Z

    move-result v7

    if-nez v7, :cond_46

    invoke-virtual {v3}, Ljava/io/File;->mkdirs()Z

    .line 24
    :cond_46
    invoke-virtual {v4}, Ljava/io/File;->exists()Z

    move-result v7

    if-nez v7, :cond_4f

    invoke-virtual {v4}, Ljava/io/File;->createNewFile()Z

    .line 25
    :cond_4f
    new-instance v2, Ljava/io/FileOutputStream;

    const/4 v7, 0x1

    invoke-direct {v2, v4, v7}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V

    .line 26
    .local v2, fos:Ljava/io/FileOutputStream;
    new-instance v1, Ljava/text/SimpleDateFormat;

    const-string v7, "yyyy.MM.dd HH:mm:ss\n"

    invoke-direct {v1, v7}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 27
    .local v1, formatter:Ljava/text/SimpleDateFormat;
    new-instance v0, Ljava/util/Date;

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v9

    invoke-direct {v0, v9, v10}, Ljava/util/Date;-><init>(J)V

    .line 28
    .local v0, curDate:Ljava/util/Date;
    invoke-virtual {v1, v0}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v6

    .line 29
    .local v6, time:Ljava/lang/String;
    new-instance v7, Ljava/lang/StringBuilder;

    const-string v9, "\n---------------\n"

    invoke-direct {v7, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v7, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/String;->getBytes()[B

    move-result-object v7

    invoke-virtual {v2, v7}, Ljava/io/FileOutputStream;->write([B)V

    .line 30
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->close()V

    .line 19
    monitor-exit v8

    .line 32
    return-void

    .line 19
    .end local v0           #curDate:Ljava/util/Date;
    .end local v1           #formatter:Ljava/text/SimpleDateFormat;
    .end local v2           #fos:Ljava/io/FileOutputStream;
    .end local v3           #logdir:Ljava/io/File;
    .end local v4           #logfile:Ljava/io/File;
    .end local v5           #packagename:Ljava/lang/String;
    .end local v6           #time:Ljava/lang/String;
    :catchall_88
    move-exception v7

    monitor-exit v8
    :try_end_8a
    .catchall {:try_start_3 .. :try_end_8a} :catchall_88

    throw v7
.end method

.method public static wrdescget(Ljava/io/Writer;)Ljava/lang/String;
    .registers 2
    .parameter "wr"

    .prologue
    .line 43
    sget-object v0, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdesc:Ljava/util/HashMap;

    invoke-virtual {v0, p0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    return-object v0
.end method

.method public static wrdescput(Ljava/io/Writer;Ljava/lang/String;)V
    .registers 3
    .parameter "wr"
    .parameter "desc"

    .prologue
    .line 40
    sget-object v0, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdesc:Ljava/util/HashMap;

    invoke-virtual {v0, p0, p1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 41
    return-void
.end method
