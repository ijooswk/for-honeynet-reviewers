.class public LPrivacyMonitor/java/lang/ProcessIns;
.super Ljava/lang/Object;
.source "ProcessIns.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getOutputStream(Ljava/lang/Process;)Ljava/io/OutputStream;
    .registers 5
    .parameter "p"

    .prologue
    .line 9
    invoke-virtual {p0}, Ljava/lang/Process;->getOutputStream()Ljava/io/OutputStream;

    move-result-object v1

    .line 11
    .local v1, r:Ljava/io/OutputStream;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "process:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 12
    .local v0, desc:Ljava/lang/String;
    invoke-static {v1, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_16
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_16} :catch_17

    .line 16
    .end local v0           #desc:Ljava/lang/String;
    :goto_16
    return-object v1

    .line 14
    :catch_17
    move-exception v2

    goto :goto_16
.end method
