.class public LPrivacyMonitor/android/content/ContextWrapperIns;
.super Ljava/lang/Object;
.source "ContextWrapperIns.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 8
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public sendBroadcast(Landroid/content/ContextWrapper;Landroid/content/Intent;)V
    .registers 6
    .parameter "cw"
    .parameter "intent"

    .prologue
    .line 10
    invoke-virtual {p1, p2}, Landroid/content/ContextWrapper;->sendBroadcast(Landroid/content/Intent;)V

    .line 12
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "ContextWrapper.sendBroadcast\narg:\nintent:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 13
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 14
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1a
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_1a} :catch_1b

    .line 19
    .end local v0           #log:Ljava/lang/String;
    :goto_1a
    return-void

    .line 16
    :catch_1b
    move-exception v1

    goto :goto_1a
.end method

.method public sendBroadcast(Landroid/content/ContextWrapper;Landroid/content/Intent;Ljava/lang/String;)V
    .registers 7
    .parameter "cw"
    .parameter "intent"
    .parameter "receiverPermission"

    .prologue
    .line 22
    invoke-virtual {p1, p2, p3}, Landroid/content/ContextWrapper;->sendBroadcast(Landroid/content/Intent;Ljava/lang/String;)V

    .line 24
    :try_start_3
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "ContextWrapper.sendBroadcast\narg:\nintent:"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "\nreceiverPermission"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 25
    .local v0, log:Ljava/lang/String;
    const-string v1, "PrivacyMonitor"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 26
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_24
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_24} :catch_25

    .line 31
    .end local v0           #log:Ljava/lang/String;
    :goto_24
    return-void

    .line 28
    :catch_25
    move-exception v1

    goto :goto_24
.end method
