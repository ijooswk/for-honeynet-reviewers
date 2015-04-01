.class public LPrivacyMonitor/android/telephony/TelephonyManagerIns;
.super Ljava/lang/Object;
.source "TelephonyManagerIns.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getCellLocation(Landroid/telephony/TelephonyManager;)Landroid/telephony/CellLocation;
    .registers 8
    .parameter "tm"

    .prologue
    .line 25
    invoke-virtual {p0}, Landroid/telephony/TelephonyManager;->getCellLocation()Landroid/telephony/CellLocation;

    move-result-object v1

    .line 27
    .local v1, location:Landroid/telephony/CellLocation;
    :try_start_4
    move-object v0, v1

    check-cast v0, Landroid/telephony/cdma/CdmaCellLocation;

    move-object v2, v0

    .line 28
    .local v2, location1:Landroid/telephony/cdma/CdmaCellLocation;
    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "TelephonyManager.getCellLocation\nreturn:(cdma)"

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2}, Landroid/telephony/cdma/CdmaCellLocation;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 29
    .local v4, log:Ljava/lang/String;
    const-string v5, "PrivacyMonitor"

    invoke-static {v5, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 30
    invoke-static {v4}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_23
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_23} :catch_41

    .line 37
    .end local v2           #location1:Landroid/telephony/cdma/CdmaCellLocation;
    .end local v4           #log:Ljava/lang/String;
    :goto_23
    :try_start_23
    move-object v0, v1

    check-cast v0, Landroid/telephony/gsm/GsmCellLocation;

    move-object v3, v0

    .line 38
    .local v3, location2:Landroid/telephony/gsm/GsmCellLocation;
    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "TelephonyManager.getCellLocation\nreturn:(gsm)"

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 39
    .restart local v4       #log:Ljava/lang/String;
    const-string v5, "PrivacyMonitor"

    invoke-static {v5, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 40
    invoke-static {v4}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_3e
    .catch Ljava/lang/Exception; {:try_start_23 .. :try_end_3e} :catch_3f

    .line 46
    .end local v3           #location2:Landroid/telephony/gsm/GsmCellLocation;
    .end local v4           #log:Ljava/lang/String;
    :goto_3e
    return-object v1

    .line 42
    :catch_3f
    move-exception v5

    goto :goto_3e

    .line 32
    :catch_41
    move-exception v5

    goto :goto_23
.end method

.method public static getDeviceId(Landroid/telephony/TelephonyManager;)Ljava/lang/String;
    .registers 5
    .parameter "tm"

    .prologue
    .line 49
    invoke-virtual {p0}, Landroid/telephony/TelephonyManager;->getDeviceId()Ljava/lang/String;

    move-result-object v0

    .line 51
    .local v0, id:Ljava/lang/String;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "TelephonyManager.getDeviceId\nreturn:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 52
    .local v1, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 53
    invoke-static {v1}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1b
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_1b} :catch_1c

    .line 58
    .end local v1           #log:Ljava/lang/String;
    :goto_1b
    return-object v0

    .line 55
    :catch_1c
    move-exception v2

    goto :goto_1b
.end method

.method public static getLine1Number(Landroid/telephony/TelephonyManager;)Ljava/lang/String;
    .registers 5
    .parameter "tm"

    .prologue
    .line 13
    invoke-virtual {p0}, Landroid/telephony/TelephonyManager;->getLine1Number()Ljava/lang/String;

    move-result-object v1

    .line 15
    .local v1, number:Ljava/lang/String;
    :try_start_4
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "TelephonyManager.getLine1Number\nreturn:"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 16
    .local v0, log:Ljava/lang/String;
    const-string v2, "PrivacyMonitor"

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 17
    invoke-static {v0}, LPrivacyMonitor/util/PrivacyMonitorUtil;->recordLog(Ljava/lang/String;)V
    :try_end_1b
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_1b} :catch_1c

    .line 22
    .end local v0           #log:Ljava/lang/String;
    :goto_1b
    return-object v1

    .line 19
    :catch_1c
    move-exception v2

    goto :goto_1b
.end method
