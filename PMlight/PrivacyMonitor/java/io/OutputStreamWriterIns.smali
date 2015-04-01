.class public LPrivacyMonitor/java/io/OutputStreamWriterIns;
.super LPrivacyMonitor/java/io/WriterIns;
.source "OutputStreamWriterIns.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 13
    invoke-direct {p0}, LPrivacyMonitor/java/io/WriterIns;-><init>()V

    return-void
.end method

.method public static append(Ljava/io/OutputStreamWriter;C)Ljava/io/Writer;
    .registers 6
    .parameter "w"
    .parameter "c"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 73
    invoke-virtual {p0, p1}, Ljava/io/OutputStreamWriter;->append(C)Ljava/io/Writer;

    move-result-object v1

    .line 75
    .local v1, r:Ljava/io/Writer;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "OutputStreamWriter.append\narg:\nchar:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 76
    .local v0, log:Ljava/lang/String;
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v3, "\ndesc:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescget(Ljava/io/Writer;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 77
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 78
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_36
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_36} :catch_37

    .line 83
    .end local v0           #log:Ljava/lang/String;
    :goto_36
    return-object v1

    .line 80
    :catch_37
    move-exception v2

    goto :goto_36
.end method

.method public static append(Ljava/io/OutputStreamWriter;Ljava/lang/CharSequence;)Ljava/io/Writer;
    .registers 6
    .parameter "w"
    .parameter "csq"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 86
    invoke-virtual {p0, p1}, Ljava/io/OutputStreamWriter;->append(Ljava/lang/CharSequence;)Ljava/io/Writer;

    move-result-object v1

    .line 88
    .local v1, r:Ljava/io/Writer;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "OutputStreamWriter.append\narg:\nCharSequence:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 89
    .local v0, log:Ljava/lang/String;
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v3, "\ndesc:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescget(Ljava/io/Writer;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 90
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 91
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_36
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_36} :catch_37

    .line 96
    .end local v0           #log:Ljava/lang/String;
    :goto_36
    return-object v1

    .line 93
    :catch_37
    move-exception v2

    goto :goto_36
.end method

.method public static append(Ljava/io/OutputStreamWriter;Ljava/lang/CharSequence;II)Ljava/io/Writer;
    .registers 8
    .parameter "w"
    .parameter "csq"
    .parameter "start"
    .parameter "end"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 99
    invoke-virtual {p0, p1, p2, p3}, Ljava/io/OutputStreamWriter;->append(Ljava/lang/CharSequence;II)Ljava/io/Writer;

    move-result-object v1

    .line 101
    .local v1, r:Ljava/io/Writer;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "OutputStreamWriter.append\narg:\nCharSequence:"

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

    .line 102
    .local v0, log:Ljava/lang/String;
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v3, "\ndesc:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescget(Ljava/io/Writer;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 103
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 104
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_4a
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_4a} :catch_4b

    .line 109
    .end local v0           #log:Ljava/lang/String;
    :goto_4a
    return-object v1

    .line 106
    :catch_4b
    move-exception v2

    goto :goto_4a
.end method

.method public static init(Ljava/io/OutputStreamWriter;Ljava/io/OutputStream;)V
    .registers 6
    .parameter "w"
    .parameter "out"

    .prologue
    .line 113
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "OutputStream.<init>\narg:\nOutputStream"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 114
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 115
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 117
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 118
    .local v0, desc:Ljava/lang/String;
    if-nez v0, :cond_1e

    .line 126
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_1d
    return-void

    .line 121
    .restart local v0       #desc:Ljava/lang/String;
    .restart local v1       #log:Ljava/lang/String;
    :cond_1e
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescput(Ljava/io/Writer;Ljava/lang/String;)V
    :try_end_21
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_21} :catch_22

    goto :goto_1d

    .line 123
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :catch_22
    move-exception v2

    goto :goto_1d
.end method

.method public static init(Ljava/io/OutputStreamWriter;Ljava/io/OutputStream;Ljava/lang/String;)V
    .registers 7
    .parameter "w"
    .parameter "out"
    .parameter "enc"

    .prologue
    .line 129
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "OutputStream.<init>\narg:\nOutputStream"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nString:(enc)"

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
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 134
    .local v0, desc:Ljava/lang/String;
    if-nez v0, :cond_28

    .line 142
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_27
    return-void

    .line 137
    .restart local v0       #desc:Ljava/lang/String;
    .restart local v1       #log:Ljava/lang/String;
    :cond_28
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescput(Ljava/io/Writer;Ljava/lang/String;)V
    :try_end_2b
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_2b} :catch_2c

    goto :goto_27

    .line 139
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :catch_2c
    move-exception v2

    goto :goto_27
.end method

.method public static init(Ljava/io/OutputStreamWriter;Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
    .registers 7
    .parameter "w"
    .parameter "out"
    .parameter "cs"

    .prologue
    .line 145
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "OutputStream.<init>\narg:\nOutputStream"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nCharset:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 146
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 147
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 149
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 150
    .local v0, desc:Ljava/lang/String;
    if-nez v0, :cond_28

    .line 158
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_27
    return-void

    .line 153
    .restart local v0       #desc:Ljava/lang/String;
    .restart local v1       #log:Ljava/lang/String;
    :cond_28
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescput(Ljava/io/Writer;Ljava/lang/String;)V
    :try_end_2b
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_2b} :catch_2c

    goto :goto_27

    .line 155
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :catch_2c
    move-exception v2

    goto :goto_27
.end method

.method public static init(Ljava/io/OutputStreamWriter;Ljava/io/OutputStream;Ljava/nio/charset/CharsetEncoder;)V
    .registers 7
    .parameter "w"
    .parameter "out"
    .parameter "enc"

    .prologue
    .line 161
    :try_start_0
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "OutputStream.<init>\narg:\nOutputStream"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "\nCharsetEncoder:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 162
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 163
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 165
    invoke-static {p1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescget(Ljava/io/OutputStream;)Ljava/lang/String;

    move-result-object v0

    .line 166
    .local v0, desc:Ljava/lang/String;
    if-nez v0, :cond_28

    .line 174
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :goto_27
    return-void

    .line 169
    .restart local v0       #desc:Ljava/lang/String;
    .restart local v1       #log:Ljava/lang/String;
    :cond_28
    invoke-static {p0, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescput(Ljava/io/Writer;Ljava/lang/String;)V
    :try_end_2b
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_2b} :catch_2c

    goto :goto_27

    .line 171
    .end local v0           #desc:Ljava/lang/String;
    .end local v1           #log:Ljava/lang/String;
    :catch_2c
    move-exception v2

    goto :goto_27
.end method

.method public static write(Ljava/io/OutputStreamWriter;I)V
    .registers 5
    .parameter "w"
    .parameter "oneChar"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 26
    invoke-virtual {p0, p1}, Ljava/io/OutputStreamWriter;->write(I)V

    .line 28
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "OutputStreamWriter.write\narg:\noneChar:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 29
    .local v0, log:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "\ndesc:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescget(Ljava/io/Writer;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 30
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_35
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_35} :catch_36

    .line 35
    .end local v0           #log:Ljava/lang/String;
    :goto_35
    return-void

    .line 32
    :catch_36
    move-exception v1

    goto :goto_35
.end method

.method public static write(Ljava/io/OutputStreamWriter;Ljava/lang/String;)V
    .registers 5
    .parameter "w"
    .parameter "str"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 61
    invoke-virtual {p0, p1}, Ljava/io/OutputStreamWriter;->write(Ljava/lang/String;)V

    .line 63
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "OutputStreamWriter.write\narg:\nString:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 64
    .local v0, log:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "\ndesc:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescget(Ljava/io/Writer;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 65
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 66
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_35
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_35} :catch_36

    .line 71
    .end local v0           #log:Ljava/lang/String;
    :goto_35
    return-void

    .line 68
    :catch_36
    move-exception v1

    goto :goto_35
.end method

.method public static write(Ljava/io/OutputStreamWriter;Ljava/lang/String;II)V
    .registers 7
    .parameter "w"
    .parameter "str"
    .parameter "offset"
    .parameter "count"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 37
    invoke-virtual {p0, p1, p2, p3}, Ljava/io/OutputStreamWriter;->write(Ljava/lang/String;II)V

    .line 39
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "OutputStreamWriter.write\narg:\nString:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

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

    .line 40
    .local v0, log:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "\ndesc:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescget(Ljava/io/Writer;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 41
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_49
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_49} :catch_4a

    .line 46
    .end local v0           #log:Ljava/lang/String;
    :goto_49
    return-void

    .line 43
    :catch_4a
    move-exception v1

    goto :goto_49
.end method

.method public static write(Ljava/io/OutputStreamWriter;[C)V
    .registers 5
    .parameter "w"
    .parameter "buf"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 49
    invoke-virtual {p0, p1}, Ljava/io/OutputStreamWriter;->write([C)V

    .line 51
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "OutputStreamWriter.write\narg:\nbuffer:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    new-instance v2, Ljava/lang/String;

    invoke-direct {v2, p1}, Ljava/lang/String;-><init>([C)V

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 52
    .local v0, log:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "\ndesc:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescget(Ljava/io/Writer;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 53
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 54
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_3a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3a} :catch_3b

    .line 59
    .end local v0           #log:Ljava/lang/String;
    :goto_3a
    return-void

    .line 56
    :catch_3b
    move-exception v1

    goto :goto_3a
.end method

.method public static write(Ljava/io/OutputStreamWriter;[CII)V
    .registers 7
    .parameter "w"
    .parameter "buf"
    .parameter "offset"
    .parameter "count"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 15
    invoke-virtual {p0, p1, p2, p3}, Ljava/io/OutputStreamWriter;->write([CII)V

    .line 17
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "OutputStreamWriter.write\narg:\nbuffer:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    new-instance v2, Ljava/lang/String;

    invoke-direct {v2, p1}, Ljava/lang/String;-><init>([C)V

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

    .line 18
    .local v0, log:Ljava/lang/String;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static {v0}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "\ndesc:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->wrdescget(Ljava/io/Writer;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 19
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_4e
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_4e} :catch_4f

    .line 24
    .end local v0           #log:Ljava/lang/String;
    :goto_4e
    return-void

    .line 21
    :catch_4f
    move-exception v1

    goto :goto_4e
.end method
