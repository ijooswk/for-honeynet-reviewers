.class public LPrivacyMonitor/java/io/PrintStreamIns;
.super LPrivacyMonitor/java/io/FilterOutputStreamIns;
.source "PrintStreamIns.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 13
    invoke-direct {p0}, LPrivacyMonitor/java/io/FilterOutputStreamIns;-><init>()V

    return-void
.end method

.method public static append(Ljava/io/PrintStream;C)Ljava/io/PrintStream;
    .registers 6
    .parameter "w"
    .parameter "c"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 164
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->append(C)Ljava/io/PrintStream;

    move-result-object v1

    .line 166
    .local v1, r:Ljava/io/PrintStream;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.append\narg:\nchar:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 167
    .local v0, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 168
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1b
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_1b} :catch_1c

    .line 173
    .end local v0           #log:Ljava/lang/String;
    :goto_1b
    return-object v1

    .line 170
    :catch_1c
    move-exception v2

    goto :goto_1b
.end method

.method public static append(Ljava/io/PrintStream;Ljava/lang/CharSequence;)Ljava/io/PrintStream;
    .registers 6
    .parameter "w"
    .parameter "csq"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 176
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->append(Ljava/lang/CharSequence;)Ljava/io/PrintStream;

    move-result-object v1

    .line 178
    .local v1, r:Ljava/io/PrintStream;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.append\narg:\nCharSequence:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 179
    .local v0, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 180
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1b
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_1b} :catch_1c

    .line 185
    .end local v0           #log:Ljava/lang/String;
    :goto_1b
    return-object v1

    .line 182
    :catch_1c
    move-exception v2

    goto :goto_1b
.end method

.method public static append(Ljava/io/PrintStream;Ljava/lang/CharSequence;II)Ljava/io/PrintStream;
    .registers 8
    .parameter "w"
    .parameter "csq"
    .parameter "start"
    .parameter "end"

    .prologue
    .line 188
    invoke-virtual {p0, p1, p2, p3}, Ljava/io/PrintStream;->append(Ljava/lang/CharSequence;II)Ljava/io/PrintStream;

    move-result-object v1

    .line 190
    .local v1, r:Ljava/io/PrintStream;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.append\narg:\nCharSequence:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nstart:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nend:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 191
    .local v0, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 192
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_2f
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_2f} :catch_30

    .line 197
    .end local v0           #log:Ljava/lang/String;
    :goto_2f
    return-object v1

    .line 194
    :catch_30
    move-exception v2

    goto :goto_2f
.end method

.method public static varargs format(Ljava/io/PrintStream;Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
    .registers 10
    .parameter "w"
    .parameter "format"
    .parameter "args"

    .prologue
    .line 201
    invoke-virtual {p0, p1, p2}, Ljava/io/PrintStream;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    move-result-object v2

    .line 203
    .local v2, r:Ljava/io/PrintStream;
    :try_start_4
    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "PrintStream.format\narg:\nformat:"

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 204
    .local v1, log:Ljava/lang/String;
    array-length v4, p2

    const/4 v3, 0x0

    :goto_15
    if-lt v3, v4, :cond_20

    .line 207
    const-string v3, "PrivacyMonitor"

    invoke-static {v3, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 208
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 213
    .end local v1           #log:Ljava/lang/String;
    :goto_1f
    return-object v2

    .line 204
    .restart local v1       #log:Ljava/lang/String;
    :cond_20
    aget-object v0, p2, v3

    .line 205
    .local v0, arg:Ljava/lang/Object;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-static {v1}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v6, "\nargs:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_38
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_38} :catch_3c

    move-result-object v1

    .line 204
    add-int/lit8 v3, v3, 0x1

    goto :goto_15

    .line 210
    .end local v0           #arg:Ljava/lang/Object;
    .end local v1           #log:Ljava/lang/String;
    :catch_3c
    move-exception v3

    goto :goto_1f
.end method

.method public static varargs format(Ljava/io/PrintStream;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
    .registers 11
    .parameter "w"
    .parameter "l"
    .parameter "format"
    .parameter "args"

    .prologue
    .line 217
    invoke-virtual {p0, p1, p2, p3}, Ljava/io/PrintStream;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    move-result-object v2

    .line 219
    .local v2, r:Ljava/io/PrintStream;
    :try_start_4
    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "PrintStream.format\narg:\nlocale:"

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "\nformat:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 220
    .local v1, log:Ljava/lang/String;
    array-length v4, p3

    const/4 v3, 0x0

    :goto_1f
    if-lt v3, v4, :cond_2a

    .line 223
    const-string v3, "PrivacyMonitor"

    invoke-static {v3, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 224
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 229
    .end local v1           #log:Ljava/lang/String;
    :goto_29
    return-object v2

    .line 220
    .restart local v1       #log:Ljava/lang/String;
    :cond_2a
    aget-object v0, p3, v3

    .line 221
    .local v0, arg:Ljava/lang/Object;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-static {v1}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v6, "\nargs:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_42
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_42} :catch_46

    move-result-object v1

    .line 220
    add-int/lit8 v3, v3, 0x1

    goto :goto_1f

    .line 226
    .end local v0           #arg:Ljava/lang/Object;
    .end local v1           #log:Ljava/lang/String;
    :catch_46
    move-exception v3

    goto :goto_29
.end method

.method public static init(Ljava/io/PrintStream;)V
    .registers 3
    .parameter "os"

    .prologue
    .line 16
    :try_start_0
    const-string v0, "FilterWriter.<init>"

    .line 17
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 18
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_a
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_a} :catch_b

    .line 23
    .end local v0           #log:Ljava/lang/String;
    :goto_a
    return-void

    .line 20
    :catch_b
    move-exception v1

    goto :goto_a
.end method

.method public static init(Ljava/io/PrintStream;Ljava/io/File;)V
    .registers 6
    .parameter "ps"
    .parameter "file"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/FileNotFoundException;
        }
    .end annotation

    .prologue
    .line 87
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.<init>\narg:\nFile:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 88
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 89
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 91
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "file:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 92
    .local v0, desc:Ljava/lang/String;
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_29
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_29} :catch_2a

    .line 97
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_29
    return-void

    .line 94
    :catch_2a
    move-exception v2

    goto :goto_29
.end method

.method public static init(Ljava/io/PrintStream;Ljava/io/File;Ljava/lang/String;)V
    .registers 7
    .parameter "ps"
    .parameter "file"
    .parameter "csn"

    .prologue
    .line 101
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.<init>\narg:\nFile:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nString:(csn)"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 102
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 103
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 105
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "file:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 106
    .local v0, desc:Ljava/lang/String;
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_33
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_33} :catch_34

    .line 111
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_33
    return-void

    .line 108
    :catch_34
    move-exception v2

    goto :goto_33
.end method

.method public static init(Ljava/io/PrintStream;Ljava/io/OutputStream;)V
    .registers 6
    .parameter "os"
    .parameter "out"

    .prologue
    .line 26
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.<init>\narg:\nOutputStream:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 27
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 28
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 30
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 31
    .local v0, desc:Ljava/lang/String;
    if-nez v0, :cond_1e

    .line 39
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_1d
    return-void

    .line 34
    .restart local v0       #desc:Ljava/lang/String;
    .restart local v1       #log:Ljava/lang/String;
    :cond_1e
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_21
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_21} :catch_22

    goto :goto_1d

    .line 36
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :catch_22
    move-exception v2

    goto :goto_1d
.end method

.method public static init(Ljava/io/PrintStream;Ljava/io/OutputStream;I)V
    .registers 7
    .parameter "os"
    .parameter "out"
    .parameter "size"

    .prologue
    .line 43
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.<init>\narg:\nOutputStream:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nint:(size)"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 44
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 45
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 47
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 48
    .local v0, desc:Ljava/lang/String;
    if-nez v0, :cond_28

    .line 56
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_27
    return-void

    .line 51
    .restart local v0       #desc:Ljava/lang/String;
    .restart local v1       #log:Ljava/lang/String;
    :cond_28
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_2b
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_2b} :catch_2c

    goto :goto_27

    .line 53
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :catch_2c
    move-exception v2

    goto :goto_27
.end method

.method public static init(Ljava/io/PrintStream;Ljava/io/OutputStream;Z)V
    .registers 7
    .parameter "ps"
    .parameter "out"
    .parameter "autoFlush"

    .prologue
    .line 59
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.<init>\narg:\nOutputStream:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nboolean:(autoFlush)"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 60
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 61
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 63
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 64
    .local v0, desc:Ljava/lang/String;
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_28
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_28} :catch_29

    .line 69
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_28
    return-void

    .line 66
    :catch_29
    move-exception v2

    goto :goto_28
.end method

.method public static init(Ljava/io/PrintStream;Ljava/io/OutputStream;ZLjava/lang/String;)V
    .registers 8
    .parameter "ps"
    .parameter "out"
    .parameter "autoFlush"
    .parameter "enc"

    .prologue
    .line 73
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.<init>\narg:\nOutputStream:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nboolean:(autoFlush)"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nString:(enc)"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 74
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 75
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 77
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 78
    .local v0, desc:Ljava/lang/String;
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_32
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_32} :catch_33

    .line 83
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_32
    return-void

    .line 80
    :catch_33
    move-exception v2

    goto :goto_32
.end method

.method public static init(Ljava/io/PrintStream;Ljava/lang/String;)V
    .registers 6
    .parameter "ps"
    .parameter "fileName"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/FileNotFoundException;
        }
    .end annotation

    .prologue
    .line 115
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.<init>\narg:\nString:(fileName)"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 116
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 117
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 119
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "file:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 120
    .local v0, desc:Ljava/lang/String;
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_29
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_29} :catch_2a

    .line 125
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_29
    return-void

    .line 122
    :catch_2a
    move-exception v2

    goto :goto_29
.end method

.method public static init(Ljava/io/PrintStream;Ljava/lang/String;Ljava/lang/String;)V
    .registers 7
    .parameter "ps"
    .parameter "fileName"
    .parameter "csn"

    .prologue
    .line 129
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "PrintStream.<init>\narg:\nString:(fileName)"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nString:(csn)"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 130
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 131
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 133
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "file:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 134
    .local v0, desc:Ljava/lang/String;
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_33
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_33} :catch_34

    .line 139
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_33
    return-void

    .line 136
    :catch_34
    move-exception v2

    goto :goto_33
.end method

.method public static print(Ljava/io/PrintStream;C)V
    .registers 5
    .parameter "w"
    .parameter "ch"

    .prologue
    .line 277
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->print(C)V

    .line 279
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\nchar:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 280
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 281
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 286
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 283
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static print(Ljava/io/PrintStream;D)V
    .registers 6
    .parameter "w"
    .parameter "dnum"

    .prologue
    .line 289
    invoke-virtual {p0, p1, p2}, Ljava/io/PrintStream;->print(D)V

    .line 291
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\ndouble:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1, p2}, Ljava/lang/StringBuilder;->append(D)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 292
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 293
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 298
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 295
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static print(Ljava/io/PrintStream;F)V
    .registers 5
    .parameter "w"
    .parameter "fnum"

    .prologue
    .line 301
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->print(F)V

    .line 303
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\nfloat:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 304
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 305
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 310
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 307
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static print(Ljava/io/PrintStream;I)V
    .registers 5
    .parameter "w"
    .parameter "inum"

    .prologue
    .line 313
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->print(I)V

    .line 315
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\nint:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 316
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 317
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 322
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 319
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static print(Ljava/io/PrintStream;J)V
    .registers 6
    .parameter "w"
    .parameter "lnum"

    .prologue
    .line 325
    invoke-virtual {p0, p1, p2}, Ljava/io/PrintStream;->print(J)V

    .line 327
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\nlong:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 328
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 329
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 334
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 331
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static print(Ljava/io/PrintStream;Ljava/lang/Object;)V
    .registers 5
    .parameter "w"
    .parameter "obj"

    .prologue
    .line 337
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->print(Ljava/lang/Object;)V

    .line 339
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\nObject:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 340
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 341
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 346
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 343
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static print(Ljava/io/PrintStream;Ljava/lang/String;)V
    .registers 5
    .parameter "w"
    .parameter "str"

    .prologue
    .line 349
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->print(Ljava/lang/String;)V

    .line 351
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\nString:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 352
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 353
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 358
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 355
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static print(Ljava/io/PrintStream;Z)V
    .registers 5
    .parameter "w"
    .parameter "bool"

    .prologue
    .line 361
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->print(Z)V

    .line 363
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\nbool:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 364
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 365
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 370
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 367
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static print(Ljava/io/PrintStream;[C)V
    .registers 5
    .parameter "w"
    .parameter "charArray"

    .prologue
    .line 265
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->print([C)V

    .line 267
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.print\narg:\ncharArray:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    new-instance v2, Ljava/lang/String;

    invoke-direct {v2, p1}, Ljava/lang/String;-><init>([C)V

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 268
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 269
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1f
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1f} :catch_20

    .line 274
    .end local v0           #log:Ljava/lang/String;
    :goto_1f
    return-void

    .line 271
    :catch_20
    move-exception v1

    goto :goto_1f
.end method

.method public static varargs printf(Ljava/io/PrintStream;Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
    .registers 10
    .parameter "w"
    .parameter "format"
    .parameter "args"

    .prologue
    .line 233
    invoke-virtual {p0, p1, p2}, Ljava/io/PrintStream;->printf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    move-result-object v2

    .line 235
    .local v2, r:Ljava/io/PrintStream;
    :try_start_4
    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "PrintStream.printf\narg:\nformat:"

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 236
    .local v1, log:Ljava/lang/String;
    array-length v4, p2

    const/4 v3, 0x0

    :goto_15
    if-lt v3, v4, :cond_20

    .line 239
    const-string v3, "PrivacyMonitor"

    invoke-static {v3, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 240
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 245
    .end local v1           #log:Ljava/lang/String;
    :goto_1f
    return-object v2

    .line 236
    .restart local v1       #log:Ljava/lang/String;
    :cond_20
    aget-object v0, p2, v3

    .line 237
    .local v0, arg:Ljava/lang/Object;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-static {v1}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v6, "\nargs:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_38
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_38} :catch_3c

    move-result-object v1

    .line 236
    add-int/lit8 v3, v3, 0x1

    goto :goto_15

    .line 242
    .end local v0           #arg:Ljava/lang/Object;
    .end local v1           #log:Ljava/lang/String;
    :catch_3c
    move-exception v3

    goto :goto_1f
.end method

.method public static varargs printf(Ljava/io/PrintStream;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
    .registers 11
    .parameter "w"
    .parameter "l"
    .parameter "format"
    .parameter "args"

    .prologue
    .line 249
    invoke-virtual {p0, p1, p2, p3}, Ljava/io/PrintStream;->printf(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    move-result-object v2

    .line 251
    .local v2, r:Ljava/io/PrintStream;
    :try_start_4
    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "PrintStream.printf\narg:\nlocale:"

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "\nformat:"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 252
    .local v1, log:Ljava/lang/String;
    array-length v4, p3

    const/4 v3, 0x0

    :goto_1f
    if-lt v3, v4, :cond_2a

    .line 255
    const-string v3, "PrivacyMonitor"

    invoke-static {v3, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 256
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 261
    .end local v1           #log:Ljava/lang/String;
    :goto_29
    return-object v2

    .line 252
    .restart local v1       #log:Ljava/lang/String;
    :cond_2a
    aget-object v0, p3, v3

    .line 253
    .local v0, arg:Ljava/lang/Object;
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-static {v1}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v6, "\nargs:"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_42
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_42} :catch_46

    move-result-object v1

    .line 252
    add-int/lit8 v3, v3, 0x1

    goto :goto_1f

    .line 258
    .end local v0           #arg:Ljava/lang/Object;
    .end local v1           #log:Ljava/lang/String;
    :catch_46
    move-exception v3

    goto :goto_29
.end method

.method public static println(Ljava/io/PrintStream;C)V
    .registers 5
    .parameter "w"
    .parameter "ch"

    .prologue
    .line 385
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->println(C)V

    .line 387
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\nchar:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 388
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 389
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 394
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 391
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static println(Ljava/io/PrintStream;D)V
    .registers 6
    .parameter "w"
    .parameter "dnum"

    .prologue
    .line 397
    invoke-virtual {p0, p1, p2}, Ljava/io/PrintStream;->print(D)V

    .line 399
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\ndouble:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1, p2}, Ljava/lang/StringBuilder;->append(D)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 400
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 401
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 406
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 403
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static println(Ljava/io/PrintStream;F)V
    .registers 5
    .parameter "w"
    .parameter "fnum"

    .prologue
    .line 409
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->println(F)V

    .line 411
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\nfloat:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 412
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 413
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 418
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 415
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static println(Ljava/io/PrintStream;I)V
    .registers 5
    .parameter "w"
    .parameter "inum"

    .prologue
    .line 421
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->println(I)V

    .line 423
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\nint:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 424
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 425
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 430
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 427
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static println(Ljava/io/PrintStream;J)V
    .registers 6
    .parameter "w"
    .parameter "lnum"

    .prologue
    .line 433
    invoke-virtual {p0, p1, p2}, Ljava/io/PrintStream;->println(J)V

    .line 435
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\nlong:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 436
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 437
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 442
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 439
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static println(Ljava/io/PrintStream;Ljava/lang/Object;)V
    .registers 5
    .parameter "w"
    .parameter "obj"

    .prologue
    .line 445
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->println(Ljava/lang/Object;)V

    .line 447
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\nObject:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 448
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 449
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 454
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 451
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static println(Ljava/io/PrintStream;Ljava/lang/String;)V
    .registers 5
    .parameter "w"
    .parameter "str"

    .prologue
    .line 457
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 459
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\nString:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 460
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 461
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 466
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 463
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static println(Ljava/io/PrintStream;Z)V
    .registers 5
    .parameter "w"
    .parameter "bool"

    .prologue
    .line 469
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->println(Z)V

    .line 471
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\nbool:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 472
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 473
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 478
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 475
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static println(Ljava/io/PrintStream;[C)V
    .registers 5
    .parameter "w"
    .parameter "charArray"

    .prologue
    .line 373
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->println([C)V

    .line 375
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.println\narg:\ncharArray:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    new-instance v2, Ljava/lang/String;

    invoke-direct {v2, p1}, Ljava/lang/String;-><init>([C)V

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 376
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 377
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1f
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1f} :catch_20

    .line 382
    .end local v0           #log:Ljava/lang/String;
    :goto_1f
    return-void

    .line 379
    :catch_20
    move-exception v1

    goto :goto_1f
.end method

.method public static write(Ljava/io/PrintStream;I)V
    .registers 5
    .parameter "w"
    .parameter "oneByte"

    .prologue
    .line 153
    invoke-virtual {p0, p1}, Ljava/io/PrintStream;->write(I)V

    .line 155
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.write\narg:\noneByte:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 156
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 157
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 162
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 159
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public static write(Ljava/io/PrintStream;[BII)V
    .registers 7
    .parameter "w"
    .parameter "buf"
    .parameter "offset"
    .parameter "count"

    .prologue
    .line 142
    invoke-virtual {p0, p1, p2, p3}, Ljava/io/PrintStream;->write([BII)V

    .line 144
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "PrintStream.write\narg:\nbuffer:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    new-instance v2, Ljava/lang/String;

    invoke-direct {v2, p1}, Ljava/lang/String;-><init>([B)V

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "\noffset:"

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

    .line 145
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 146
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_33
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_33} :catch_34

    .line 151
    .end local v0           #log:Ljava/lang/String;
    :goto_33
    return-void

    .line 148
    :catch_34
    move-exception v1

    goto :goto_33
.end method
