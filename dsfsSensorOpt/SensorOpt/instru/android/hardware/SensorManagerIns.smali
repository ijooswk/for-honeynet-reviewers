.class public Linstru/android/hardware/SensorManagerIns;
.super Ljava/lang/Object;
.source "SensorManagerIns.java"


# static fields
.field protected static final TAG:Ljava/lang/String; = "[SensorManagerIns]"

.field public static callbackCount:J

.field private static dsfsEnable:Z

.field static listenerMap:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Landroid/hardware/SensorEventListener;",
            "Linstru/android/hardware/iListenerPackage;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .prologue
    .line 16
    const/4 v0, 0x0

    sput-boolean v0, Linstru/android/hardware/SensorManagerIns;->dsfsEnable:Z

    .line 17
    const-wide/16 v0, 0x0

    sput-wide v0, Linstru/android/hardware/SensorManagerIns;->callbackCount:J

    .line 33
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 14
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static registerListener(Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;I)Z
    .locals 1
    .parameter "sm"
    .parameter "listener"
    .parameter "sensor"
    .parameter "rate"

    .prologue
    .line 69
    const/4 v0, 0x0

    invoke-static {p0, p1, p2, p3, v0}, Linstru/android/hardware/SensorManagerIns;->registerListener(Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;ILandroid/os/Handler;)Z

    move-result v0

    return v0
.end method

.method public static declared-synchronized registerListener(Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;ILandroid/os/Handler;)Z
    .locals 12
    .parameter "sm"
    .parameter "listener"
    .parameter "sensor"
    .parameter "rate"
    .parameter "handler"

    .prologue
    .line 37
    const-class v11, Linstru/android/hardware/SensorManagerIns;

    monitor-enter v11

    :try_start_0
    const-string v2, "[SensorManagerIns]"

    const-string v3, "registerListener called"

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 38
    invoke-virtual/range {p0 .. p4}, Landroid/hardware/SensorManager;->registerListener(Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;ILandroid/os/Handler;)Z

    move-result v10

    .line 40
    .local v10, r:Z
    sget-boolean v2, Linstru/android/hardware/SensorManagerIns;->dsfsEnable:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-nez v2, :cond_1

    .line 64
    :cond_0
    :goto_0
    monitor-exit v11

    return v10

    .line 43
    :cond_1
    if-eqz v10, :cond_0

    .line 46
    const/4 v8, 0x0

    .line 48
    .local v8, ctx:Landroid/content/Context;
    :try_start_1
    new-instance v0, Linstru/android/hardware/iSensorDVFSModule;

    const-string v5, "dsfs.msa"

    move-object v1, p0

    move-object v2, p1

    move-object v3, p2

    move v4, p3

    invoke-direct/range {v0 .. v5}, Linstru/android/hardware/iSensorDVFSModule;-><init>(Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;ILjava/lang/String;)V

    .line 49
    .local v0, idvfs:Linstru/android/hardware/iSensorDVFSModule;
    new-instance v1, Linstru/android/hardware/iSensorPolicyFetcher;

    move-object v2, v8

    move-object v3, p0

    move-object v4, p1

    move-object v5, p2

    move v6, p3

    move-object v7, v0

    invoke-direct/range {v1 .. v7}, Linstru/android/hardware/iSensorPolicyFetcher;-><init>(Landroid/content/Context;Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;ILinstru/android/hardware/iSensorDVFSModule;)V

    .line 51
    .local v1, ifetcher:Linstru/android/hardware/iSensorPolicyFetcher;
    sget-object v2, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v2, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    sget-object v2, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v2, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Linstru/android/hardware/iListenerPackage;

    iget-object v2, v2, Linstru/android/hardware/iListenerPackage;->sensors:Ljava/util/Vector;

    invoke-virtual {v2, p2}, Ljava/util/Vector;->contains(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 54
    :cond_2
    sget-object v2, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v2, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_3

    .line 55
    new-instance v9, Linstru/android/hardware/iListenerPackage;

    invoke-direct {v9}, Linstru/android/hardware/iListenerPackage;-><init>()V

    .line 56
    .local v9, ipackage:Linstru/android/hardware/iListenerPackage;
    sget-object v2, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v2, p1, v9}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 59
    .end local v9           #ipackage:Linstru/android/hardware/iListenerPackage;
    :cond_3
    sget-object v2, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v2, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Linstru/android/hardware/iListenerPackage;

    iget-object v2, v2, Linstru/android/hardware/iListenerPackage;->sensors:Ljava/util/Vector;

    invoke-virtual {v2, p2}, Ljava/util/Vector;->add(Ljava/lang/Object;)Z

    .line 60
    sget-object v2, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v2, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Linstru/android/hardware/iListenerPackage;

    iget-object v2, v2, Linstru/android/hardware/iListenerPackage;->idvfss:Ljava/util/Vector;

    invoke-virtual {v2, v0}, Ljava/util/Vector;->add(Ljava/lang/Object;)Z

    .line 61
    sget-object v2, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v2, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Linstru/android/hardware/iListenerPackage;

    iget-object v2, v2, Linstru/android/hardware/iListenerPackage;->ifetchers:Ljava/util/Vector;

    invoke-virtual {v2, v1}, Ljava/util/Vector;->add(Ljava/lang/Object;)Z

    .line 62
    invoke-virtual {v1}, Linstru/android/hardware/iSensorPolicyFetcher;->start()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 37
    .end local v0           #idvfs:Linstru/android/hardware/iSensorDVFSModule;
    .end local v1           #ifetcher:Linstru/android/hardware/iSensorPolicyFetcher;
    .end local v8           #ctx:Landroid/content/Context;
    .end local v10           #r:Z
    :catchall_0
    move-exception v2

    monitor-exit v11

    throw v2
.end method

.method public static unregisterListener(Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;)V
    .locals 4
    .parameter "sm"
    .parameter "listener"

    .prologue
    .line 91
    sget-boolean v0, Linstru/android/hardware/SensorManagerIns;->dsfsEnable:Z

    if-nez v0, :cond_0

    .line 92
    invoke-virtual {p0, p1}, Landroid/hardware/SensorManager;->unregisterListener(Landroid/hardware/SensorEventListener;)V

    .line 93
    const-string v0, "[SensorManagerIns]"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "callback count "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    sget-wide v2, Linstru/android/hardware/SensorManagerIns;->callbackCount:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 101
    :goto_0
    return-void

    .line 96
    :cond_0
    const/4 v0, 0x0

    invoke-static {p0, p1, v0}, Linstru/android/hardware/SensorManagerIns;->unregisterListener(Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;)V

    .line 97
    const-string v0, "[SensorManagerIns]"

    const-string v1, "unregisterListener called!"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0
.end method

.method public static declared-synchronized unregisterListener(Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;)V
    .locals 5
    .parameter "sm"
    .parameter "listener"
    .parameter "sensor"

    .prologue
    .line 74
    const-class v1, Linstru/android/hardware/SensorManagerIns;

    monitor-enter v1

    :try_start_0
    sget-boolean v0, Linstru/android/hardware/SensorManagerIns;->dsfsEnable:Z

    if-nez v0, :cond_1

    .line 75
    invoke-virtual {p0, p1, p2}, Landroid/hardware/SensorManager;->unregisterListener(Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;)V

    .line 76
    const-string v0, "[SensorManagerIns]"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "callback count "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-wide v3, Linstru/android/hardware/SensorManagerIns;->callbackCount:J

    invoke-virtual {v2, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 88
    :cond_0
    :goto_0
    monitor-exit v1

    return-void

    .line 79
    :cond_1
    :try_start_1
    const-string v0, "[SensorManagerIns]"

    const-string v2, "unregisterListener called!"

    invoke-static {v0, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 80
    sget-object v0, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 83
    sget-object v0, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Linstru/android/hardware/iListenerPackage;

    invoke-virtual {v0, p0, p2}, Linstru/android/hardware/iListenerPackage;->destroy(Landroid/hardware/SensorManager;Landroid/hardware/Sensor;)V

    .line 84
    if-nez p2, :cond_2

    .line 85
    sget-object v0, Linstru/android/hardware/SensorManagerIns;->listenerMap:Ljava/util/HashMap;

    invoke-virtual {v0, p1}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 87
    :cond_2
    const-string v0, "[SensorManagerIns]"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "callback count "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-wide v3, Linstru/android/hardware/SensorManagerIns;->callbackCount:J

    invoke-virtual {v2, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 74
    :catchall_0
    move-exception v0

    monitor-exit v1

    throw v0
.end method
