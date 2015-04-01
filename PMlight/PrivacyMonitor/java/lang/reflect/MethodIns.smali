.class public LPrivacyMonitor/java/lang/reflect/MethodIns;
.super Ljava/lang/Object;
.source "MethodIns.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static varargs invoke(Ljava/lang/reflect/Method;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    .registers 9
    .parameter "m"
    .parameter "receiver"
    .parameter "args"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/IllegalArgumentException;,
            Ljava/lang/IllegalAccessException;,
            Ljava/lang/reflect/InvocationTargetException;
        }
    .end annotation

    .prologue
    .line 11
    invoke-virtual {p0, p1, p2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    .line 13
    .local v2, r:Ljava/lang/Object;
    :try_start_4
    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, "Method.invoke\narg:method:"

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, "\nreceiver:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 14
    .local v1, log:Ljava/lang/String;
    new-instance v3, Ljava/lang/StringBuffer;

    invoke-direct {v3, v1}, Ljava/lang/StringBuffer;-><init>(Ljava/lang/String;)V

    .line 15
    .local v3, strbuf:Ljava/lang/StringBuffer;
    if-eqz p2, :cond_28

    .line 16
    const/4 v0, 0x0

    .local v0, i:I
    :goto_25
    array-length v4, p2

    if-lt v0, v4, :cond_35

    .line 20
    .end local v0           #i:I
    :cond_28
    invoke-virtual {v3}, Ljava/lang/StringBuffer;->toString()Ljava/lang/String;

    move-result-object v1

    .line 21
    const-string v4, "PrivacyMonitor"

    invoke-static {v4, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 22
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V

    .line 26
    .end local v1           #log:Ljava/lang/String;
    .end local v3           #strbuf:Ljava/lang/StringBuffer;
    :goto_34
    return-object v2

    .line 17
    .restart local v0       #i:I
    .restart local v1       #log:Ljava/lang/String;
    .restart local v3       #strbuf:Ljava/lang/StringBuffer;
    :cond_35
    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, "\narg"

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    aget-object v5, p2, v0

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuffer;->append(Ljava/lang/String;)Ljava/lang/StringBuffer;
    :try_end_4d
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_4d} :catch_50

    .line 16
    add-int/lit8 v0, v0, 0x1

    goto :goto_25

    .line 24
    .end local v0           #i:I
    .end local v1           #log:Ljava/lang/String;
    .end local v3           #strbuf:Ljava/lang/StringBuffer;
    :catch_50
    move-exception v4

    goto :goto_34
.end method
