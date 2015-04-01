.class public Linstru/android/hardware/iSensorPolicyFetcher;
.super Ljava/lang/Thread;
.source "iSensorPolicyFetcher.java"


# static fields
.field private static final POLICY_fetching_Window_ms:J = 0x7d0L

.field private static final delayTable:[Ljava/lang/String;

.field private static final policyTable:[Ljava/lang/String;

.field private static final sensorTable:[Ljava/lang/String;


# instance fields
.field protected TAG:Ljava/lang/String;

.field ctx:Landroid/content/Context;

.field public enable:Z

.field idvfs:Linstru/android/hardware/iSensorDVFSModule;

.field private mBound:Z

.field public policy:I

.field public rate:I

.field sensor:Landroid/hardware/Sensor;

.field private serviceConnection:Landroid/content/ServiceConnection;

.field sl:Landroid/hardware/SensorEventListener;

.field sm:Landroid/hardware/SensorManager;


# direct methods
.method static constructor <clinit>()V
    .locals 8

    .prologue
    const/4 v7, 0x4

    const/4 v6, 0x3

    const/4 v5, 0x2

    const/4 v4, 0x1

    const/4 v3, 0x0

    .line 20
    new-array v0, v7, [Ljava/lang/String;

    const-string v1, "fastest"

    aput-object v1, v0, v3

    const-string v1, "game"

    aput-object v1, v0, v4

    const-string v1, "ui"

    aput-object v1, v0, v5

    const-string v1, "normal"

    aput-object v1, v0, v6

    sput-object v0, Linstru/android/hardware/iSensorPolicyFetcher;->delayTable:[Ljava/lang/String;

    .line 21
    const/16 v0, 0xe

    new-array v0, v0, [Ljava/lang/String;

    const-string v1, "All"

    aput-object v1, v0, v3

    const-string v1, "ACCELEROMETER"

    aput-object v1, v0, v4

    const-string v1, "MAGNETIC_FIELD"

    aput-object v1, v0, v5

    const-string v1, "ORIENTATION"

    aput-object v1, v0, v6

    const-string v1, "GYROSCOPE"

    aput-object v1, v0, v7

    const/4 v1, 0x5

    const-string v2, "LIGHT"

    aput-object v2, v0, v1

    const/4 v1, 0x6

    const-string v2, "PRESSURE"

    aput-object v2, v0, v1

    const/4 v1, 0x7

    const-string v2, "TEMPERATURE"

    aput-object v2, v0, v1

    const/16 v1, 0x8

    const-string v2, "PROXIMITY"

    aput-object v2, v0, v1

    const/16 v1, 0x9

    const-string v2, "GRAVITY"

    aput-object v2, v0, v1

    const/16 v1, 0xa

    const-string v2, "LINEAR_ACCELERATION"

    aput-object v2, v0, v1

    const/16 v1, 0xb

    const-string v2, "ROTATION_VECTOR"

    aput-object v2, v0, v1

    const/16 v1, 0xc

    const-string v2, "RELATIVE_HUMIDITY"

    aput-object v2, v0, v1

    const/16 v1, 0xd

    const-string v2, "AMBIENT_TEMPERATURE"

    aput-object v2, v0, v1

    sput-object v0, Linstru/android/hardware/iSensorPolicyFetcher;->sensorTable:[Ljava/lang/String;

    .line 23
    new-array v0, v7, [Ljava/lang/String;

    const-string v1, "None"

    aput-object v1, v0, v3

    const-string v1, "Auto-Scaling"

    aput-object v1, v0, v4

    const-string v1, "Manual-Scaling"

    aput-object v1, v0, v5

    const-string v1, "Disable Sensor"

    aput-object v1, v0, v6

    sput-object v0, Linstru/android/hardware/iSensorPolicyFetcher;->policyTable:[Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;ILinstru/android/hardware/iSensorDVFSModule;)V
    .locals 3
    .parameter "ctx2"
    .parameter "sm2"
    .parameter "listener"
    .parameter "sensor2"
    .parameter "rate2"
    .parameter "idvfs2"

    .prologue
    const/4 v2, 0x0

    .line 60
    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    .line 16
    const-string v1, "[iPolicyFetcher]"

    iput-object v1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->TAG:Ljava/lang/String;

    .line 26
    iput-boolean v2, p0, Linstru/android/hardware/iSensorPolicyFetcher;->mBound:Z

    .line 28
    new-instance v1, Linstru/android/hardware/iSensorPolicyFetcher$1;

    invoke-direct {v1, p0}, Linstru/android/hardware/iSensorPolicyFetcher$1;-><init>(Linstru/android/hardware/iSensorPolicyFetcher;)V

    iput-object v1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->serviceConnection:Landroid/content/ServiceConnection;

    .line 54
    const/4 v1, 0x0

    iput-object v1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->ctx:Landroid/content/Context;

    .line 61
    iput-object p1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->ctx:Landroid/content/Context;

    .line 62
    iput-object p2, p0, Linstru/android/hardware/iSensorPolicyFetcher;->sm:Landroid/hardware/SensorManager;

    .line 63
    iput-object p3, p0, Linstru/android/hardware/iSensorPolicyFetcher;->sl:Landroid/hardware/SensorEventListener;

    .line 64
    iput-object p4, p0, Linstru/android/hardware/iSensorPolicyFetcher;->sensor:Landroid/hardware/Sensor;

    .line 65
    iput p5, p0, Linstru/android/hardware/iSensorPolicyFetcher;->rate:I

    .line 66
    iput-object p6, p0, Linstru/android/hardware/iSensorPolicyFetcher;->idvfs:Linstru/android/hardware/iSensorDVFSModule;

    .line 68
    iput-boolean v2, p0, Linstru/android/hardware/iSensorPolicyFetcher;->enable:Z

    .line 69
    iput v2, p0, Linstru/android/hardware/iSensorPolicyFetcher;->policy:I

    .line 74
    iget-object v1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->ctx:Landroid/content/Context;

    if-eqz v1, :cond_0

    .line 75
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v2, p0, Linstru/android/hardware/iSensorPolicyFetcher;->TAG:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Linstru/android/hardware/iSensorPolicyFetcher;->ctx:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->TAG:Ljava/lang/String;

    .line 76
    new-instance v0, Landroid/content/Intent;

    const-string v1, "oslab.dsfs.sensingpolicy.service"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 77
    .local v0, intent:Landroid/content/Intent;
    iget-object v1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->ctx:Landroid/content/Context;

    invoke-virtual {v1, v0}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 82
    .end local v0           #intent:Landroid/content/Intent;
    :goto_0
    return-void

    .line 79
    :cond_0
    iget-object v1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->TAG:Ljava/lang/String;

    const-string v2, "ContextUtil can NOT get application context."

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0
.end method

.method static synthetic access$002(Linstru/android/hardware/iSensorPolicyFetcher;Z)Z
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 14
    iput-boolean p1, p0, Linstru/android/hardware/iSensorPolicyFetcher;->mBound:Z

    return p1
.end method


# virtual methods
.method public fetcherdestroy()V
    .locals 1

    .prologue
    .line 177
    const/4 v0, 0x0

    iput-boolean v0, p0, Linstru/android/hardware/iSensorPolicyFetcher;->enable:Z

    .line 178
    iget-object v0, p0, Linstru/android/hardware/iSensorPolicyFetcher;->idvfs:Linstru/android/hardware/iSensorDVFSModule;

    invoke-virtual {v0}, Linstru/android/hardware/iSensorDVFSModule;->dvfsdestroy()V

    .line 179
    return-void
.end method

.method public run()V
    .locals 8

    .prologue
    const/4 v7, 0x1

    .line 85
    iget-object v3, p0, Linstru/android/hardware/iSensorPolicyFetcher;->sensor:Landroid/hardware/Sensor;

    invoke-virtual {v3}, Landroid/hardware/Sensor;->getType()I

    move-result v2

    .line 86
    .local v2, type:I
    const/4 v3, -0x1

    if-ne v2, v3, :cond_0

    const/4 v2, 0x0

    .line 87
    :cond_0
    iget-object v3, p0, Linstru/android/hardware/iSensorPolicyFetcher;->TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v5, Linstru/android/hardware/iSensorPolicyFetcher;->sensorTable:[Ljava/lang/String;

    aget-object v5, v5, v2

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    sget-object v5, Linstru/android/hardware/iSensorPolicyFetcher;->delayTable:[Ljava/lang/String;

    iget v6, p0, Linstru/android/hardware/iSensorPolicyFetcher;->rate:I

    aget-object v5, v5, v6

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 91
    iget-object v3, p0, Linstru/android/hardware/iSensorPolicyFetcher;->ctx:Landroid/content/Context;

    if-eqz v3, :cond_1

    .line 92
    iget-object v3, p0, Linstru/android/hardware/iSensorPolicyFetcher;->TAG:Ljava/lang/String;

    const-string v4, "Trying to bind to remote service"

    invoke-static {v3, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 93
    new-instance v1, Landroid/content/Intent;

    const-string v3, "oslab.dsfs.sensingpolicy.service"

    invoke-direct {v1, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 94
    .local v1, intent:Landroid/content/Intent;
    iget-object v3, p0, Linstru/android/hardware/iSensorPolicyFetcher;->ctx:Landroid/content/Context;

    iget-object v4, p0, Linstru/android/hardware/iSensorPolicyFetcher;->serviceConnection:Landroid/content/ServiceConnection;

    invoke-virtual {v3, v1, v4, v7}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    .line 95
    iget-object v3, p0, Linstru/android/hardware/iSensorPolicyFetcher;->TAG:Ljava/lang/String;

    const-string v4, "Bind to remote service"

    invoke-static {v3, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 98
    .end local v1           #intent:Landroid/content/Intent;
    :cond_1
    iget v0, p0, Linstru/android/hardware/iSensorPolicyFetcher;->rate:I

    .line 100
    .local v0, currentfreq:I
    iput-boolean v7, p0, Linstru/android/hardware/iSensorPolicyFetcher;->enable:Z

    .line 102
    iget-object v3, p0, Linstru/android/hardware/iSensorPolicyFetcher;->idvfs:Linstru/android/hardware/iSensorDVFSModule;

    invoke-virtual {v3}, Linstru/android/hardware/iSensorDVFSModule;->start()V

    .line 174
    return-void
.end method
