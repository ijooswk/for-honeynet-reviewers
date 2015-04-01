.class Linstru/android/hardware/iListenerPackage;
.super Ljava/lang/Object;
.source "SensorManagerIns.java"


# instance fields
.field public idvfss:Ljava/util/Vector;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Vector",
            "<",
            "Linstru/android/hardware/iSensorDVFSModule;",
            ">;"
        }
    .end annotation
.end field

.field public ifetchers:Ljava/util/Vector;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Vector",
            "<",
            "Linstru/android/hardware/iSensorPolicyFetcher;",
            ">;"
        }
    .end annotation
.end field

.field public sensors:Ljava/util/Vector;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Vector",
            "<",
            "Landroid/hardware/Sensor;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 108
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 109
    new-instance v0, Ljava/util/Vector;

    invoke-direct {v0}, Ljava/util/Vector;-><init>()V

    iput-object v0, p0, Linstru/android/hardware/iListenerPackage;->idvfss:Ljava/util/Vector;

    .line 110
    new-instance v0, Ljava/util/Vector;

    invoke-direct {v0}, Ljava/util/Vector;-><init>()V

    iput-object v0, p0, Linstru/android/hardware/iListenerPackage;->ifetchers:Ljava/util/Vector;

    .line 111
    new-instance v0, Ljava/util/Vector;

    invoke-direct {v0}, Ljava/util/Vector;-><init>()V

    iput-object v0, p0, Linstru/android/hardware/iListenerPackage;->sensors:Ljava/util/Vector;

    .line 112
    return-void
.end method


# virtual methods
.method public destroy(Landroid/hardware/SensorManager;Landroid/hardware/Sensor;)V
    .locals 4
    .parameter "sm"
    .parameter "sensor"

    .prologue
    .line 114
    if-eqz p2, :cond_1

    .line 115
    iget-object v2, p0, Linstru/android/hardware/iListenerPackage;->sensors:Ljava/util/Vector;

    invoke-virtual {v2, p2}, Ljava/util/Vector;->indexOf(Ljava/lang/Object;)I

    move-result v1

    .line 116
    .local v1, index:I
    iget-object v2, p0, Linstru/android/hardware/iListenerPackage;->ifetchers:Ljava/util/Vector;

    invoke-virtual {v2, v1}, Ljava/util/Vector;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Linstru/android/hardware/iSensorPolicyFetcher;

    .line 118
    .local v0, ifetcher:Linstru/android/hardware/iSensorPolicyFetcher;
    invoke-virtual {v0}, Linstru/android/hardware/iSensorPolicyFetcher;->fetcherdestroy()V

    .line 120
    iget-object v2, p0, Linstru/android/hardware/iListenerPackage;->idvfss:Ljava/util/Vector;

    invoke-virtual {v2, v1}, Ljava/util/Vector;->remove(I)Ljava/lang/Object;

    .line 121
    iget-object v2, p0, Linstru/android/hardware/iListenerPackage;->ifetchers:Ljava/util/Vector;

    invoke-virtual {v2, v1}, Ljava/util/Vector;->remove(I)Ljava/lang/Object;

    .line 122
    iget-object v2, p0, Linstru/android/hardware/iListenerPackage;->sensors:Ljava/util/Vector;

    invoke-virtual {v2, v1}, Ljava/util/Vector;->remove(I)Ljava/lang/Object;

    .line 128
    .end local v0           #ifetcher:Linstru/android/hardware/iSensorPolicyFetcher;
    .end local v1           #index:I
    :cond_0
    return-void

    .line 125
    :cond_1
    iget-object v2, p0, Linstru/android/hardware/iListenerPackage;->ifetchers:Ljava/util/Vector;

    invoke-virtual {v2}, Ljava/util/Vector;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Linstru/android/hardware/iSensorPolicyFetcher;

    .line 126
    .restart local v0       #ifetcher:Linstru/android/hardware/iSensorPolicyFetcher;
    invoke-virtual {v0}, Linstru/android/hardware/iSensorPolicyFetcher;->fetcherdestroy()V

    goto :goto_0
.end method
