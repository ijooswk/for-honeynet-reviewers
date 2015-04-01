.class public LPrivacyMonitor/java/io/BufferedOutputStreamIns;
.super LPrivacyMonitor/java/io/FilterOutputStreamIns;
.source "BufferedOutputStreamIns.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 10
    invoke-direct {p0}, LPrivacyMonitor/java/io/FilterOutputStreamIns;-><init>()V

    return-void
.end method

.method public static init(Ljava/io/BufferedOutputStream;Ljava/io/OutputStream;)V
    .registers 6
    .parameter "bos"
    .parameter "out"

    .prologue
    .line 13
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "BufferedOutputStream.<init>\narg:\nOutputStream:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 14
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 15
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 17
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 18
    .local v0, desc:Ljava/lang/String;
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_1e
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_1e} :catch_1f

    .line 23
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_1e
    return-void

    .line 20
    :catch_1f
    move-exception v2

    goto :goto_1e
.end method

.method public static init(Ljava/io/BufferedOutputStream;Ljava/io/OutputStream;I)V
    .registers 7
    .parameter "bos"
    .parameter "out"
    .parameter "size"

    .prologue
    .line 27
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "BufferedOutputStream.<init>\narg:\nOutputStream:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nint"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 28
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 29
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 31
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 32
    .local v0, desc:Ljava/lang/String;
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_28
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_28} :catch_29

    .line 37
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_28
    return-void

    .line 34
    :catch_29
    move-exception v2

    goto :goto_28
.end method

.method public static write(Ljava/io/BufferedOutputStream;I)V
    .registers 5
    .parameter "os"
    .parameter "oneByte"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 65
    invoke-virtual {p0, p1}, Ljava/io/BufferedOutputStream;->write(I)V

    .line 67
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "BufferedOutputStream.write\narg:\noneByte:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 68
    .local v0, log:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "\ndesc:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 69
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 70
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_35
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_35} :catch_36

    .line 75
    .end local v0           #log:Ljava/lang/String;
    :goto_35
    return-void

    .line 72
    :catch_36
    move-exception v1

    goto :goto_35
.end method

.method public static write(Ljava/io/BufferedOutputStream;[B)V
    .registers 5
    .parameter "os"
    .parameter "buffer"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 39
    invoke-virtual {p0, p1}, Ljava/io/BufferedOutputStream;->write([B)V

    .line 41
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "BufferedOutputStream.write\narg:\nbuffer:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "("

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    new-instance v2, Ljava/lang/String;

    invoke-direct {v2, p1}, Ljava/lang/String;-><init>([B)V

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ")"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 42
    .local v0, log:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "\ndesc:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 43
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 44
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_4a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_4a} :catch_4b

    .line 49
    .end local v0           #log:Ljava/lang/String;
    :goto_4a
    return-void

    .line 46
    :catch_4b
    move-exception v1

    goto :goto_4a
.end method

.method public static write(Ljava/io/BufferedOutputStream;[BII)V
    .registers 7
    .parameter "os"
    .parameter "buffer"
    .parameter "offset"
    .parameter "count"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 52
    invoke-virtual {p0, p1, p2, p3}, Ljava/io/BufferedOutputStream;->write([BII)V

    .line 54
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "BufferedOutputStream.write\narg:\nbuffer:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "("

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    new-instance v2, Ljava/lang/String;

    invoke-direct {v2, p1}, Ljava/lang/String;-><init>([B)V

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ")\noffset:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "\ncount:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 55
    .local v0, log:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "\ndesc:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 56
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 57
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_58
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_58} :catch_59

    .line 62
    .end local v0           #log:Ljava/lang/String;
    :goto_58
    return-void

    .line 59
    :catch_59
    move-exception v1

    goto :goto_58
.end method
