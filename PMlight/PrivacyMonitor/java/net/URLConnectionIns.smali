.class public LPrivacyMonitor/java/net/URLConnectionIns;
.super Ljava/lang/Object;
.source "URLConnectionIns.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getOutputStream(Ljava/net/URLConnection;)Ljava/io/OutputStream;
    .registers 4
    .parameter "s"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 11
    invoke-virtual {p0}, Ljava/net/URLConnection;->getOutputStream()Ljava/io/OutputStream;

    move-result-object v1

    .line 13
    .local v1, r:Ljava/io/OutputStream;
    :try_start_4
    invoke-virtual {p0}, Ljava/net/URLConnection;->toString()Ljava/lang/String;

    move-result-object v0

    .line 14
    .local v0, desc:Ljava/lang/String;
    invoke-static {v1, v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->osdescput(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_b
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_b} :catch_c

    .line 18
    .end local v0           #desc:Ljava/lang/String;
    :goto_b
    return-object v1

    .line 16
    :catch_c
    move-exception v2

    goto :goto_b
.end method
