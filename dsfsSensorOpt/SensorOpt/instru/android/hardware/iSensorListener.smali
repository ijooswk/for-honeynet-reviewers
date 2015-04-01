.class public Linstru/android/hardware/iSensorListener;
.super Ljava/lang/Object;
.source "iSensorListener.java"

# interfaces
.implements Landroid/hardware/SensorEventListener;


# static fields
.field protected static final TAG:Ljava/lang/String; = "[iSensorListener]"


# instance fields
.field public delay_queue:Ljava/util/Queue;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Queue",
            "<",
            "Ljava/lang/Float;",
            ">;"
        }
    .end annotation
.end field

.field public delta:[F

.field private osl:Landroid/hardware/SensorEventListener;

.field public prevalue:[F


# direct methods
.method public constructor <init>(Landroid/hardware/SensorEventListener;)V
    .locals 2
    .parameter "osl_"

    .prologue
    const/4 v1, 0x4

    .line 21
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 16
    const/4 v0, 0x0

    iput-object v0, p0, Linstru/android/hardware/iSensorListener;->osl:Landroid/hardware/SensorEventListener;

    .line 22
    new-array v0, v1, [F

    iput-object v0, p0, Linstru/android/hardware/iSensorListener;->prevalue:[F

    .line 23
    new-array v0, v1, [F

    iput-object v0, p0, Linstru/android/hardware/iSensorListener;->delta:[F

    .line 24
    new-instance v0, Ljava/util/concurrent/ConcurrentLinkedQueue;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentLinkedQueue;-><init>()V

    iput-object v0, p0, Linstru/android/hardware/iSensorListener;->delay_queue:Ljava/util/Queue;

    .line 25
    iput-object p1, p0, Linstru/android/hardware/iSensorListener;->osl:Landroid/hardware/SensorEventListener;

    .line 26
    return-void
.end method


# virtual methods
.method public onAccuracyChanged(Landroid/hardware/Sensor;I)V
    .locals 1
    .parameter "sensor"
    .parameter "accuracy"

    .prologue
    .line 52
    iget-object v0, p0, Linstru/android/hardware/iSensorListener;->osl:Landroid/hardware/SensorEventListener;

    invoke-interface {v0, p1, p2}, Landroid/hardware/SensorEventListener;->onAccuracyChanged(Landroid/hardware/Sensor;I)V

    .line 53
    return-void
.end method

.method public onSensorChanged(Landroid/hardware/SensorEvent;)V
    .locals 6
    .parameter "event"

    .prologue
    const/4 v5, 0x0

    .line 31
    const/4 v1, 0x0

    .line 32
    .local v1, maxdelay:F
    const/4 v0, 0x0

    .local v0, i:I
    :goto_0
    iget-object v2, p1, Landroid/hardware/SensorEvent;->values:[F

    array-length v2, v2

    if-ge v0, v2, :cond_2

    .line 33
    iget-object v2, p0, Linstru/android/hardware/iSensorListener;->delta:[F

    iget-object v3, p1, Landroid/hardware/SensorEvent;->values:[F

    aget v3, v3, v0

    iget-object v4, p0, Linstru/android/hardware/iSensorListener;->prevalue:[F

    aget v4, v4, v0

    sub-float/2addr v3, v4

    aput v3, v2, v0

    .line 37
    iget-object v2, p0, Linstru/android/hardware/iSensorListener;->delta:[F

    aget v2, v2, v0

    cmpg-float v2, v2, v5

    if-gez v2, :cond_0

    iget-object v2, p0, Linstru/android/hardware/iSensorListener;->delta:[F

    iget-object v3, p0, Linstru/android/hardware/iSensorListener;->delta:[F

    aget v3, v3, v0

    sub-float v3, v5, v3

    aput v3, v2, v0

    .line 39
    :cond_0
    iget-object v2, p0, Linstru/android/hardware/iSensorListener;->delta:[F

    aget v2, v2, v0

    cmpl-float v2, v2, v1

    if-lez v2, :cond_1

    iget-object v2, p0, Linstru/android/hardware/iSensorListener;->delta:[F

    aget v1, v2, v0

    .line 40
    :cond_1
    iget-object v2, p0, Linstru/android/hardware/iSensorListener;->prevalue:[F

    iget-object v3, p1, Landroid/hardware/SensorEvent;->values:[F

    aget v3, v3, v0

    aput v3, v2, v0

    .line 32
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 44
    :cond_2
    iget-object v3, p0, Linstru/android/hardware/iSensorListener;->delay_queue:Ljava/util/Queue;

    monitor-enter v3

    .line 45
    :try_start_0
    iget-object v2, p0, Linstru/android/hardware/iSensorListener;->delay_queue:Ljava/util/Queue;

    invoke-static {v1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v4

    invoke-interface {v2, v4}, Ljava/util/Queue;->add(Ljava/lang/Object;)Z

    .line 46
    monitor-exit v3

    .line 47
    return-void

    .line 46
    :catchall_0
    move-exception v2

    monitor-exit v3
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v2
.end method
