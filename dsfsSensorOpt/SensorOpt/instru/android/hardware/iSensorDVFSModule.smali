.class public Linstru/android/hardware/iSensorDVFSModule;
.super Ljava/lang/Thread;
.source "iSensorDVFSModule.java"


# static fields
.field private static final SENSOR_THRESHOLD_MAX:[F = null

.field private static final SENSOR_THRESHOLD_MIN:[F = null

.field private static final Scaling_Window_ms:J = 0x1f4L

.field private static final delayTable:[Ljava/lang/String;

.field private static final sensorTable:[Ljava/lang/String;


# instance fields
.field protected TAG:Ljava/lang/String;

.field public enable:Z

.field isl:Linstru/android/hardware/iSensorListener;

.field osl:Landroid/hardware/SensorEventListener;

.field rate:I

.field sensor:Landroid/hardware/Sensor;

.field sm:Landroid/hardware/SensorManager;

.field type:I


# direct methods
.method static constructor <clinit>()V
    .locals 7

    .prologue
    const/4 v6, 0x3

    const/4 v5, 0x2

    const/4 v4, 0x1

    const/4 v2, 0x0

    const/16 v3, 0xe

    .line 16
    const/4 v0, 0x4

    new-array v0, v0, [Ljava/lang/String;

    const-string v1, "fastest"

    aput-object v1, v0, v2

    const-string v1, "game"

    aput-object v1, v0, v4

    const-string v1, "ui"

    aput-object v1, v0, v5

    const-string v1, "normal"

    aput-object v1, v0, v6

    sput-object v0, Linstru/android/hardware/iSensorDVFSModule;->delayTable:[Ljava/lang/String;

    .line 18
    new-array v0, v3, [Ljava/lang/String;

    const-string v1, "All"

    aput-object v1, v0, v2

    const-string v1, "ACCELEROMETER"

    aput-object v1, v0, v4

    const-string v1, "MAGNETIC_FIELD"

    aput-object v1, v0, v5

    const-string v1, "ORIENTATION"

    aput-object v1, v0, v6

    const/4 v1, 0x4

    const-string v2, "GYROSCOPE"

    aput-object v2, v0, v1

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

    sput-object v0, Linstru/android/hardware/iSensorDVFSModule;->sensorTable:[Ljava/lang/String;

    .line 19
    new-array v0, v3, [F

    fill-array-data v0, :array_0

    sput-object v0, Linstru/android/hardware/iSensorDVFSModule;->SENSOR_THRESHOLD_MAX:[F

    .line 20
    new-array v0, v3, [F

    fill-array-data v0, :array_1

    sput-object v0, Linstru/android/hardware/iSensorDVFSModule;->SENSOR_THRESHOLD_MIN:[F

    return-void

    .line 19
    :array_0
    .array-data 0x4
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
        0x0t 0x0t 0x20t 0x41t
    .end array-data

    .line 20
    :array_1
    .array-data 0x4
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
        0x0t 0x0t 0x80t 0x3ft
    .end array-data
.end method

.method public constructor <init>(Landroid/hardware/SensorManager;Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;ILjava/lang/String;)V
    .locals 3
    .parameter "sm2"
    .parameter "listener"
    .parameter "sensor2"
    .parameter "rate2"
    .parameter "pkgname"

    .prologue
    const/4 v2, 0x0

    .line 31
    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    .line 11
    const-string v0, "[iDVFSModel]"

    iput-object v0, p0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    .line 32
    iput-object p1, p0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    .line 33
    iput-object p2, p0, Linstru/android/hardware/iSensorDVFSModule;->osl:Landroid/hardware/SensorEventListener;

    .line 34
    iput-object p3, p0, Linstru/android/hardware/iSensorDVFSModule;->sensor:Landroid/hardware/Sensor;

    .line 35
    iput p4, p0, Linstru/android/hardware/iSensorDVFSModule;->rate:I

    .line 36
    iget-object v0, p0, Linstru/android/hardware/iSensorDVFSModule;->sensor:Landroid/hardware/Sensor;

    invoke-virtual {v0}, Landroid/hardware/Sensor;->getType()I

    move-result v0

    iput v0, p0, Linstru/android/hardware/iSensorDVFSModule;->type:I

    .line 37
    iget v0, p0, Linstru/android/hardware/iSensorDVFSModule;->type:I

    const/4 v1, -0x1

    if-ne v0, v1, :cond_0

    iput v2, p0, Linstru/android/hardware/iSensorDVFSModule;->type:I

    .line 38
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    .line 39
    iput-boolean v2, p0, Linstru/android/hardware/iSensorDVFSModule;->enable:Z

    .line 40
    new-instance v0, Linstru/android/hardware/iSensorListener;

    iget-object v1, p0, Linstru/android/hardware/iSensorDVFSModule;->osl:Landroid/hardware/SensorEventListener;

    invoke-direct {v0, v1}, Linstru/android/hardware/iSensorListener;-><init>(Landroid/hardware/SensorEventListener;)V

    iput-object v0, p0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    .line 41
    return-void
.end method


# virtual methods
.method public dvfsdestroy()V
    .locals 1

    .prologue
    .line 171
    const/4 v0, 0x0

    iput-boolean v0, p0, Linstru/android/hardware/iSensorDVFSModule;->enable:Z

    .line 172
    return-void
.end method

.method public run()V
    .locals 18

    .prologue
    .line 44
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    sget-object v15, Linstru/android/hardware/iSensorDVFSModule;->sensorTable:[Ljava/lang/String;

    move-object/from16 v0, p0

    iget v0, v0, Linstru/android/hardware/iSensorDVFSModule;->type:I

    move/from16 v16, v0

    aget-object v15, v15, v16

    invoke-static {v14, v15}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 45
    const/4 v14, 0x1

    move-object/from16 v0, p0

    iput-boolean v14, v0, Linstru/android/hardware/iSensorDVFSModule;->enable:Z

    .line 47
    const/4 v14, 0x4

    new-array v11, v14, [J

    .line 48
    .local v11, freqTimes:[J
    const/4 v2, 0x0

    .line 49
    .local v2, counterup:I
    const/4 v1, 0x0

    .line 50
    .local v1, counterdown:I
    const/4 v12, 0x0

    .local v12, i:I
    :goto_0
    array-length v14, v11

    if-ge v12, v14, :cond_0

    .line 51
    const-wide/16 v14, 0x0

    aput-wide v14, v11, v12

    .line 50
    add-int/lit8 v12, v12, 0x1

    goto :goto_0

    .line 53
    :cond_0
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v7

    .line 58
    .local v7, freqBeginTime:J
    move-object/from16 v0, p0

    iget v14, v0, Linstru/android/hardware/iSensorDVFSModule;->rate:I

    const/4 v15, 0x3

    if-ne v14, v15, :cond_1

    .line 168
    :goto_1
    return-void

    .line 62
    :cond_1
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    move-object/from16 v0, p0

    iget-object v0, v0, Linstru/android/hardware/iSensorDVFSModule;->sensor:Landroid/hardware/Sensor;

    move-object/from16 v16, v0

    move-object/from16 v0, p0

    iget v0, v0, Linstru/android/hardware/iSensorDVFSModule;->rate:I

    move/from16 v17, v0

    invoke-virtual/range {v14 .. v17}, Landroid/hardware/SensorManager;->registerListener(Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;I)Z

    .line 66
    move-object/from16 v0, p0

    iget v3, v0, Linstru/android/hardware/iSensorDVFSModule;->rate:I

    .line 67
    .local v3, delay:I
    :cond_2
    :goto_2
    move-object/from16 v0, p0

    iget-boolean v14, v0, Linstru/android/hardware/iSensorDVFSModule;->enable:Z

    if-eqz v14, :cond_7

    .line 69
    const-wide/16 v14, 0x1f4

    :try_start_0
    invoke-static {v14, v15}, Linstru/android/hardware/iSensorDVFSModule;->sleep(J)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 74
    :goto_3
    const/4 v6, 0x0

    .line 76
    .local v6, flag:I
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    iget-object v15, v14, Linstru/android/hardware/iSensorListener;->delay_queue:Ljava/util/Queue;

    monitor-enter v15

    .line 83
    :cond_3
    :goto_4
    :try_start_1
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    iget-object v14, v14, Linstru/android/hardware/iSensorListener;->delay_queue:Ljava/util/Queue;

    invoke-interface {v14}, Ljava/util/Queue;->isEmpty()Z

    move-result v14

    if-nez v14, :cond_4

    .line 84
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    iget-object v14, v14, Linstru/android/hardware/iSensorListener;->delay_queue:Ljava/util/Queue;

    invoke-interface {v14}, Ljava/util/Queue;->poll()Ljava/lang/Object;

    move-result-object v14

    check-cast v14, Ljava/lang/Float;

    invoke-virtual {v14}, Ljava/lang/Float;->floatValue()F

    move-result v4

    .line 87
    .local v4, delta:F
    sget-object v14, Linstru/android/hardware/iSensorDVFSModule;->SENSOR_THRESHOLD_MAX:[F

    move-object/from16 v0, p0

    iget v0, v0, Linstru/android/hardware/iSensorDVFSModule;->type:I

    move/from16 v16, v0

    aget v14, v14, v16

    cmpl-float v14, v4, v14

    if-lez v14, :cond_5

    .line 88
    const/4 v6, 0x1

    .line 95
    .end local v4           #delta:F
    :cond_4
    monitor-exit v15
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 100
    if-nez v6, :cond_6

    const/4 v14, 0x3

    if-ge v3, v14, :cond_6

    .line 102
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v16, Linstru/android/hardware/iSensorDVFSModule;->delayTable:[Ljava/lang/String;

    aget-object v16, v16, v3

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    const-string v16, "->"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    sget-object v16, Linstru/android/hardware/iSensorDVFSModule;->delayTable:[Ljava/lang/String;

    add-int/lit8 v17, v3, 0x1

    aget-object v16, v16, v17

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 105
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v9

    .line 106
    .local v9, freqEndTime:J
    aget-wide v14, v11, v3

    sub-long v16, v9, v7

    add-long v14, v14, v16

    aput-wide v14, v11, v3

    .line 107
    move-wide v7, v9

    .line 109
    add-int/lit8 v1, v1, 0x1

    .line 110
    add-int/lit8 v3, v3, 0x1

    .line 112
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->osl:Landroid/hardware/SensorEventListener;

    invoke-virtual {v14, v15}, Landroid/hardware/SensorManager;->unregisterListener(Landroid/hardware/SensorEventListener;)V

    .line 113
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->osl:Landroid/hardware/SensorEventListener;

    move-object/from16 v0, p0

    iget-object v0, v0, Linstru/android/hardware/iSensorDVFSModule;->sensor:Landroid/hardware/Sensor;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    invoke-virtual {v14, v15, v0, v3}, Landroid/hardware/SensorManager;->registerListener(Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;I)Z

    .line 114
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    invoke-virtual {v14, v15}, Landroid/hardware/SensorManager;->unregisterListener(Landroid/hardware/SensorEventListener;)V

    .line 115
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    move-object/from16 v0, p0

    iget-object v0, v0, Linstru/android/hardware/iSensorDVFSModule;->sensor:Landroid/hardware/Sensor;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    invoke-virtual {v14, v15, v0, v3}, Landroid/hardware/SensorManager;->registerListener(Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;I)Z

    .line 117
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    const-string v15, "reregistered listeners"

    invoke-static {v14, v15}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_2

    .line 70
    .end local v6           #flag:I
    .end local v9           #freqEndTime:J
    :catch_0
    move-exception v5

    .line 71
    .local v5, e:Ljava/lang/Exception;
    invoke-virtual {v5}, Ljava/lang/Exception;->printStackTrace()V

    goto/16 :goto_3

    .line 91
    .end local v5           #e:Ljava/lang/Exception;
    .restart local v4       #delta:F
    .restart local v6       #flag:I
    :cond_5
    :try_start_2
    sget-object v14, Linstru/android/hardware/iSensorDVFSModule;->SENSOR_THRESHOLD_MIN:[F

    move-object/from16 v0, p0

    iget v0, v0, Linstru/android/hardware/iSensorDVFSModule;->type:I

    move/from16 v16, v0

    aget v14, v14, v16

    cmpl-float v14, v4, v14

    if-lez v14, :cond_3

    .line 92
    const/4 v6, 0x2

    goto/16 :goto_4

    .line 95
    .end local v4           #delta:F
    :catchall_0
    move-exception v14

    monitor-exit v15
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    throw v14

    .line 119
    :cond_6
    const/4 v14, 0x1

    if-ne v6, v14, :cond_2

    move-object/from16 v0, p0

    iget v14, v0, Linstru/android/hardware/iSensorDVFSModule;->rate:I

    if-le v3, v14, :cond_2

    .line 120
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v16, Linstru/android/hardware/iSensorDVFSModule;->delayTable:[Ljava/lang/String;

    aget-object v16, v16, v3

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    const-string v16, "->"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    sget-object v16, Linstru/android/hardware/iSensorDVFSModule;->delayTable:[Ljava/lang/String;

    add-int/lit8 v17, v3, -0x1

    aget-object v16, v16, v17

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 123
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v9

    .line 124
    .restart local v9       #freqEndTime:J
    aget-wide v14, v11, v3

    sub-long v16, v9, v7

    add-long v14, v14, v16

    aput-wide v14, v11, v3

    .line 125
    move-wide v7, v9

    .line 127
    add-int/lit8 v2, v2, 0x1

    .line 128
    add-int/lit8 v3, v3, -0x1

    .line 130
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->osl:Landroid/hardware/SensorEventListener;

    invoke-virtual {v14, v15}, Landroid/hardware/SensorManager;->unregisterListener(Landroid/hardware/SensorEventListener;)V

    .line 131
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->osl:Landroid/hardware/SensorEventListener;

    move-object/from16 v0, p0

    iget-object v0, v0, Linstru/android/hardware/iSensorDVFSModule;->sensor:Landroid/hardware/Sensor;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    invoke-virtual {v14, v15, v0, v3}, Landroid/hardware/SensorManager;->registerListener(Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;I)Z

    .line 132
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    invoke-virtual {v14, v15}, Landroid/hardware/SensorManager;->unregisterListener(Landroid/hardware/SensorEventListener;)V

    .line 133
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    move-object/from16 v0, p0

    iget-object v0, v0, Linstru/android/hardware/iSensorDVFSModule;->sensor:Landroid/hardware/Sensor;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    invoke-virtual {v14, v15, v0, v3}, Landroid/hardware/SensorManager;->registerListener(Landroid/hardware/SensorEventListener;Landroid/hardware/Sensor;I)Z

    .line 135
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    const-string v15, "reregistered listeners"

    invoke-static {v14, v15}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_2

    .line 145
    .end local v6           #flag:I
    .end local v9           #freqEndTime:J
    :cond_7
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->sm:Landroid/hardware/SensorManager;

    move-object/from16 v0, p0

    iget-object v15, v0, Linstru/android/hardware/iSensorDVFSModule;->isl:Linstru/android/hardware/iSensorListener;

    invoke-virtual {v14, v15}, Landroid/hardware/SensorManager;->unregisterListener(Landroid/hardware/SensorEventListener;)V

    .line 149
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v9

    .line 150
    .restart local v9       #freqEndTime:J
    aget-wide v14, v11, v3

    sub-long v16, v9, v7

    add-long v14, v14, v16

    aput-wide v14, v11, v3

    .line 154
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "DVFS dumped for Sensor: "

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v0, p0

    iget-object v0, v0, Linstru/android/hardware/iSensorDVFSModule;->sensor:Landroid/hardware/Sensor;

    move-object/from16 v16, v0

    invoke-virtual/range {v16 .. v16}, Landroid/hardware/Sensor;->toString()Ljava/lang/String;

    move-result-object v16

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 155
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "Frequency Scaling: \tUp:\t"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v15

    const-string v16, "\tDown:\t"

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 161
    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "Frequency Scaling: \tFastest:\t"

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const/4 v15, 0x0

    aget-wide v15, v11, v15

    invoke-virtual/range {v14 .. v16}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, "\tGame:\t"

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const/4 v15, 0x1

    aget-wide v15, v11, v15

    invoke-virtual/range {v14 .. v16}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, "\tUI:\t"

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const/4 v15, 0x2

    aget-wide v15, v11, v15

    invoke-virtual/range {v14 .. v16}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, "\tNormal:\t"

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const/4 v15, 0x3

    aget-wide v15, v11, v15

    invoke-virtual/range {v14 .. v16}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    .line 166
    .local v13, statString:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v14, v0, Linstru/android/hardware/iSensorDVFSModule;->TAG:Ljava/lang/String;

    invoke-static {v14, v13}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1
.end method
